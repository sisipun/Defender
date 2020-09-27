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
import io.cucumber.model.asset.LevelAssets
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
    lateinit var assets: LevelAssets
    lateinit var menu: Menu
    lateinit var roadMap: RoadMap
    lateinit var enemy: Enemy
    lateinit var defenders: Array<Defender>
    lateinit var road: Array<RoadBlock>

    override fun create() {
        val screenViewport = StretchViewport(SCREEN_WIDTH, SCREEN_HEIGHT)
        stage = Stage(screenViewport)
        Gdx.input.inputProcessor = stage

        LevelManager.loadLevels()
        assets = LevelManager.get()

        road = Array()

        val generator = RoadMapGenerator()
        roadMap = generator.generate((SCREEN_WIDTH / BLOCK_SIZE).toInt(), ((SCREEN_HEIGHT - SCREEN_HEIGHT / 8) / BLOCK_SIZE).toInt())

        roadMap.value.forEachIndexed { i, row ->
            row.forEachIndexed { j, roadType ->
                if (roadType != RoadType.NONE) {
                    val block = RoadBlock(i * BLOCK_SIZE, j * BLOCK_SIZE + SCREEN_HEIGHT / 8,
                            BLOCK_SIZE, assets.block, BLOCK_SIZE / 16f, assets.menuBackground, roadType)
                    road.add(block)
                    stage.addActor(block)
                }
            }
        }

        enemy = Enemy(roadMap.startPositionX * BLOCK_SIZE, roadMap.startPositionY * BLOCK_SIZE + SCREEN_HEIGHT / 8, ENEMY_SIZE,
                ENEMY_VELOCITY, ENEMY_VELOCITY, assets.enemy, ENEMY_HEALTH)
        stage.addActor(enemy)

        menu = Menu(
                RectangleBound(0f, 0f, SCREEN_WIDTH, SCREEN_HEIGHT / 8),
                assets.menuBackground,
                Array.with(assets.hero, assets.enemy, assets.hero, assets.hero, assets.enemy)
        )
        stage.addActor(menu)

        defenders = Array()

        val dragAndDrop = DragAndDrop()
        dragAndDrop.addSource(object : DragAndDrop.Source(menu) {
            override fun dragStart(event: InputEvent, x: Float, y: Float, pointer: Int): Payload {
                val payload = Payload()
                val item: MenuItem = (actor as Menu).getItem(x, y) ?: return payload
                val actor = Defender(x, y, DEFENDER_SIZE, item.region, DEFENDER_COLLIDER_SIZE, assets.menuBackground, DEFENDER_POWER)
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
                if (menu.isContains(x, y)) {
                    payload?.dragActor?.remove()
                } else {
                    payload?.let {
                        val defender = it.dragActor as Defender
                        it.dragActor.x = AlignHelper.computeX(it.dragActor.x, it.dragActor.width, HorizontalAlign.LEFT)
                        it.dragActor.y = y
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
                enemy.hit(it.power)
            }
        }

        road.forEach {
            if (it.isCollidesZone(enemy)) {
                enemy.changeDirection(it.type)
            }
        }

        if (enemy.isDead) {
            enemy.init(roadMap.startPositionX * BLOCK_SIZE, roadMap.startPositionY * BLOCK_SIZE + SCREEN_HEIGHT / 8,
                    ENEMY_SIZE, ENEMY_VELOCITY, ENEMY_VELOCITY, assets.enemy, ENEMY_HEALTH)
        }
    }

    override fun dispose() {
        LevelManager.removeLevels()
        stage.dispose()
    }
}