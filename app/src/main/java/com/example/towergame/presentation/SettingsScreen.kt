package com.example.towergame.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SettingsScreen(onClick: (String) -> Unit) {
    Column() {
        Text(text = "Settings")
        Button(onClick = { onClick("menu") }) {
            Text(text = "Menu")
        }
    }
}