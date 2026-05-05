package com.example.towergame.core

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.example.towergame.communication.Move
import com.example.towergame.communication.State
import com.example.towergame.constants.Constants
import kotlin.math.abs

class Player(
    var x: Float,
    var y: Float
) {
    val rect = Rectangle(x - Constants.PLAYER_WIDTH / 2, y - Constants.PLAYER_HEIGHT / 2, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT)
    val velocity = Vector2(0f, 0f)

    // constants
    private val gravity = 1000f
    private val acceleration = 500f
    private val friction = 0.4f
    private val maxSpeed = 500f
    private val baseJump = 450f
    private var wallBounciness = 0.5f
    private var previousY = 0f
    var isGrounded = true
    var platformLeftEnd = 0f
    var platformRightEnd = Constants.WORLD_WIDTH
    var score = 0

//    var moveLeftState = false
//    var moveRightState = false
    var horizontalInputValue = 0f
    var jumpState = false
    var jumpEvent = false

    fun setMove(move: Move, state: Boolean) {
//        when (move) {
//            Move.LEFT -> moveLeftState = state
//            Move.RIGHT -> moveRightState = state
//            Move.JUMP -> jumpState = state
//        }
    }

    fun setHorizontalInput(input: Float) {
        horizontalInputValue = input.coerceIn(-1f, 1f)
    }

    fun setJump(state: Boolean) {
        jumpState = state
    }

    fun isJumpEvent(): Boolean {
        return jumpEvent
    }

    fun getState(): State {
//        if(isGrounded) {
//            if(moveLeftState) return State.LEFT_GROUND
//            if(moveRightState) return State.RIGHT_GROUND
//            return State.IDLE
//        }
//        else {
//            if(velocity.y > 0) return State.JUMP
//            return State.FALL
//        }

        if(isGrounded) {
            return when {
                horizontalInputValue < -0.2f -> State.LEFT_GROUND
                horizontalInputValue > 0.2f -> State.RIGHT_GROUND
                else -> State.IDLE
            }
        } else {
            return if (velocity.y > 0) State.JUMP else State.FALL
        }
    }
    fun update(delta: Float) {
        previousY = rect.y
        val currentAccel = if(isGrounded) acceleration else acceleration * 0.6f

//        if(moveLeftState) {
//            velocity.x -= currentAccel * delta
//        }
//        else if(moveRightState) {
//            velocity.x += currentAccel * delta
//        }
//        else {
//            if(isGrounded) {
//                velocity.x *= friction
//            }
//            else {
//                // different value in the air
//                velocity.x *= 0.98f
//            }
//        }


        if(abs(horizontalInputValue) > 0.1f) {
            velocity.x += horizontalInputValue * currentAccel * delta * 2
        }
        else {
            velocity.x *= if (isGrounded) friction else 0.98f
        }

        // speed cap
        if(velocity.x > maxSpeed) velocity.x = maxSpeed
        if(velocity.x < -maxSpeed) velocity.x = -maxSpeed

        // gravity
        if(!isGrounded) {
            velocity.y -= gravity * delta
        }

        // jump
        if(isGrounded && jumpState) {
            val speedBonus = abs(velocity.x) * 0.7f // bonus for fast run
            velocity.y = baseJump + speedBonus
            isGrounded = false
            jumpEvent = true
        }
        else {
            jumpEvent = false
        }

        // applying velocity
        rect.x += velocity.x * delta
        rect.y += velocity.y * delta

        // check walls
        if(rect.x < 0f) {
            rect.x = 0f
            if(velocity.x < 0) {
                velocity.x = -velocity.x * wallBounciness
            }
        }
        if(rect.x + rect.width > Constants.WORLD_WIDTH) {
            rect.x = Constants.WORLD_WIDTH - rect.width
            if(velocity.x > 0) {
                velocity.x = -velocity.x * wallBounciness
            }
        }

        if(rect.x + rect.width / 2f !in platformLeftEnd..platformRightEnd) {
            isGrounded = false
        }

        // score
        score = maxOf(score, (rect.y / Constants.PLATFORM_GAP).toInt())
    }

    fun resolveCollision(platformRect: Rectangle) {
        val platformTop = platformRect.y + platformRect.height

        if(velocity.y < 0 && previousY >= platformTop) {
            rect.y = platformTop
            velocity.y = 0f
            isGrounded = true
            platformLeftEnd = platformRect.x
            platformRightEnd = platformRect.x + platformRect.width
        }
    }
}