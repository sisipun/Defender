package io.cucumber.view

import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop
import com.badlogic.gdx.scenes.scene2d.utils.DragListener
import com.badlogic.gdx.utils.Array
import io.cucumber.Game
import io.cucumber.actor.Balance
import io.cucumber.actor.Health
import io.cucumber.actor.SecondsTimer
import io.cucumber.actor.area.AreaBlock
import io.cucumber.actor.area.AreaType
import io.cucumber.actor.area.GameArea
import io.cucumber.actor.menu.DefenderMenu
import io.cucumber.actor.menu.DefenderMenuItem
import io.cucumber.actor.menu.preview.DefenderPreview
import io.cucumber.base.helper.FontHelper
import io.cucumber.base.helper.FontParams
import io.cucumber.base.view.BaseScreen
import io.cucumber.manager.Level
import io.cucumber.manager.event.GenerateEnemyTimeEvent
import io.cucumber.manager.event.TimeEventType
import io.cucumber.utils.constants.Constants.*
import io.cucumber.utils.generator.AreaMapGenerator
import kotlin.math.max
import kotlin.math.min

class GameScreen(
        game: Game,
        private val level: Level
) : BaseScreen(game) {

    private var startPositionX: Float = 0f
    private var startPositionY: Float = 0f

    private val areaMapGenerator: AreaMapGenerator = AreaMapGenerator()

    private val gameArea: GameArea = GameArea()
    private val health: Health = Health(
            0f,
            MENU_HEIGHT,
            SCREEN_WIDTH,
            HEALTH_BAR_HEIGHT,
            level.assets.health,
            level.assets.healthBackground,
            level.health
    )
    private val defenderMenu: DefenderMenu = DefenderMenu(
            0f,
            0f,
            SCREEN_WIDTH,
            MENU_HEIGHT,
            level.assets.menuBackground,
            level.defenderTypes
    )
    private val balance: Balance = Balance(
            SCREEN_WIDTH / 64,
            SCREEN_HEIGHT - SCREEN_HEIGHT / 16,
            FontHelper.toFont(DEFAULT_FONT, FontParams(20, Color.WHITE)),
            level.initialBalance
    )
    private val timer: SecondsTimer = SecondsTimer(
            SCREEN_WIDTH - SCREEN_WIDTH / 16,
            SCREEN_HEIGHT - SCREEN_HEIGHT / 16,
            FontHelper.toFont(DEFAULT_FONT, FontParams(20, Color.WHITE)),
            level.timeInSeconds
    )

    init {
        game.stage.keyboardFocus = gameArea
        gameArea.listeners.add(object : DragListener() {
            override fun drag(event: InputEvent?, x: Float, y: Float, pointer: Int) {
                val camera = game.stage.viewport.camera
                val cameraDeltaY = if (deltaY < 0f) {
                    min(-deltaY, max(gameArea.topY -
                            (camera.position.y + camera.viewportHeight / 2), 0f))
                } else {
                    -min(deltaY, max((camera.position.y -
                            camera.viewportHeight / 2) - gameArea.bottomY, 0f))
                }

                camera.translate(0f, cameraDeltaY, 0f)
                defenderMenu.moveBy(0f, cameraDeltaY)
                health.moveBy(0f, cameraDeltaY)
                balance.moveBy(0f, cameraDeltaY)
                timer.moveBy(0f, cameraDeltaY)
                super.drag(event, x, y, pointer)
            }
        })
        gameArea.listeners.add(object : InputListener() {
            override fun keyDown(event: InputEvent?, keycode: Int): Boolean {
                if (Input.Keys.Q == keycode) {
                    gameArea.defenders.forEachIndexed { i, defender ->
                        if (defender.isHighlighted) {
                            balance.plus(defender.cost)
                            gameArea.defenders.removeIndex(i)
                            defender.remove()
                        }
                    }
                }

                return super.keyDown(event, keycode)
            }

            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                gameArea.defenders.forEach { defender ->
                    defender.isHighlighted = false
                    if (defender.isContains(x, y)) {
                        defender.isHighlighted = true
                    }
                }
                return super.touchDown(event, x, y, pointer, button)
            }
        })

        val defenderMenuDragAndDrop = DragAndDrop()
        defenderMenuDragAndDrop.setKeepWithinStage(false)
        defenderMenuDragAndDrop.addSource(object : DragAndDrop.Source(defenderMenu) {
            override fun dragStart(event: InputEvent, x: Float,
                                   y: Float, pointer: Int): DragAndDrop.Payload {
                val payload = DragAndDrop.Payload()
                val itemDefender: DefenderMenuItem = (actor as DefenderMenu).getItem(x, y)
                        ?: return payload
                val defender = DefenderPreview(
                        x,
                        y,
                        itemDefender.value,
                        level.assets.zone
                )
                if (balance.hasBalance(defender.cost)) {
                    payload.dragActor = defender
                    addActor(defender)
                }
                return payload
            }

            override fun dragStop(event: InputEvent?, x: Float, y: Float, pointer: Int,
                                  payload: DragAndDrop.Payload?, target: DragAndDrop.Target?) {
                val defender = payload?.dragActor as DefenderPreview? ?: return
                defender.remove()
            }
        })
        defenderMenuDragAndDrop.addTarget(object : DragAndDrop.Target(gameArea) {
            override fun drag(source: DragAndDrop.Source?, payload: DragAndDrop.Payload?, x: Float,
                              y: Float, pointer: Int): Boolean {
                val defender = payload?.dragActor as DefenderPreview? ?: return false

                val available = !defenderMenu.isCollides(defender) &&
                        !gameArea.isCollides(defender)

                if (available != defender.isAvailable) {
                    defender.isAvailable = available
                }

                return available
            }

            override fun drop(source: DragAndDrop.Source?, payload: DragAndDrop.Payload?, x: Float,
                              y: Float, pointer: Int) {
                val defenderPreview = payload?.dragActor as DefenderPreview? ?: return
                if (balance.hasBalance(defenderPreview.cost)) {
                    gameArea.defenders.forEach { defender -> defender.isHighlighted = false }
                    gameArea.addDefender(defenderPreview.x, defenderPreview.y, defenderPreview)
                    balance.minus(defenderPreview.cost)
                }
            }
        })

        init()
    }

    fun init(): GameScreen {
        defenderMenu.remove()
        gameArea.remove()
        balance.remove()

        val areaMap = areaMapGenerator.generate(
                (SCREEN_WIDTH / BLOCK_SIZE).toInt(),
                level.horizontalBlockCount
        )

        val area = Array<AreaBlock>()
        var block: AreaBlock? = null
        var i = areaMap.startPositionX
        var j = areaMap.startPositionY
        while (block?.type != AreaType.END || block.type == AreaType.NONE) {
            val areaType = areaMap.value[i][j]
            val previousType = block?.type ?: AreaType.NONE
            block = AreaBlock(
                    i * BLOCK_SIZE,
                    j * BLOCK_SIZE + GAME_UI_HEIGHT,
                    BLOCK_SIZE,
                    areaType,
                    previousType,
                    level.assets.block,
                    BLOCK_ZONE_SIZE,
                    level.assets.zone
            )
            area.add(block)

            when (areaType) {
                AreaType.LEFT -> {
                    i--
                }
                AreaType.RIGHT -> {
                    i++
                }
                AreaType.DOWN -> {
                    j--
                }
                AreaType.UP -> {
                    j++
                }
            }
        }

        areaMap.value.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { typeIndex, type ->
                if (type == AreaType.NONE) {
                    block = AreaBlock(
                            rowIndex * BLOCK_SIZE,
                            typeIndex * BLOCK_SIZE + GAME_UI_HEIGHT,
                            BLOCK_SIZE,
                            type,
                            AreaType.NONE,
                            level.assets.background,
                            BLOCK_ZONE_SIZE,
                            level.assets.zone
                    )
                    area.add(block)
                }
            }
        }

        startPositionX = areaMap.startPositionX * BLOCK_SIZE
        startPositionY = areaMap.startPositionY * BLOCK_SIZE

        gameArea.init(area)
        addActor(gameArea)

        health.init(
                0f,
                MENU_HEIGHT,
                SCREEN_WIDTH,
                HEALTH_BAR_HEIGHT,
                level.assets.health,
                level.assets.healthBackground,
                level.health
        )
        addActor(health)

        defenderMenu.init(
                0f,
                0f,
                SCREEN_WIDTH,
                MENU_HEIGHT,
                level.assets.menuBackground,
                level.defenderTypes
        )
        addActor(defenderMenu)

        balance.init(
                SCREEN_WIDTH / 64,
                SCREEN_HEIGHT - SCREEN_HEIGHT / 16,
                FontHelper.toFont(DEFAULT_FONT, FontParams(20, Color.WHITE)),
                level.initialBalance
        )
        addActor(balance)

        timer.init(
                SCREEN_WIDTH - SCREEN_WIDTH / 16,
                SCREEN_HEIGHT - SCREEN_HEIGHT / 16,
                FontHelper.toFont(DEFAULT_FONT, FontParams(20, Color.WHITE)),
                level.timeInSeconds
        )
        timer.scheduleAction{
            val event = level.getEvent(timer.value)
            if (TimeEventType.GENERATE_ENEMY == event?.type) {
                val enemyData = (event as GenerateEnemyTimeEvent).data
                gameArea.addEnemy(startPositionX, startPositionY + GAME_UI_HEIGHT, enemyData)
            }
        }
        addActor(timer)

        return this
    }

    override fun stateCheck(delta: Float) {
        gameArea.enemies.forEachIndexed { i, enemy ->
            gameArea.defenders.forEach { defender ->
                if (defender.isCollidesZone(enemy)) {
                    enemy.hit(defender.power * delta)
                }
            }
            gameArea.area.forEach { block ->
                if (block.isCollidesZone(enemy)) {
                    enemy.changeDirection(block.type)
                }
            }
            if (enemy.isDead) {
                balance.plus(enemy.cost)
                gameArea.enemies.removeIndex(i)
                enemy.remove()
            }
            if (enemy.isPassed) {
                health.minus(enemy.power)
                gameArea.enemies.removeIndex(i)
                enemy.remove()
            }
        }

        if (!health.hasHealth()) {
            init()
        }

        if (timer.isCompleted && gameArea.enemies.size <= 0) {
            init()
        }
    }
}