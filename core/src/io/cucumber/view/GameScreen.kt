package io.cucumber.view

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.Timer
import io.cucumber.Game
import io.cucumber.actor.Defender
import io.cucumber.actor.Enemy
import io.cucumber.actor.preview.DefenderPreview
import io.cucumber.actor.road.RoadBlock
import io.cucumber.actor.road.RoadMap
import io.cucumber.actor.road.RoadType
import io.cucumber.actor.ui.Background
import io.cucumber.actor.ui.DefenderMenu
import io.cucumber.actor.ui.DefenderMenuItem
import io.cucumber.base.view.BaseScreen
import io.cucumber.storage.event.EventType
import io.cucumber.storage.model.Level
import io.cucumber.utils.constants.Constants.*
import io.cucumber.utils.generator.RoadMapGenerator

class GameScreen(
        game: Game,
        private val level: Level
) : BaseScreen(game) {

    private var health: Float = GAME_HEALTH
    private var timer: Int = level.length

    private val roadMapGenerator: RoadMapGenerator = RoadMapGenerator()
    private val defenders: Array<Defender> = Array()
    private val enemies: Array<Enemy> = Array()

    private lateinit var roadMap: RoadMap
    private val background: Background = Background(
            0f,
            SCREEN_HEIGHT / 8,
            SCREEN_WIDTH,
            SCREEN_HEIGHT - SCREEN_HEIGHT / 8,
            level.assets.background,
            Array()
    )
    private val defenderMenu: DefenderMenu = DefenderMenu(
            0f,
            0f,
            SCREEN_WIDTH,
            SCREEN_HEIGHT / 8,
            level.assets.menuBackground,
            level.defenderTypes
    )

    init {
        val dragAndDrop = DragAndDrop()
        dragAndDrop.addSource(object : DragAndDrop.Source(defenderMenu) {
            override fun dragStart(event: InputEvent, x: Float, y: Float, pointer: Int): DragAndDrop.Payload {
                val payload = DragAndDrop.Payload()
                val itemDefender: DefenderMenuItem = (actor as DefenderMenu).getItem(x, y) ?: return payload
                val actor = DefenderPreview(
                        x,
                        y,
                        itemDefender.value,
                        level.assets.zone
                )
                payload.dragActor = actor
                addActor(actor)
                return payload
            }

            override fun dragStop(event: InputEvent?, x: Float, y: Float, pointer: Int,
                                  payload: DragAndDrop.Payload?, target: DragAndDrop.Target?) {
                val defender = payload?.dragActor as DefenderPreview? ?: return
                defender.remove()
            }
        })
        dragAndDrop.addTarget(object : DragAndDrop.Target(background) {
            override fun drag(source: DragAndDrop.Source?, payload: DragAndDrop.Payload?, x: Float, y: Float, pointer: Int): Boolean {
                val defender = payload?.dragActor as DefenderPreview? ?: return false

                val available = !defenderMenu.isCollides(defender) &&
                        !enemies.any { it.isCollides(defender) } &&
                        !background.road.any { it.isCollides(defender) } &&
                        !defenders.any { it.isCollides(defender) }

                if (available != defender.isAvailable) {
                    defender.isAvailable = available
                }

                return available
            }

            override fun drop(source: DragAndDrop.Source?, payload: DragAndDrop.Payload?, x: Float, y: Float, pointer: Int) {
                val defenderPreview = payload?.dragActor as DefenderPreview? ?: return
                val defender = Defender(defenderPreview)
                defenders.add(defender)
                addActor(defender)
            }
        })

        Timer.schedule(object : Timer.Task() {
            override fun run() {
                val event = level.getEvent(level.length - timer)
                if (EventType.GENERATE_ENEMY == event?.eventType) {
                    val enemy = Enemy(
                            roadMap.startPositionX * BLOCK_SIZE,
                            roadMap.startPositionY * BLOCK_SIZE + SCREEN_HEIGHT / 8,
                            ENEMY_SIZE,
                            ENEMY_VELOCITY,
                            ENEMY_VELOCITY,
                            ENEMY_POWER,
                            ENEMY_HEALTH,
                            level.assets.enemy
                    )
                    enemies.add(enemy)
                    addActor(enemy)
                }
                timer--
            }
        }, 0f, 1f)


        init()
    }

    fun init(): GameScreen {
        defenderMenu.remove()
        background.remove()
        defenders.forEach { it.remove() }
        enemies.forEach { it.remove() }

        health = GAME_HEALTH
        timer = level.length

        roadMap = roadMapGenerator.generate(
                (SCREEN_WIDTH / BLOCK_SIZE).toInt(),
                ((SCREEN_HEIGHT - SCREEN_HEIGHT / 8) / BLOCK_SIZE).toInt()
        )

        val road = Array<RoadBlock>()
        roadMap.value.forEachIndexed { i, row ->
            row.forEachIndexed { j, roadType ->
                if (roadType != RoadType.NONE) {
                    val block = RoadBlock(
                            i * BLOCK_SIZE,
                            j * BLOCK_SIZE + SCREEN_HEIGHT / 8,
                            BLOCK_SIZE,
                            roadType,
                            level.assets.block,
                            BLOCK_ZONE_SIZE,
                            level.assets.zone
                    )
                    road.add(block)
                }
            }
        }

        background.init(
                0f,
                SCREEN_HEIGHT / 8,
                SCREEN_WIDTH,
                SCREEN_HEIGHT - SCREEN_HEIGHT / 8,
                level.assets.background,
                road
        )
        addActor(background)
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
        enemies.forEachIndexed { index, enemy ->
            defenders.forEach { defender ->
                if (defender.isCollidesZone(enemy)) {
                    enemy.hit(defender.power * Gdx.graphics.deltaTime)
                }
            }

            background.road.forEach { road ->
                if (road.isCollidesZone(enemy)) {
                    enemy.changeDirection(road.type)
                }
            }

            if (enemy.isDead) {
                enemies.removeIndex(index)
                enemy.remove()
            }

            if (enemy.isPassed) {
                health -= enemy.power
                enemies.removeIndex(index)
                enemy.remove()
            }
        }

        if (health <= 0) {
            init()
        }

        if (timer <= 0 && enemies.isEmpty) {
            init()
        }
    }
}