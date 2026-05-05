package com.example.towergame.viewmodel

import com.badlogic.gdx.math.Rectangle
import com.example.towergame.communication.Move
import com.example.towergame.communication.PlatformRenderData
import com.example.towergame.communication.State
import com.example.towergame.core.World

class VM {
    private val world: World = World()

    fun getPlatformRenderData(): List<PlatformRenderData> {
        return world.platforms.map { platform -> PlatformRenderData(platform.rect, platform.level, platform.segments) }
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

    fun setHorizontalInput(input: Float) {
        world.setHorizontalInput(input)
    }

    fun setJump(state: Boolean) {
        world.setJump(state)
    }

    fun isJumpEvent(): Boolean {
        return world.isJumpEvent()
    }

    fun getState(): State {
        return world.getState()
    }

    fun update(time: Float) {
        world.update(time)
    }
}