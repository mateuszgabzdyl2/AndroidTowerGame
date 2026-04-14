package com.example.towergame.presentation

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.towergame.gdx.LibGdxActivity

@Composable
fun LobbyScreen(onClick: (String) -> Unit) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Lobby",
                style = MaterialTheme.typography.headlineLarge
            )

            Button(
                onClick = {
                    context.startActivity(
                        Intent(context, LibGdxActivity::class.java)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(60.dp)
            ) {
                Text(
                    text = "Start Game",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Button(
                onClick = { onClick("menu") },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(60.dp)
            ) {
                Text("Back to Menu")
            }
        }
    }
}