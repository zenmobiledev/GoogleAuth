package com.mobbelldev.googleauth.presentation.screen.profile

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.mobbelldev.googleauth.domain.model.ApiResponse
import com.mobbelldev.googleauth.domain.model.MessageBarState
import com.mobbelldev.googleauth.util.RequestState

@Composable
fun ProfileScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            ProfileTopBar(
                onSave = {},
                onDeleteAllConfirmed = {}
            )
        },
        content = { paddingValues ->
            ProfileContent(
                modifier = Modifier.padding(paddingValues = paddingValues),
                apiResponse = RequestState.Success(data = ApiResponse(success = true)),
                messageBarState = MessageBarState(),
                firstName = "",
                onFirstNameChanged = {},
                lastName = "",
                onLastNameChanged = {},
                email = "",
                photo = "",
                onSignOutClicked = {}
            )
        }
    )
}