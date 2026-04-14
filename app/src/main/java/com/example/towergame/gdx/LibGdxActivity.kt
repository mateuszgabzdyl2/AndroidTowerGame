package com.example.towergame.gdx

import android.content.pm.ActivityInfo
import android.os.Bundle
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration

class LibGdxActivity : AndroidApplication() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation =
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        val config = AndroidApplicationConfiguration().apply {
            useAccelerometer = false
            useCompass = false
        }

        initialize(MyGame(onGameOver =  {
            runOnUiThread {
                finish()
            }
        }), config)
    }
}