package io.cucumber.view

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop
import com.badlogic.gdx.utils.Array
import io.cucumber.Game
import io.cucumber.base.helper.AlignHelper
import io.cucumber.base.helper.HorizontalAlign
import io.cucumber.base.model.bound.RectangleBound
import io.cucumber.base.view.BaseScreen
import io.cucumber.manager.LevelManager
import io.cucumber.model.character.Defender
import io.cucumber.model.character.Enemy
import io.cucumber.model.menu.Menu
import io.cucumber.model.menu.MenuItem
import io.cucumber.model.road.RoadBlock
import io.cucumber.model.road.RoadType
import io.cucumber.utils.constants.Constants.*
import io.cucumber.utils.generator.RoadMap
import io.cucumber.utils.generator.RoadMapGenerator

class GameScreen(
        game: Game,
        private val level: LevelManager.Level
) : BaseScreen(game) {

    private val roadMapGenerator: RoadMapGenerator = RoadMapGenerator()
    private val road: Array<RoadBlock> = Array()
    private val defenders: Array<Defender> = Array()

    private lateinit var roadMap: RoadMap
    private val enemy: Enemy = Enemy(
            0f,
            0f,
            ENEMY_SIZE,
            ENEMY_VELOCITY,
            ENEMY_VELOCITY,
            level.assets.enemy,
            ENEMY_HEALTH
    )
    private val menu: Menu = Menu(
            RectangleBound(0f, 0f, SCREEN_WIDTH, SCREEN_HEIGHT / 8),
            level.assets.menuBackground,
            Array.with(level.assets.defender)
    )

    init {
        val dragAndDrop = DragAndDrop()
        dragAndDrop.addSource(object : DragAndDrop.Source(menu) {
            override fun dragStart(event: InputEvent, x: Float, y: Float, pointer: Int): DragAndDrop.Payload {
                val payload = DragAndDrop.Payload()
                val item: MenuItem = (actor as Menu).getItem(x, y) ?: return payload
                val actor = Defender(
                        x,
                        y,
                        DEFENDER_SIZE,
                        item.region,
                        DEFENDER_POWER,
                        DEFENDER_ZONE_SIZE,
                        DEFENDER_ZONE_ALPHA,
                        level.assets.menuBackground
                )
                payload.dragActor = actor
                addActor(actor)
                return payload
            }

            override fun dragStop(event: InputEvent?, x: Float, y: Float, pointer: Int,
                                  payload: DragAndDrop.Payload?, target: DragAndDrop.Target?) {
                if (payload == null || payload.dragActor == null) {
                    return
                }

                val defender = payload.dragActor as Defender
                defender.x = AlignHelper.computeX(x, defender.width, HorizontalAlign.RIGHT)
                defender.y = y
                if (menu.isCollides(defender) ||
                        enemy.isCollides(defender) ||
                        road.any { it.isCollides(defender) } ||
                        defenders.any { it.isCollides(defender) }) {
                    defender.remove()
                } else {
                    defenders.add(defender)
                }
            }
        })

        init()
    }

    fun init() {
        enemy.remove()
        menu.remove()

        roadMap = roadMapGenerator.generate(
                (SCREEN_WIDTH / BLOCK_SIZE).toInt(),
                ((SCREEN_HEIGHT - SCREEN_HEIGHT / 8) / BLOCK_SIZE).toInt()
        )

        roadMap.value.forEachIndexed { i, row ->
            row.forEachIndexed { j, roadType ->
                if (roadType != RoadType.NONE) {
                    val block = RoadBlock(
                            i * BLOCK_SIZE,
                            j * BLOCK_SIZE + SCREEN_HEIGHT / 8,
                            BLOCK_SIZE,
                            level.assets.block,
                            roadType,
                            BLOCK_ZONE_SIZE,
                            BLOCK_ZONE_ALPHA,
                            level.assets.menuBackground
                    )
                    road.add(block)
                    addActor(block)
                }
            }
        }

        enemy.init(
                roadMap.startPositionX * BLOCK_SIZE,
                roadMap.startPositionY * BLOCK_SIZE + SCREEN_HEIGHT / 8,
                ENEMY_SIZE,
                ENEMY_VELOCITY,
                ENEMY_VELOCITY,
                level.assets.enemy,
                ENEMY_HEALTH
        )
        menu.init(
                RectangleBound(0f, 0f, SCREEN_WIDTH, SCREEN_HEIGHT / 8),
                level.assets.menuBackground,
                Array.with(level.assets.defender)
        )
    }

    override fun stateCheck() {
        defenders.forEach {
            if (it.isCollidesZone(enemy)) {
                enemy.hit(it.power * Gdx.graphics.deltaTime)
            }
        }

        road.forEach {
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
                    level.assets.enemy,
                    ENEMY_HEALTH
            )
        }

        addActor(enemy)
        addActor(menu)
    }
}