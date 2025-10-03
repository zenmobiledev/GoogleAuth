package com.mobbelldev.googleauth.presentation.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.mobbelldev.googleauth.R
import com.mobbelldev.googleauth.component.GoogleButton
import com.mobbelldev.googleauth.component.MessageBar
import com.mobbelldev.googleauth.domain.model.ApiResponse
import com.mobbelldev.googleauth.domain.model.MessageBarState
import com.mobbelldev.googleauth.ui.theme.LoadingBlue
import com.mobbelldev.googleauth.util.RequestState

@Composable
fun ProfileContent(
    modifier: Modifier,
    apiResponse: RequestState<ApiResponse>,
    messageBarState: MessageBarState,
    firstName: String,
    onFirstNameChanged: (String) -> Unit,
    lastName: String,
    onLastNameChanged: (String) -> Unit,
    email: String,
    photo: String,
    onSignOutClicked: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(modifier = Modifier.weight(1F)) {
            if (apiResponse is RequestState.Loading) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    color = LoadingBlue
                )
            } else {
                MessageBar(messageBarState = messageBarState)
            }
        }

        Column(
            modifier = Modifier.weight(9F),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Content(
                firstName = firstName,
                onFirstNameChanged = onFirstNameChanged,
                lastName = lastName,
                onLastNameChanged = onLastNameChanged,
                email = email,
                photo = photo,
                onSignOutClicked = onSignOutClicked
            )
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun Content(
    firstName: String,
    onFirstNameChanged: (String) -> Unit,
    lastName: String,
    onLastNameChanged: (String) -> Unit,
    email: String?,
    photo: String?,
    onSignOutClicked: () -> Unit,
) {
    val painter = rememberImagePainter(data = photo) {
        crossfade(durationMillis = 1000)
        placeholder(R.drawable.baseline_image_24)
    }

    Image(
        modifier = Modifier
            .padding(bottom = 40.dp)
            .size(size = 150.dp)
            .clip(CircleShape),
        painter = painter,
        contentDescription = stringResource(R.string.hint_profile_image)
    )
    OutlinedTextField(
        value = firstName,
        onValueChange = { onFirstNameChanged(it) },
        label = { Text(text = stringResource(R.string.hint_first_name)) },
        textStyle = MaterialTheme.typography.bodyMedium,
        singleLine = true
    )
    OutlinedTextField(
        value = lastName,
        onValueChange = { onLastNameChanged(it) },
        label = { Text(text = stringResource(R.string.hint_last_name)) },
        textStyle = MaterialTheme.typography.bodyMedium,
        singleLine = true
    )
    OutlinedTextField(
        value = email.toString(),
        onValueChange = { },
        label = { Text(text = stringResource(R.string.hint_email)) },
        textStyle = MaterialTheme.typography.bodyMedium,
        singleLine = true,
        enabled = false
    )
    GoogleButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        primaryText = "Sign Out",
        secondaryText = "Sign Out",
        onClick = { onSignOutClicked() }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ContentPreview() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Content(
            firstName = "Zaenal",
            onFirstNameChanged = {},
            lastName = "Arif",
            onLastNameChanged = {},
            email = "zaenalxxxx@gmail.com",
            photo = null,
            onSignOutClicked = {}
        )
    }
}