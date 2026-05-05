package com.example.towergame.communication

import com.badlogic.gdx.math.Rectangle

data class PlatformRenderData(
    val rect: Rectangle,
    val level: Int,
    val segments: Int
)
