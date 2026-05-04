package com.example.towergame.viewmodel

import com.badlogic.gdx.math.Rectangle
import com.example.towergame.communication.Move
import com.example.towergame.communication.State
import com.example.towergame.core.World

class VM {
    private val world: World = World()

    fun getPlatformRects(): List<Rectangle> {
        return world.platforms.map { it.rect }
    }

    fun getPlayerRects(): List<Rectangle> {
        return world.players.map { it.rect }
    }
    fun getCameraPosition(): Float {
        return world.getCameraPosition()
    }

    fun isGameOver(): Boolean {
        return world.isGameOver()
    }
    fun getScore(): Int {
        return world.getScore()
    }

    fun setMove(move: Move, state: Boolean) {
        world.setMove(move, state)
    }

    fun getState(): State {
        return world.getState()
    }

    fun update(time: Float) {
        world.update(time)
    }
}