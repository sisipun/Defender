package io.cucumber

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.viewport.StretchViewport
import io.cucumber.base.model.bound.RectangleBound
import io.cucumber.base.model.simple.SimpleRectangle
import io.cucumber.utils.constants.Constants.SCREEN_HEIGHT
import io.cucumber.utils.constants.Constants.SCREEN_WIDTH
import io.cucumber.manager.LevelManager
import io.cucumber.model.Menu
import io.cucumber.model.MenuItem

class Game : Game() {

    lateinit var stage: Stage
    lateinit var hero: Menu

    override fun create() {
        val screenViewport = StretchViewport(SCREEN_WIDTH, SCREEN_HEIGHT)
        stage = Stage(screenViewport)
        LevelManager.loadLevels()
        val assets = LevelManager.get()
        hero = Menu(
                RectangleBound(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 16, SCREEN_WIDTH, SCREEN_HEIGHT / 8),
                assets.menuBackground,
                Array.with(MenuItem(assets.hero), MenuItem(assets.enemy), MenuItem(assets.hero), MenuItem(assets.hero), MenuItem(assets.enemy))
        )
        stage.addActor(hero)
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