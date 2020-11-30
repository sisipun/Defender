package io.cucumber.view

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.Timer
import io.cucumber.Game
import io.cucumber.actor.preview.DefenderPreview
import io.cucumber.actor.road.RoadBlock
import io.cucumber.actor.road.RoadType
import io.cucumber.actor.ui.DefenderMenu
import io.cucumber.actor.ui.DefenderMenuItem
import io.cucumber.actor.ui.GameZone
import io.cucumber.base.view.BaseScreen
import io.cucumber.manager.Level
import io.cucumber.manager.event.GenerateEnemyTimeEvent
import io.cucumber.manager.event.TimeEventType
import io.cucumber.utils.constants.Constants.*
import io.cucumber.utils.generator.RoadMapGenerator

class GameScreen(
        game: Game,
        private val level: Level
) : BaseScreen(game) {

    private var timer: Int = level.length

    private val roadMapGenerator: RoadMapGenerator = RoadMapGenerator()

    private val gameZone: GameZone = GameZone(level.health, level.initialBalance)
    private val defenderMenu: DefenderMenu = DefenderMenu(
            0f,
            0f,
            SCREEN_WIDTH,
            SCREEN_HEIGHT / 8,
            level.assets.menuBackground,
            level.defenderTypes
    )

    init {
        val defenderMenuDragAndDrop = DragAndDrop()
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
                if (gameZone.canAdd(defender)) {
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
        defenderMenuDragAndDrop.addTarget(object : DragAndDrop.Target(gameZone) {
            override fun drag(source: DragAndDrop.Source?, payload: DragAndDrop.Payload?, x: Float,
                              y: Float, pointer: Int): Boolean {
                val defender = payload?.dragActor as DefenderPreview? ?: return false

                val available = !defenderMenu.isCollides(defender) &&
                        !gameZone.isCollides(defender)

                if (available != defender.isAvailable) {
                    defender.isAvailable = available
                }

                return available
            }

            override fun drop(source: DragAndDrop.Source?, payload: DragAndDrop.Payload?, x: Float,
                              y: Float, pointer: Int) {
                val defenderPreview = payload?.dragActor as DefenderPreview? ?: return
                gameZone.addDefender(defenderPreview.x, defenderPreview.y, defenderPreview)
            }
        })

        Timer.schedule(object : Timer.Task() {
            override fun run() {
                val event = level.getEvent(level.length - timer)
                if (TimeEventType.GENERATE_ENEMY == event?.type) {
                    val enemyData = (event as GenerateEnemyTimeEvent).data
                    gameZone.addEnemy(gameZone.startPositionX, gameZone.startPositionY + SCREEN_HEIGHT / 8, enemyData)
                }
                timer--
            }
        }, 0f, 1f)


        init()
    }

    fun init(): GameScreen {
        defenderMenu.remove()
        gameZone.remove()

        timer = level.length

        val roadMap = roadMapGenerator.generate(
                (SCREEN_WIDTH / BLOCK_SIZE).toInt(),
                ((SCREEN_HEIGHT - SCREEN_HEIGHT / 8) / BLOCK_SIZE).toInt()
        )

        val road = Array<RoadBlock>()
        var block: RoadBlock? = null
        var i = roadMap.startPositionX
        var j = roadMap.startPositionY
        while (block?.type != RoadType.END || block.type == RoadType.NONE) {
            val roadType = roadMap.value[i][j]
            val previousType = block?.type ?: RoadType.NONE
            block = RoadBlock(
                    i * BLOCK_SIZE,
                    j * BLOCK_SIZE + SCREEN_HEIGHT / 8,
                    BLOCK_SIZE,
                    roadType,
                    previousType,
                    level.assets.block,
                    BLOCK_ZONE_SIZE,
                    level.assets.zone
            )
            road.add(block)

            when (roadType) {
                RoadType.LEFT -> {
                    i--
                }
                RoadType.RIGHT -> {
                    i++
                }
                RoadType.DOWN -> {
                    j--
                }
                RoadType.UP -> {
                    j++
                }
            }
        }

        roadMap.value.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { typeIndex, type ->
                if (type == RoadType.NONE) {
                    block = RoadBlock(
                            rowIndex * BLOCK_SIZE,
                            typeIndex * BLOCK_SIZE + SCREEN_HEIGHT / 8,
                            BLOCK_SIZE,
                            type,
                            RoadType.NONE,
                            level.assets.background,
                            BLOCK_ZONE_SIZE,
                            level.assets.zone
                    )
                    road.add(block)
                }
            }
        }

        gameZone.init(
                level.health,
                level.initialBalance,
                roadMap.startPositionX * BLOCK_SIZE,
                roadMap.startPositionY * BLOCK_SIZE,
                road
        )
        addActor(gameZone)
        defenderMenu.init(
                0f,
                0f,
                SCREEN_WIDTH,
                SCREEN_HEIGHT / 8,
                level.assets.menuBackground,
                level.defenderTypes
        )
        addActor(defenderMenu)

        return this
    }

    override fun stateCheck() {
        if (gameZone.isGameOver) {
            init()
        }

        if (timer <= 0 && gameZone.hasEnemies()) {
            init()
        }
    }
}