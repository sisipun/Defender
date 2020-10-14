package io.cucumber

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.StretchViewport
import io.cucumber.storage.LevelStorage
import io.cucumber.utils.constants.Constants.SCREEN_HEIGHT
import io.cucumber.utils.constants.Constants.SCREEN_WIDTH
import io.cucumber.view.GameScreen

class Game : Game() {

    lateinit var stage: Stage
    lateinit var levelStorage: LevelStorage

    override fun create() {
        val screenViewport = StretchViewport(SCREEN_WIDTH, SCREEN_HEIGHT)
        stage = Stage(screenViewport)
        Gdx.input.inputProcessor = stage

        levelStorage = LevelStorage()
        setScreen(GameScreen(this, levelStorage.init()))
    }

    override fun dispose() {
        levelStorage.removeLevel()
        stage.dispose()
    }
}