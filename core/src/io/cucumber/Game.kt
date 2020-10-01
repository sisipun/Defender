package io.cucumber

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.StretchViewport
import io.cucumber.manager.LevelManager
import io.cucumber.utils.constants.Constants.SCREEN_HEIGHT
import io.cucumber.utils.constants.Constants.SCREEN_WIDTH
import io.cucumber.view.GameScreen

class Game : Game() {

    lateinit var stage: Stage
    lateinit var levelManager: LevelManager

    override fun create() {
        val screenViewport = StretchViewport(SCREEN_WIDTH, SCREEN_HEIGHT)
        stage = Stage(screenViewport)
        Gdx.input.inputProcessor = stage

        levelManager = LevelManager()
        setScreen(GameScreen(this, levelManager.loadLevel()))
    }

    override fun dispose() {
        levelManager.removeLevel()
        stage.dispose()
    }
}