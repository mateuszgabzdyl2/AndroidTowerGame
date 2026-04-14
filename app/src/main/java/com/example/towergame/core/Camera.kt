package com.example.towergame.core

import com.example.towergame.constants.Constants

class Camera (
    var y: Float = 0f,
) {
    var threshold: Float = Constants.WORLD_HEIGHT * 0.25f
    var autoScrollStarted: Boolean = false

    private fun getAutoScrollSpeed(level: Int): Float {
        return when (level / 10) {
            0 -> 20f      // 1–10
            1 -> 34f      // 11–20
            2 -> 48f      // 21–30
            else -> 72f   //
        }
    }
    fun update(time: Float, playerY: Float) {
        if(!autoScrollStarted && playerY > Constants.WORLD_HEIGHT / 2f) {
            autoScrollStarted = true
        }

        // follow player
        val cameraTriggerLine = y + threshold
        if (playerY > cameraTriggerLine) {
            val diff = playerY - cameraTriggerLine
            y += diff
        }

        val level: Int = (y / Constants.PLATFORM_GAP).toInt()
        // auto scroll
        if(autoScrollStarted) {
            val speed = getAutoScrollSpeed(level)
            y += speed * time
        }
    }
}