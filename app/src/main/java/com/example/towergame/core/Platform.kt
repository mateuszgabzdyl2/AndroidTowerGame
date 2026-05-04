package com.example.towergame.core

import com.badlogic.gdx.math.Rectangle
import com.example.towergame.constants.Constants

class Platform(
    var level: Int,
    var x: Float,   // x-coordinate of the center of the platform
    var width: Int
) {
    val rect = Rectangle(x - width * Constants.PLATFORM_HEIGHT / 2, level * Constants.PLATFORM_GAP, width * Constants.PLATFORM_HEIGHT, Constants.PLATFORM_HEIGHT)
}