package com.mobbelldev.googleauth.presentation.screen.login

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.mobbelldev.googleauth.domain.model.MessageBarState

@Composable
fun LoginScreen(navController: NavHostController) {
    Scaffold(
        topBar = { LoginTopBar() },
        content = { paddingValues ->
            LoginContent(
                modifier = Modifier.padding(paddingValues = paddingValues),
                signedInState = false,
                messageBarState = MessageBarState(),
                onButtonClicked = {}
            )
        }
    )
}