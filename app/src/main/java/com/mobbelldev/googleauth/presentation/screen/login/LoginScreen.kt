package com.mobbelldev.googleauth.presentation.screen.login

import android.app.Activity
import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mobbelldev.googleauth.domain.model.MessageBarState
import com.mobbelldev.googleauth.presentation.screen.common.StartActivityForResult
import com.mobbelldev.googleauth.presentation.screen.common.signIn

@Composable
fun LoginScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel(),
) {
    val signedInState: Boolean by loginViewModel.signedInState
    val messageBarState: MessageBarState by loginViewModel.messageBarState

    Scaffold(
        topBar = { LoginTopBar() },
        content = { paddingValues ->
            LoginContent(
                modifier = Modifier.padding(paddingValues = paddingValues),
                signedInState = signedInState,
                messageBarState = messageBarState,
                onButtonClicked = { loginViewModel.saveSignedInState(signedIn = true) }
            )
        }
    )

    val activity = LocalActivity.current as Activity
    StartActivityForResult(
        key = signedInState,
        onResultReceived = { tokenId ->
            Log.d("LoginScreen", "tokenId: $tokenId")
        },
        onDialogDismissed = {
            loginViewModel.saveSignedInState(signedIn = false)
        }
    ) { launcher ->
        if (signedInState) {
            signIn(
                activity = activity,
                launchActivityResult = { intentSenderRequest ->
                    launcher.launch(input = intentSenderRequest)
                },
                accountNotFound = {
                    loginViewModel.saveSignedInState(signedIn = false)
                    loginViewModel.updateMessageBarState()
                }
            )
        }
    }
}