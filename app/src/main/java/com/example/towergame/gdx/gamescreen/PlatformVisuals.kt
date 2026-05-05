package com.example.towergame.gdx.gamescreen

import com.badlogic.gdx.graphics.Texture

enum class PlatformTheme {
    BROWN, GREEN, YELLOW , BLUE
}

data class PlatformTextures(
    val left: Texture,
    val mid: Texture,
    val right: Texture
)

object PlatformVisuals {

    fun getTheme(level: Int): PlatformTheme {
        return PlatformTheme.entries[(level / 20) % PlatformTheme.entries.size]
    }

    val textures: Map<PlatformTheme, PlatformTextures> = mapOf(
        PlatformTheme.YELLOW to PlatformTextures(
            Texture("platforms/tileYellow_1.png"),
            Texture("platforms/tileYellow_2.png"),
            Texture("platforms/tileYellow_3.png")
        ),
        PlatformTheme.GREEN to PlatformTextures(
            Texture("platforms/tileGreen_1.png"),
            Texture("platforms/tileGreen_2.png"),
            Texture("platforms/tileGreen_3.png")
        ),
        PlatformTheme.BROWN to PlatformTextures(
            Texture("platforms/tileBrown_1.png"),
            Texture("platforms/tileBrown_2.png"),
            Texture("platforms/tileBrown_3.png")
        ),
        PlatformTheme.BLUE to PlatformTextures(
            Texture("platforms/tileBlue_1.png"),
            Texture("platforms/tileBlue_2.png"),
            Texture("platforms/tileBlue_3.png")
        )
    )
}