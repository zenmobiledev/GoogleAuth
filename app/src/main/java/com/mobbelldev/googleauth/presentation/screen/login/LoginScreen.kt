package com.mobbelldev.googleauth.presentation.screen.login

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mobbelldev.googleauth.domain.model.ApiRequest
import com.mobbelldev.googleauth.domain.model.ApiResponse
import com.mobbelldev.googleauth.domain.model.MessageBarState
import com.mobbelldev.googleauth.navigation.Screen
import com.mobbelldev.googleauth.presentation.screen.common.StartActivityForResult
import com.mobbelldev.googleauth.presentation.screen.common.signIn
import com.mobbelldev.googleauth.util.RequestState

@Composable
fun LoginScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel(),
) {
    val signedInState: Boolean by loginViewModel.signedInState
    val messageBarState: MessageBarState by loginViewModel.messageBarState
    val apiResponse: RequestState<ApiResponse> by loginViewModel.apiResponse

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
            loginViewModel.verifyTokenOnBackend(request = ApiRequest(tokenId = tokenId))
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

    LaunchedEffect(key1 = apiResponse) {
        when (apiResponse) {
            is RequestState.Success -> {
                val response = (apiResponse as RequestState.Success<ApiResponse>).data.success
                if (response) {
                    navigateToProfileScreen(navController = navController)
                } else {
                    loginViewModel.saveSignedInState(signedIn = false)
                }
            } else -> {

            }
        }
    }
}

private fun navigateToProfileScreen(navController: NavHostController) {
    navController.navigate(route = Screen.Profile.route) {
        popUpTo(route = Screen.Login.route) {
            inclusive = true
        }
    }
}