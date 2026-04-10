package com.example.towergame.gdx

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.ExtendViewport

class GameScreen(private val game: MyGame) : Screen {
    companion object {
        const val WORLD_WIDTH = 480f
        const val WORLD_HEIGHT = 800f
    }
    private val camera = OrthographicCamera()
    private val viewport = ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera)
    private val touchPoint = Vector3()

    // stage + ui
    private val stage = Stage()
    private val skin = Skin(Gdx.files.internal("uiskin.json"))

    private val menuButton = TextButton("Menu", skin).apply {
        setSize(140f, 70f)
        setPosition(20f, WORLD_HEIGHT - 90f)
    }

    override fun show() {
        Gdx.input.inputProcessor = stage

        stage.addActor(menuButton)

        menuButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
//                game.setScreen(MainMenuScreen(game))
            }
        })
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(0.2f, 0.0f, 0.3f, 1f)

        camera.update()
        game.batch.projectionMatrix = camera.combined

        game.batch.begin()

//        camera.unproject(touchPoint.set(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f))

        game.batch.end()

        stage.act(delta)
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
        stage.viewport.update(width, height, true)
    }

    override fun pause() {}

    override fun resume() {}

    override fun hide() {}

    override fun dispose() {
        println("GameScreen disposed")
        stage.dispose()
        skin.dispose()
    }
}