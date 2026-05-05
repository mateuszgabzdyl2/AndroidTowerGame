package com.example.towergame.gdx.gamescreen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.example.towergame.communication.Move
import com.example.towergame.viewmodel.VM

//class PlayerView(
//    var vm: VM,
//) {
//    var lastStateLeft: Boolean = false
//    var lastStateRight: Boolean = false
//    var lastStateJump: Boolean = false
//
//    fun update() {
//        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && !lastStateLeft) {
//            lastStateLeft = true
//            vm.setMove(Move.LEFT, true)
//        }
//        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !lastStateRight) {
//            lastStateRight = true
//            vm.setMove(Move.RIGHT, true)
//        }
//        if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && !lastStateJump) {
//            lastStateJump = true
//            vm.setMove(Move.JUMP, true)
//        }
//        if(!Gdx.input.isKeyPressed(Input.Keys.LEFT) && lastStateLeft) {
//            lastStateLeft = false
//            vm.setMove(Move.LEFT, false)
//        }
//        if(!Gdx.input.isKeyPressed(Input.Keys.RIGHT) && lastStateRight) {
//            lastStateRight = false
//            vm.setMove(Move.RIGHT, false)
//        }
//        if(!Gdx.input.isKeyPressed(Input.Keys.SPACE) && lastStateJump) {
//            lastStateJump = false
//            vm.setMove(Move.JUMP, false)
//        }
//    }
//
//}


class PlayerView(
    private val vm: VM
) {
    private var smoothedTilt = 0f
    var lastStateJump: Boolean = false

    fun update(delta: Float) {
        // accelerometer
        val raw = Gdx.input.accelerometerY

        val deadZone = 1.0f
        val maxTilt = 6f

        // normalize to [-1, 1]
        val normalized = when {
            kotlin.math.abs(raw) < deadZone -> 0f
            else -> (raw / maxTilt).coerceIn(-1f, 1f)
        }

        // smoothing
        smoothedTilt = smoothedTilt * 0.8f + normalized * 0.2f
        vm.setHorizontalInput(smoothedTilt)

        // tap for jump
        if(Gdx.input.isTouched() && !lastStateJump) {
            lastStateJump = true
            vm.setJump(true)
        }

        if(!Gdx.input.isTouched() && lastStateJump) {
            lastStateJump = false
            vm.setJump(false)
        }
    }
}