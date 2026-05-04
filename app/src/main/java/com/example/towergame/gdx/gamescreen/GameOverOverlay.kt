package com.example.towergame.gdx.gamescreen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener

class GameOverOverlay(
    private val stage: Stage,
    private val onRestart: () -> Unit,
    private val onMenu: () -> Unit
) {

    private val skin = Skin(Gdx.files.internal("uiskin.json"))
    private val window: Window
    private val scoreLabel: Label
    var isVisible = false
        private set

    init {
        window = Window("Game Over", skin)
        window.isVisible = false
        window.setSize(400f, 300f)

        scoreLabel = Label("Score: 0", skin)

        val restartBtn = TextButton("Restart", skin)
        val menuBtn = TextButton("Main Menu", skin)

        restartBtn.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                onRestart()
            }
        })

        menuBtn.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                onMenu()
            }
        })

        window.add(scoreLabel).pad(10f).row()
        window.add(restartBtn).pad(10f).row()
        window.add(menuBtn).pad(10f)

        stage.addActor(window)
    }

    fun show(score: Int) {
        scoreLabel.setText("Score: $score")
        window.isVisible = true
        isVisible = true

        center()
    }

    private fun center() {
        window.setPosition(
            stage.viewport.worldWidth / 2f - window.width / 2f,
            stage.viewport.worldHeight / 2f - window.height / 2f
        )
    }

    fun resize() {
        center()
    }

    fun dispose() {
        skin.dispose()
    }
}