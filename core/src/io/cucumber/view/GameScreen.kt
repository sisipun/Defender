package io.cucumber.view

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop
import com.badlogic.gdx.utils.Array
import io.cucumber.Game
import io.cucumber.base.model.bound.RectangleBound
import io.cucumber.base.view.BaseScreen
import io.cucumber.model.actor.Defender
import io.cucumber.model.actor.Enemy
import io.cucumber.model.actor.preview.DefenderPreview
import io.cucumber.model.actor.road.RoadBlock
import io.cucumber.model.actor.road.RoadMap
import io.cucumber.model.actor.road.RoadType
import io.cucumber.model.actor.ui.Background
import io.cucumber.model.actor.ui.Menu
import io.cucumber.model.actor.ui.MenuItem
import io.cucumber.model.level.Level
import io.cucumber.utils.constants.Constants.*
import io.cucumber.utils.generator.RoadMapGenerator

class GameScreen(
        game: Game,
        private val level: Level
) : BaseScreen(game) {

    private var health: Float = GAME_HEALTH

    private val roadMapGenerator: RoadMapGenerator = RoadMapGenerator()
    private val defenders: Array<Defender> = Array()

    private lateinit var roadMap: RoadMap
    private val enemy: Enemy = Enemy(
            0f,
            0f,
            ENEMY_SIZE,
            ENEMY_VELOCITY,
            ENEMY_VELOCITY,
            ENEMY_POWER,
            ENEMY_HEALTH,
            level.assets.enemy
    )
    private val background: Background = Background(
            0f,
            SCREEN_HEIGHT / 8,
            SCREEN_WIDTH,
            SCREEN_HEIGHT - SCREEN_HEIGHT / 8,
            level.assets.background,
            Array()
    )
    private val menu: Menu = Menu(
            RectangleBound(0f, 0f, SCREEN_WIDTH, SCREEN_HEIGHT / 8),
            level.assets.menuBackground,
            level.defenderTypes
    )

    init {
        val dragAndDrop = DragAndDrop()
        dragAndDrop.addSource(object : DragAndDrop.Source(menu) {
            override fun dragStart(event: InputEvent, x: Float, y: Float, pointer: Int): DragAndDrop.Payload {
                val payload = DragAndDrop.Payload()
                val item: MenuItem = (actor as Menu).getItem(x, y) ?: return payload
                val actor = DefenderPreview(
                        x,
                        y,
                        item.defenderData,
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

                val available = !menu.isCollides(defender) &&
                        !enemy.isCollides(defender) &&
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


        init()
    }

    fun init(): GameScreen {
        enemy.remove()
        menu.remove()
        background.remove()
        defenders.forEach { it.remove() }

        health = GAME_HEALTH

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

        enemy.init(
                roadMap.startPositionX * BLOCK_SIZE,
                roadMap.startPositionY * BLOCK_SIZE + SCREEN_HEIGHT / 8,
                ENEMY_SIZE,
                ENEMY_VELOCITY,
                ENEMY_VELOCITY,
                ENEMY_POWER,
                ENEMY_HEALTH,
                level.assets.enemy
        )
        addActor(enemy)

        menu.init(
                RectangleBound(0f, 0f, SCREEN_WIDTH, SCREEN_HEIGHT / 8),
                level.assets.menuBackground,
                level.defenderTypes
        )
        addActor(menu)

        return this
    }

    override fun stateCheck() {
        defenders.forEach {
            if (it.isCollidesZone(enemy)) {
                enemy.hit(it.power * Gdx.graphics.deltaTime)
            }
        }

        background.road.forEach {
            if (it.isCollidesZone(enemy)) {
                enemy.changeDirection(it.type)
            }
        }

        if (enemy.isDead) {
            enemy.init(
                    roadMap.startPositionX * BLOCK_SIZE,
                    roadMap.startPositionY * BLOCK_SIZE + SCREEN_HEIGHT / 8,
                    ENEMY_SIZE,
                    ENEMY_VELOCITY,
                    ENEMY_VELOCITY,
                    ENEMY_POWER,
                    ENEMY_HEALTH,
                    level.assets.enemy
            )
        }

        if (enemy.isPassed) {
            health -= enemy.power
            enemy.init(
                    roadMap.startPositionX * BLOCK_SIZE,
                    roadMap.startPositionY * BLOCK_SIZE + SCREEN_HEIGHT / 8,
                    ENEMY_SIZE,
                    ENEMY_VELOCITY,
                    ENEMY_VELOCITY,
                    ENEMY_POWER,
                    ENEMY_HEALTH,
                    level.assets.enemy
            )
        }

        if (health <= 0) {
            init()
        }
    }
}