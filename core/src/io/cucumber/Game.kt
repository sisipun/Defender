package io.cucumber

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.StretchViewport
import io.cucumber.base.model.simple.SimpleRectangle
import io.cucumber.base.utils.constants.Constants.SCREEN_HEIGHT
import io.cucumber.base.utils.constants.Constants.SCREEN_WIDTH
import io.cucumber.manager.LevelManager

class Game : Game() {

    lateinit var stage: Stage
    lateinit var hero: SimpleRectangle

    override fun create() {
        val screenViewport = StretchViewport(SCREEN_WIDTH, SCREEN_HEIGHT)
        stage = Stage(screenViewport)
        LevelManager.loadLevels()
        hero = SimpleRectangle(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, 50f, 50f, LevelManager.get().hero)
        stage.addActor(hero)
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.act()
        stage.draw()
    }

    override fun dispose() {
        hero.remove()
        stage.dispose()
    }
}