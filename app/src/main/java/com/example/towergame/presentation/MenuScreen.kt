package com.example.towergame.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun MenuScreen(onClick: (String) -> Unit) {
    Column {
        Text(text = "Menu")
        Button(onClick = { onClick("settings") }) {
            Text(text = "Settings")
        }
        Button(onClick = { onClick("lobby") }) {
            Text(text = "Lobby")
        }
    }

}