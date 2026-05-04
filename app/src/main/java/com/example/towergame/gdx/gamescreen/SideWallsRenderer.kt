package com.example.towergame.gdx.gamescreen

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.example.towergame.constants.Constants
import kotlin.math.ceil

class SideWallsRenderer(
    private val viewportWidth: Float,
    private val viewportHeight: Float
) {
    private val wallTexture = Texture("side_wall.jpg")
    private val tileSize = 550f
    private val sideWallWidth = (viewportWidth - (Constants.WORLD_WIDTH / Constants.WORLD_HEIGHT) * viewportHeight) / 2
    private val tilesX = (sideWallWidth / tileSize).toInt() + 2
    private val tilesY = (viewportHeight / tileSize).toInt() + 2
    private val parallaxFactor = 1.5f
    fun render(batch: SpriteBatch, cameraY: Float) {
        val cameraBottomY = cameraY - Constants.WORLD_HEIGHT / 2
        val bgY = cameraBottomY * parallaxFactor

        val startY = bgY - ceil(bgY / tileSize) * tileSize

        batch.begin()

        for (x in 0 until tilesX) {
            for (y in 0 until tilesY) {
                val yPos = startY + y * tileSize

                // left wall
                batch.draw(
                    wallTexture,
                    sideWallWidth - tileSize - x * tileSize,
                    yPos,
                    tileSize,
                    tileSize
                )

                // right wall
                batch.draw(
                    wallTexture,
                    viewportWidth - sideWallWidth + x * tileSize,
                    yPos,
                    tileSize,
                    tileSize
                )
            }
        }

        batch.end()
    }

    fun dispose() {
        wallTexture.dispose()
    }
}