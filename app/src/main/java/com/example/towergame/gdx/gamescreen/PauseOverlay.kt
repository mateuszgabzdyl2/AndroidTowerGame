package com.example.towergame.gdx.gamescreen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable

class PauseOverlay(
    private val stage: Stage,
    private val onResume: () -> Unit,
    private val onRestart: () -> Unit,
    private val onMenu: () -> Unit
) {

    private val skin = Skin(Gdx.files.internal("uiskin.json"))
    private val pauseTexture = Texture("pause.png")

    private val pauseWindow: Window
    private val pauseButton: ImageButton

    var isPaused = false
        private set

    init {
        val drawable = TextureRegionDrawable(TextureRegion(pauseTexture))
        pauseButton = ImageButton(drawable)

        pauseButton.setSize(100f, 100f)
        pauseButton.setPosition(
            stage.viewport.worldWidth - 120f,
            stage.viewport.worldHeight - 120f
        )

        pauseButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                show()
            }
        })

        stage.addActor(pauseButton)

        pauseWindow = Window("Paused", skin)
        pauseWindow.isVisible = false
        pauseWindow.setSize(400f, 300f)
        pauseWindow.setPosition(
            stage.viewport.worldWidth / 2f - 200f,
            stage.viewport.worldHeight / 2f - 150f
        )

        val resumeBtn = TextButton("Resume", skin)
        val restartBtn = TextButton("Restart", skin)
        val menuBtn = TextButton("Main Menu", skin)

        resumeBtn.addListener {
            hide()
            onResume()
            true
        }

        restartBtn.addListener {
            onRestart()
            true
        }

        menuBtn.addListener {
            onMenu()
            true
        }

        pauseWindow.add(resumeBtn).pad(10f).row()
        pauseWindow.add(restartBtn).pad(10f).row()
        pauseWindow.add(menuBtn).pad(10f)

        stage.addActor(pauseWindow)
    }

    fun show() {
        isPaused = true
        pauseWindow.isVisible = true
    }

    fun hide() {
        isPaused = false
        pauseWindow.isVisible = false
    }

    fun resize() {
        pauseButton.setPosition(
            stage.viewport.worldWidth - 120f,
            stage.viewport.worldHeight - 120f
        )

        pauseWindow.setPosition(
            stage.viewport.worldWidth / 2f - pauseWindow.width / 2f,
            stage.viewport.worldHeight / 2f - pauseWindow.height / 2f
        )
    }

    fun dispose() {
        skin.dispose()
        pauseTexture.dispose()
    }
}