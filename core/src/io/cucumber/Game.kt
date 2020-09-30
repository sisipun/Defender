package io.cucumber

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.viewport.StretchViewport
import io.cucumber.base.helper.AlignHelper
import io.cucumber.base.helper.HorizontalAlign
import io.cucumber.base.model.bound.RectangleBound
import io.cucumber.manager.LevelManager
import io.cucumber.model.character.Defender
import io.cucumber.model.character.Enemy
import io.cucumber.model.road.RoadType
import io.cucumber.model.menu.Menu
import io.cucumber.model.menu.MenuItem
import io.cucumber.model.road.RoadBlock
import io.cucumber.utils.constants.Constants.*
import io.cucumber.utils.generator.RoadMap
import io.cucumber.utils.generator.RoadMapGenerator

class Game : Game() {

    lateinit var stage: Stage
    lateinit var levelManager: LevelManager
    lateinit var level: LevelManager.Level
    lateinit var menu: Menu
    lateinit var roadMap: RoadMap
    lateinit var enemy: Enemy
    lateinit var defenders: Array<Defender>
    lateinit var road: Array<RoadBlock>

    override fun create() {
        val screenViewport = StretchViewport(SCREEN_WIDTH, SCREEN_HEIGHT)
        stage = Stage(screenViewport)
        Gdx.input.inputProcessor = stage

        levelManager = LevelManager()
        level = levelManager.loadLevel()

        road = Array()

        val generator = RoadMapGenerator()
        roadMap = generator.generate(
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
                    stage.addActor(block)
                }
            }
        }

        enemy = Enemy(
                roadMap.startPositionX * BLOCK_SIZE,
                roadMap.startPositionY * BLOCK_SIZE + SCREEN_HEIGHT / 8,
                ENEMY_SIZE,
                ENEMY_VELOCITY,
                ENEMY_VELOCITY,
                level.assets.enemy,
                ENEMY_HEALTH
        )
        stage.addActor(enemy)

        menu = Menu(
                RectangleBound(0f, 0f, SCREEN_WIDTH, SCREEN_HEIGHT / 8),
                level.assets.menuBackground,
                Array.with(level.assets.defender)
        )
        stage.addActor(menu)

        defenders = Array()

        val dragAndDrop = DragAndDrop()
        dragAndDrop.addSource(object : DragAndDrop.Source(menu) {
            override fun dragStart(event: InputEvent, x: Float, y: Float, pointer: Int): Payload {
                val payload = Payload()
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
                stage.addActor(actor)
                payload.dragActor = actor
                return payload
            }

            override fun drag(event: InputEvent?, x: Float, y: Float, pointer: Int) {
                dragAndDrop.dragActor?.x = x
                dragAndDrop.dragActor?.y = y
                super.drag(event, x, y, pointer)
            }

            override fun dragStop(event: InputEvent?, x: Float, y: Float, pointer: Int, payload: Payload?, target: DragAndDrop.Target?) {
                if (payload != null && payload.dragActor != null) {
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
            }
        })
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.act()
        stage.draw()

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
    }

    override fun dispose() {
        levelManager.removeLevel()
        stage.dispose()
    }
}