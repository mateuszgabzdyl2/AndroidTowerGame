package com.example.towergame.presentation

import android.content.Intent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.towergame.gdx.LibGdxActivity

@Composable
fun LobbyScreen() {
    val context = LocalContext.current

    Button(onClick = {
        context.startActivity(
            Intent(context, LibGdxActivity::class.java)
        )
    }) {
        Text("Start Game")
    }
}