package com.mobbelldev.googleauth.presentation.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mobbelldev.googleauth.R
import com.mobbelldev.googleauth.domain.model.MessageBarState
import com.mobbelldev.googleauth.component.GoogleButton
import com.mobbelldev.googleauth.component.MessageBar
import com.mobbelldev.googleauth.ui.theme.GoogleAuthTheme

@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    signedInState: Boolean,
    messageBarState: MessageBarState,
    onButtonClicked: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(modifier = Modifier.weight(1F)) {
            MessageBar(messageBarState = messageBarState)
        }
        Column(
            modifier = Modifier
                .weight(9F)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CenterContent(
                signedInState = signedInState,
                onButtonClicked = onButtonClicked
            )
        }
    }
}

@Composable
fun CenterContent(
    signedInState: Boolean,
    onButtonClicked: () -> Unit,
) {
    Image(
        modifier = Modifier
            .padding(bottom = 20.dp)
            .size(120.dp),
        painter = painterResource(R.drawable.google_logo),
        contentDescription = stringResource(R.string.google_logo)
    )

    Text(
        text = stringResource(R.string.text_sign_in_title),
        fontWeight = FontWeight.Bold,
        fontSize = MaterialTheme.typography.titleMedium.fontSize
    )

    Text(
        modifier = Modifier
            .alpha(alpha = 0.74f)
            .padding(bottom = 40.dp, top = 4.dp),
        text = stringResource(R.string.text_sign_in_subtitle),
        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
        textAlign = TextAlign.Center
    )

    GoogleButton(
        loadingState = signedInState,
        onClick = { onButtonClicked() }
    )
}

@Preview(showBackground = true)
@Composable
private fun LoginContentPreview() {
    GoogleAuthTheme {
        LoginContent(
            signedInState = true,
            messageBarState = MessageBarState(),
            onButtonClicked = {}
        )
    }
}