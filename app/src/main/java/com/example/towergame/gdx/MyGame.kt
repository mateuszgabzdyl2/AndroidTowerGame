package com.example.towergame.gdx

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.example.towergame.gdx.gamescreen.GameScreen

class MyGame(
    private val onGameOver: () -> Unit
) : Game() {
    lateinit var batch: SpriteBatch

    override fun create() {
        batch = SpriteBatch()
        setScreen(GameScreen(this, onGameOver))
    }

    override fun render() {
        super.render()
    }

    override fun dispose() {
        batch.dispose()
    }
}