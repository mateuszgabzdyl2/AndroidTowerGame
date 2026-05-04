package com.example.towergame.gdx.gamescreen

import com.badlogic.gdx.graphics.Texture

class PlayerAssets {

    val idleFrames = arrayOf(
        Texture("player/player_idle.png"),
        Texture("player/player_stand.png")
    )

    val runFrames = arrayOf(
        Texture("player/player_walk1.png"),
        Texture("player/player_walk2.png")
    )

    val jumpFrame = Texture("player/player_jump.png")
    val fallFrame = Texture("player/player_fall.png")

    fun dispose() {
        idleFrames.forEach { it.dispose() }
        runFrames.forEach { it.dispose() }
        jumpFrame.dispose()
        fallFrame.dispose()
    }
}