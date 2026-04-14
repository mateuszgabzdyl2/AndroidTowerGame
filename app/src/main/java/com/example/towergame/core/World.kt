package com.example.towergame.core

import com.example.towergame.communication.Move
import com.example.towergame.constants.Constants
import kotlin.math.abs
import kotlin.random.Random


class World {
    val platforms = mutableListOf<Platform>()
    val players = mutableListOf<Player>()
    val camera = Camera(Constants.WORLD_HEIGHT / 2)

    init {
        platforms.add(Platform(0, 200f, 400f))
        for(i in 1..100) {
            platforms.add(generatePlatform(i))
        }

        players.add(Player(200f, 35f))
    }

    fun setMove(move: Move, state: Boolean) {
        players[0].setMove(move, state)
    }
    fun update(time: Float) {
        for(player in players) {
            player.update(time)
        }

        for(player in players) {
            for(plat in platforms) {
                if(abs(plat.rect.y - player.rect.y) > Constants.PLATFORM_GAP) {
                    continue
                }

                if (player.rect.overlaps(plat.rect)) {
                    player.resolveCollision(plat.rect)
                }
            }
        }

        camera.update(time, players[0].rect.y)

//        if(isGameOver()) {
//            println("GAME OVER")
//        }
    }

    fun getCameraPosition(): Float {
        return camera.y
    }

    fun generatePlatform(level: Int): Platform {
        val stage = level / 10

        val (minWidth, maxWidth) = when (stage) {
            0 -> 160f to 200f   // levels 1–10
            1 -> 120f to 170f   // 11–20
            2 -> 90f to 140f    // 21–30
            else -> 60f to 100f //
        }

        val width = Random.nextFloat() * (maxWidth - minWidth) + minWidth

        val x = Random.nextFloat() * (Constants.WORLD_WIDTH - width) + width / 2f

        return Platform(level, x, width)

    }

    fun isGameOver(): Boolean {
        if(players[0].rect.y < camera.y - Constants.WORLD_HEIGHT / 2 - Constants.PLAYER_HEIGHT) {
            return true
        }
        return false
    }

    fun getScore(): Int {
        return players[0].score
    }
}