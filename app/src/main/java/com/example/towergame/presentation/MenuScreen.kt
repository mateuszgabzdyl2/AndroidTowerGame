package com.example.towergame.presentation

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
import androidx.compose.ui.unit.dp

@Composable
fun MenuScreen(onClick: (String) -> Unit, onStartGame: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Menu",
                style = MaterialTheme.typography.headlineLarge
            )

            Button(
                onClick = onStartGame,
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(60.dp)
            ) {
                Text("Singleplayer")
            }

            Button(
                onClick = {
                    onClick("lobby")
                },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(60.dp)
            ) {
                Text("Multiplayer")
            }

            Button(
                onClick = {
                    onClick("statistics")
                },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(60.dp)
            ) {
                Text("Statistics")
            }

            Button(
                onClick = {
                    onClick("settings")
                },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(60.dp)
            ) {
                Text("Settings")
            }
        }
    }
}