package io.cucumber

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.viewport.StretchViewport
import io.cucumber.base.model.bound.RectangleBound
import io.cucumber.base.model.simple.SimpleRectangle
import io.cucumber.manager.LevelManager
import io.cucumber.model.Menu
import io.cucumber.model.MenuItem
import io.cucumber.utils.constants.Constants.SCREEN_HEIGHT
import io.cucumber.utils.constants.Constants.SCREEN_WIDTH

class Game : Game() {

    lateinit var stage: Stage
    lateinit var menu: Menu

    override fun create() {
        val screenViewport = StretchViewport(SCREEN_WIDTH, SCREEN_HEIGHT)
        stage = Stage(screenViewport)
        Gdx.input.inputProcessor = stage

        LevelManager.loadLevels()
        val assets = LevelManager.get()

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
                val actor = SimpleRectangle(item.x, item.y, item.width, item.height, item.region)
                payload.dragActor = actor
                Gdx.app.log("drag", "start")
                return payload
            }

            override fun dragStop(event: InputEvent?, x: Float, y: Float, pointer: Int, payload: Payload?, target: DragAndDrop.Target?) {
                payload?.dragActor?.remove()
                Gdx.app.log("drag", "end")
            }
        })
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