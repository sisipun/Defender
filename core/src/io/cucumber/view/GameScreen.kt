package io.cucumber.view

import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop
import com.badlogic.gdx.scenes.scene2d.utils.DragListener
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.Pools
import io.cucumber.Game
import io.cucumber.actor.area.AreaBlock
import io.cucumber.actor.area.GameArea
import io.cucumber.actor.ui.Balance
import io.cucumber.actor.ui.Health
import io.cucumber.actor.ui.LevelTimer
import io.cucumber.actor.ui.menu.DefenderMenu
import io.cucumber.actor.ui.menu.DefenderMenuItem
import io.cucumber.actor.ui.menu.DefenderPreview
import io.cucumber.base.helper.FontHelper
import io.cucumber.base.helper.FontParams
import io.cucumber.base.view.BaseScreen
import io.cucumber.manager.Level
import io.cucumber.manager.event.GenerateEnemyTimeEvent
import io.cucumber.manager.event.IncreaseBalanceTimeEvent
import io.cucumber.manager.event.TimeEventType
import io.cucumber.utils.constants.Constants.*
import io.cucumber.utils.generator.AreaMapGenerator
import kotlin.math.max
import kotlin.math.min

class GameScreen(
        game: Game,
        private val level: Level
) : BaseScreen(game) {

    private var initialCameraY: Float = game.stage.camera.position.y

    private val areaMapGenerator: AreaMapGenerator = AreaMapGenerator()

    private val gameArea: GameArea = GameArea()
    private val defenderMenu: DefenderMenu = DefenderMenu()

    private val health: Health = Health()
    private val balance: Balance = Balance()
    private val levelTimer: LevelTimer = LevelTimer()

    init {
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
                levelTimer.moveBy(0f, cameraDeltaY)
                super.drag(event, x, y, pointer)
            }
        })
        game.stage.addListener(object : InputListener() {
            override fun keyDown(event: InputEvent?, keycode: Int): Boolean {
                if (Input.Keys.BACKSPACE == keycode) {
                    gameArea.defenders.forEachIndexed { i, defender ->
                        if (defender.isHighlighted) {
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
                                   y: Float, pointer: Int): DragAndDrop.Payload? {
                val itemDefender: DefenderMenuItem? = (actor as DefenderMenu).getItem(x, y)
                if (itemDefender == null || !balance.hasBalance(itemDefender.value.cost)) {
                    return null
                }

                val defender = Pools.obtain(DefenderPreview::class.java).init(
                        x,
                        y,
                        itemDefender.value
                )
                addActor(defender)

                val payload = DragAndDrop.Payload()
                payload.dragActor = defender
                return payload
            }

            override fun dragStop(event: InputEvent?, x: Float, y: Float, pointer: Int,
                                  payload: DragAndDrop.Payload?, target: DragAndDrop.Target?) {
                val defender = payload?.dragActor as DefenderPreview? ?: return
                defender.remove()
                Pools.free(defender)
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
                val data = defenderPreview.data
                if (balance.hasBalance(data.cost)) {
                    gameArea.defenders.forEach { defender -> defender.isHighlighted = false }
                    gameArea.addDefender(defenderPreview.x, defenderPreview.y, data)
                    balance.minus(data.cost)
                }
            }
        })

        init()
    }

    fun init(): GameScreen {
        defenderMenu.remove()
        gameArea.remove()
        health.remove()
        balance.remove()
        levelTimer.remove()

        game.stage.viewport.camera.position.y = initialCameraY

        val areaMap = areaMapGenerator.generate(
                (SCREEN_WIDTH / BLOCK_SIZE).toInt(),
                level.horizontalBlockCount + MAP_BORDER_SIZE,
                MAP_BORDER_SIZE
        )

        val area = Array<AreaBlock>()

        areaMap.blocks.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { typeIndex, block ->
                area.add(Pools.obtain(AreaBlock::class.java).init(
                        rowIndex * BLOCK_SIZE,
                        typeIndex * BLOCK_SIZE + GAME_UI_HEIGHT,
                        BLOCK_SIZE,
                        block,
                        level.assets.areaTextures[block],
                        BLOCK_ZONE_SIZE,
                        level.assets.zone
                ))
            }
        }

        areaMap.remove()

        gameArea.init(
                0f,
                0f,
                area
        )
        addActor(gameArea)

        health.init(
                0f,
                MENU_HEIGHT,
                SCREEN_WIDTH,
                HEALTH_HEIGHT,
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

        levelTimer.init(
                SCREEN_WIDTH / 8,
                SCREEN_HEIGHT - SCREEN_HEIGHT / 16 + SCREEN_HEIGHT / 64,
                SCREEN_WIDTH - SCREEN_WIDTH / 4,
                TIMER_HEIGHT,
                level.assets.timer,
                level.assets.timerBackground,
                level.timeInSeconds,
                level.events,
                TIMER_EVENT_SIZE,
                level.assets.timeEventTextures
        )
        levelTimer.addListener(TimeEventType.GENERATE_ENEMY) { event ->
            val enemyData = (event as GenerateEnemyTimeEvent).data
            gameArea.addEnemy(
                    areaMap.startPositionX * BLOCK_SIZE,
                    areaMap.startPositionY * BLOCK_SIZE + GAME_UI_HEIGHT,
                    enemyData
            )
        }
        levelTimer.addListener(TimeEventType.INCREASE_BALANCE) { event ->
            val value = (event as IncreaseBalanceTimeEvent).value
            balance.plus(value)
        }
        addActor(levelTimer)

        levelTimer.start()
        return this
    }

    override fun stateCheck(delta: Float) {
        gameArea.enemies.forEachIndexed { i, enemy ->
            gameArea.defenders.forEach { defender ->
                if (defender.isCollidesZone(enemy)) {
                    defender.shoot(enemy)
                }
            }
            gameArea.area.forEach { block ->
                if (block.isCollidesZone(enemy)) {
                    enemy.changeDirection(block.type)
                }
            }
            if (enemy.isDead) {
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

        if (levelTimer.isCompleted && gameArea.enemies.size <= 0) {
            init()
        }
    }
}