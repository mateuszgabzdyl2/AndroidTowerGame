package com.example.towergame.gdx.gamescreen

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.example.towergame.communication.State

class PlayerAnimator(private val assets: PlayerAssets) {

    private val idleAnim: Animation<TextureRegion> = Animation(
        0.3f,
        *assets.idleFrames.map { TextureRegion(it) }.toTypedArray()
    )
    private val runAnim: Animation<TextureRegion> = Animation(
        0.1f,
        *assets.runFrames.map { TextureRegion(it) }.toTypedArray()
    )
    private val jumpFrame = TextureRegion(assets.jumpFrame)
    private val fallFrame = TextureRegion(assets.fallFrame)

    private var stateTime = 0f

    fun update(delta: Float) {
        stateTime += delta
    }

    fun getFrame(state: State): TextureRegion {

        val baseFrame = when (state) {

            State.IDLE -> idleAnim.getKeyFrame(stateTime, true)

            State.JUMP -> jumpFrame

            State.FALL -> fallFrame

            State.LEFT_GROUND, State.RIGHT_GROUND -> runAnim.getKeyFrame(stateTime, true)
        }

        val frame = TextureRegion(baseFrame)

        if (state == State.LEFT_GROUND) {
            frame.flip(true, false)
        }

        return frame
    }
}