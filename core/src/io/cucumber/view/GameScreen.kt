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
import io.cucumber.model.actor.GameZone
import io.cucumber.model.actor.menu.Menu
import io.cucumber.model.actor.menu.MenuItem
import io.cucumber.model.actor.preview.DefenderPreview
import io.cucumber.model.actor.road.RoadBlock
import io.cucumber.model.actor.road.RoadMap
import io.cucumber.model.actor.road.RoadType
import io.cucumber.model.level.LevelData
import io.cucumber.utils.constants.Constants.*
import io.cucumber.utils.generator.RoadMapGenerator

class GameScreen(
        game: Game,
        private val levelData: LevelData
) : BaseScreen(game) {

    private val roadMapGenerator: RoadMapGenerator = RoadMapGenerator()
    private val defenders: Array<Defender> = Array()

    private lateinit var roadMap: RoadMap
    private val enemy: Enemy = Enemy(
            0f,
            0f,
            ENEMY_SIZE,
            ENEMY_VELOCITY,
            ENEMY_VELOCITY,
            levelData.assets.enemy,
            ENEMY_HEALTH
    )
    private val gameZone: GameZone = GameZone(
            0f,
            SCREEN_HEIGHT / 8,
            SCREEN_WIDTH,
            SCREEN_HEIGHT - SCREEN_HEIGHT / 8,
            levelData.assets.background,
            Array()
    )
    private val menu: Menu = Menu(
            RectangleBound(0f, 0f, SCREEN_WIDTH, SCREEN_HEIGHT / 8),
            levelData.assets.menuBackground,
            levelData.defenderSamples
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
                        item.defenderSample
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
        dragAndDrop.addTarget(object : DragAndDrop.Target(gameZone) {
            override fun drag(source: DragAndDrop.Source?, payload: DragAndDrop.Payload?, x: Float, y: Float, pointer: Int): Boolean {
                val defender = payload?.dragActor as DefenderPreview? ?: return false

                val available = !menu.isCollides(defender) &&
                        !enemy.isCollides(defender) &&
                        !gameZone.isCollidesRoad(defender) &&
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
        gameZone.remove()

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
                            levelData.assets.block,
                            roadType,
                            BLOCK_ZONE_SIZE,
                            BLOCK_ZONE_ALPHA,
                            levelData.assets.zone
                    )
                    road.add(block)
                }
            }
        }

        gameZone.init(
                0f,
                SCREEN_HEIGHT / 8,
                SCREEN_WIDTH,
                SCREEN_HEIGHT - SCREEN_HEIGHT / 8,
                levelData.assets.background,
                road
        )
        addActor(gameZone)

        enemy.init(
                roadMap.startPositionX * BLOCK_SIZE,
                roadMap.startPositionY * BLOCK_SIZE + SCREEN_HEIGHT / 8,
                ENEMY_SIZE,
                ENEMY_VELOCITY,
                ENEMY_VELOCITY,
                levelData.assets.enemy,
                ENEMY_HEALTH
        )
        addActor(enemy)

        menu.init(
                RectangleBound(0f, 0f, SCREEN_WIDTH, SCREEN_HEIGHT / 8),
                levelData.assets.menuBackground,
                levelData.defenderSamples
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

        gameZone.changeDirection(enemy)

        if (enemy.isDead) {
            enemy.init(
                    roadMap.startPositionX * BLOCK_SIZE,
                    roadMap.startPositionY * BLOCK_SIZE + SCREEN_HEIGHT / 8,
                    ENEMY_SIZE,
                    ENEMY_VELOCITY,
                    ENEMY_VELOCITY,
                    levelData.assets.enemy,
                    ENEMY_HEALTH
            )
        }
    }
}