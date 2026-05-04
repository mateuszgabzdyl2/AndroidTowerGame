package com.example.towergame.gdx.gamescreen

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.example.towergame.constants.Constants
import kotlin.math.floor

class BackgroundRenderer {
    private val bgTexture = Texture("background.jpg")
    private val tileSize = Constants.BACKGROUND_TEXTURE_SIZE
    private val tilesX = (Constants.WORLD_WIDTH / tileSize).toInt() + 2
    private val tilesY = (Constants.WORLD_HEIGHT / tileSize).toInt() + 2
    val parallaxFactor = 0.5f
    fun render(batch: SpriteBatch, cameraY: Float) {
        val cameraBottomY = cameraY - Constants.WORLD_HEIGHT / 2
        val bgY = cameraBottomY * parallaxFactor

        val startY = bgY + floor((cameraBottomY - bgY) / tileSize) * tileSize

        batch.begin()

        for (x in 0 until tilesX) {
            for (y in 0 until tilesY) {

                batch.draw(
                    bgTexture,
                    x * tileSize,
                    startY + y * tileSize,
                    tileSize,
                    tileSize
                )
            }
        }

        batch.end()
    }

    fun dispose() {
        bgTexture.dispose()
    }
}