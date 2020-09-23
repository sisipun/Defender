package io.cucumber

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.Timer
import com.badlogic.gdx.utils.viewport.StretchViewport
import io.cucumber.base.model.bound.RectangleBound
import io.cucumber.base.model.simple.SimpleRectangle
import io.cucumber.manager.LevelManager
import io.cucumber.model.Menu
import io.cucumber.model.MenuItem
import io.cucumber.utils.constants.Constants.*
import io.cucumber.utils.generator.GameMap
import io.cucumber.utils.generator.MapGenerator
import java.lang.String

class Game : Game() {

    lateinit var stage: Stage
    lateinit var menu: Menu
    lateinit var map: GameMap
    lateinit var enemy: SimpleRectangle

    override fun create() {
        val screenViewport = StretchViewport(SCREEN_WIDTH, SCREEN_HEIGHT)
        stage = Stage(screenViewport)
        Gdx.input.inputProcessor = stage

        LevelManager.loadLevels()
        val assets = LevelManager.get()

        val generator = MapGenerator()
        map = generator.generate((SCREEN_WIDTH / BLOCK_SIZE).toInt(), ((SCREEN_HEIGHT - SCREEN_HEIGHT / 8) / BLOCK_SIZE).toInt())

        map.map.forEachIndexed { i, row ->
            row.forEachIndexed { j, block ->
                if (block != GameMap.Direction.NONE) {
                    stage.addActor(SimpleRectangle(i * BLOCK_SIZE, j * BLOCK_SIZE + SCREEN_HEIGHT / 8, BLOCK_SIZE, BLOCK_SIZE, assets.block))
                }
            }
        }

        var enemyX = map.startPositionX
        var enemyY = map.startPositionY
        enemy = SimpleRectangle(enemyX * BLOCK_SIZE, enemyY * BLOCK_SIZE + SCREEN_HEIGHT / 8, BLOCK_SIZE, BLOCK_SIZE, assets.enemy)
        stage.addActor(enemy)

        menu = Menu(
                RectangleBound(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 16, SCREEN_WIDTH, SCREEN_HEIGHT / 8),
                assets.menuBackground,
                Array.with(assets.hero, assets.enemy, assets.hero, assets.hero, assets.enemy)
        )
        stage.addActor(menu)

        val dragAndDrop = DragAndDrop()
        dragAndDrop.addSource(object : DragAndDrop.Source(menu) {
            override fun dragStart(event: InputEvent, x: Float, y: Float, pointer: Int): Payload {
                val payload = Payload()
                val item: MenuItem = (actor as Menu).getItem(x, y) ?: return payload
                val actor = SimpleRectangle(item.x, item.y, BLOCK_SIZE, BLOCK_SIZE, item.region)
                stage.addActor(actor)
                payload.dragActor = actor
                return payload
            }

            override fun dragStop(event: InputEvent?, x: Float, y: Float, pointer: Int, payload: Payload?, target: DragAndDrop.Target?) {
                if (menu.isContains(x, y)) {
                    payload?.dragActor?.remove()
                }
            }
        })

        Timer.schedule(object : Timer.Task() {
            override fun run() {
                when (map.map[enemyX][enemyY]) {
                    GameMap.Direction.DOWN -> {
                        enemyY--
                    }
                    GameMap.Direction.LEFT -> {
                        enemyX--
                    }
                    GameMap.Direction.RIGHT -> {
                        enemyX++
                    }
                    GameMap.Direction.UP -> {
                        enemyY++
                    }
                }

                if (enemyX == map.endPositionX && enemyY == map.endPositionY) {
                    enemyX = map.startPositionX
                    enemyY = map.startPositionY
                }

                enemy.x = enemyX * BLOCK_SIZE
                enemy.y = enemyY * BLOCK_SIZE + SCREEN_HEIGHT / 8
            }
        }, .5f, .5f)
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.act()
        stage.draw()
    }

    override fun dispose() {
        LevelManager.removeLevels()
        stage.dispose()
    }
}