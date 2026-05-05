package com.example.towergame.core

import com.example.towergame.communication.Move
import com.example.towergame.communication.State
import com.example.towergame.constants.Constants
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.random.Random

class World {
    val platforms = mutableListOf<Platform>()
    val players = mutableListOf<Player>()
    val camera = Camera(Constants.WORLD_HEIGHT / 2)

    init {
        platforms.add(Platform(0, Constants.WORLD_WIDTH / 2, ceil(Constants.WORLD_WIDTH / Constants.PLATFORM_HEIGHT.toDouble()).toInt()))
        for(i in 1..100) {
            platforms.add(generatePlatform(i))
        }

        players.add(Player(Constants.WORLD_WIDTH / 2, 35f))
    }

    fun setMove(move: Move, state: Boolean) {
        players[0].setMove(move, state)
    }

    fun setHorizontalInput(input: Float) {
        players[0].setHorizontalInput(input)
    }

    fun setJump(state: Boolean) {
        players[0].setJump(state)
    }

    fun isJumpEvent(): Boolean {
        return players[0].isJumpEvent()
    }

    fun getState(): State {
        return players[0].getState()
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

                if(player.rect.overlaps(plat.rect)) {
                    player.resolveCollision(plat.rect)
                }
            }
        }

//        for(i in platforms.indices) {
//            for(j in i + 1 until platforms.size) {
//                val p1 = players[i]
//                val p2 = players[j]
//
//                if(p1.rect.overlaps(p2.rect)) {
//
//                }
//            }
//        }

        camera.update(time, players[0].rect.y)
    }

    fun getCameraPosition(): Float {
        return camera.y
    }

    fun generatePlatform(level: Int): Platform {
        val stage = level / 10

        val (minSegments, maxSegments) = when (stage) {
            0 -> 11 to 14
            1 -> 9 to 12
            2 -> 7 to 10
            else -> 5 to 8
        }

        val segments = Random.nextInt(minSegments, maxSegments + 1)

        val x = Random.nextFloat() * (Constants.WORLD_WIDTH - segments * Constants.PLATFORM_HEIGHT) + segments * Constants.PLATFORM_HEIGHT / 2f

        return Platform(level, x, segments)
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