package io.cucumber.base.view

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Actor
import io.cucumber.Game


abstract class BaseScreen(
        protected val game: Game
) : ScreenAdapter() {

    override fun hide() {
        game.stage.clear()
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        game.stage.viewport.update(width, height, true)
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        game.stage.act(delta)
        game.stage.draw()
        stateCheck(delta)
        super.render(delta)
    }

    protected open fun stateCheck(delta: Float) {}

    protected fun addActor(actor: Actor) {
        game.stage.addActor(actor)
    }

    protected fun setScreen(screen: BaseScreen) {
        game.screen = screen
    }
}