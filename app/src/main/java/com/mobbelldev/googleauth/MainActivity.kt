package com.mobbelldev.googleauth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.mobbelldev.googleauth.navigation.SetupNavGraph
import com.mobbelldev.googleauth.ui.theme.GoogleAuthTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GoogleAuthTheme {
                SetupNavGraph(
                    navController = rememberNavController()
                )
            }
        }
    }
}