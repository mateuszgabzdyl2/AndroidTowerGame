package com.example.towergame.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.towergame.gdx.LibGdxActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val context = LocalContext.current

            NavHost(navController = navController, startDestination = "menu") {
                composable("menu") {
                    MenuScreen(onClick = {
                        navController.navigate(it)
                    }, onStartGame = {
                        val gameIntent = Intent(context, LibGdxActivity::class.java).apply {
                            putExtra("GAME_MODE", "SINGLEPLAYER")
                        }
                        context.startActivity(gameIntent)
                    })
                }
                composable("settings") {
                    SettingsScreen(onClick = {
                        navController.navigate(
                            route = it,
                            navOptions = navOptions {
                                popUpTo("menu") { inclusive = true }
                            }
                        )
                    })
                }
                composable("lobby") {
                    LobbyScreen(onClick = {
                        navController.navigate(
                            route = it,
                            navOptions = navOptions {
                                popUpTo("menu") { inclusive = true }
                            }
                        )
                    }, onStartGame = {
                        val gameIntent = Intent(context, LibGdxActivity::class.java).apply {
                            putExtra("GAME_MODE", "MULTIPLAYER")
                        }
                        context.startActivity(gameIntent)
                    })
                }
                composable("statistics") {
                    StatisticsScreen(onClick = {
                        navController.navigate(
                            route = it,
                            navOptions = navOptions {
                                popUpTo("menu") { inclusive = true }
                            }
                        )
                    })
                }
            }
        }
    }

    // test
    override fun onStart() {
        super.onStart()
        Log.d("LIFECYCLE", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("LIFECYCLE", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("LIFECYCLE", "onPause ")
    }

    override fun onStop() {
        super.onStop()
        Log.d("LIFECYCLE", "onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("LIFECYCLE", "onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("LIFECYCLE", "onDestroy")
    }
}