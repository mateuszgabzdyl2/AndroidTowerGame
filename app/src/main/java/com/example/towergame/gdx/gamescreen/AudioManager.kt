package com.example.towergame.gdx.gamescreen

import com.badlogic.gdx.Gdx

class AudioManager {

    val bgMusic = Gdx.audio.newMusic(Gdx.files.internal("music.ogg"))
    val jumpSound = Gdx.audio.newSound(Gdx.files.internal("jump3.ogg"))

    init {
        bgMusic.isLooping = true
        bgMusic.volume = 0.5f
    }

    fun playMusic() {
        bgMusic.play()
    }

    fun playJump() {
        jumpSound.play(0.7f)
    }

    fun dispose() {
        bgMusic.dispose()
        jumpSound.dispose()
    }
}