package com.mobbelldev.googleauth.presentation.screen.login

import android.content.res.Configuration
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mobbelldev.googleauth.R
import com.mobbelldev.googleauth.presentation.theme.GoogleAuthTheme
import com.mobbelldev.googleauth.presentation.theme.topAppBarBackgroundColor
import com.mobbelldev.googleauth.presentation.theme.topAppBarContentColor
import com.mobbelldev.googleauth.util.Constant.DARK_THEME
import com.mobbelldev.googleauth.util.Constant.LIGHT_THEME

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginTopBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.text_sign_in),
                color = topAppBarContentColor
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = topAppBarBackgroundColor
        )
    )
}

@Preview(name = LIGHT_THEME)
@Composable
private fun LoginTopBarLightModePreview() {
    GoogleAuthTheme {
        LoginTopBar()
    }
}

@Preview(name = DARK_THEME, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun LoginTopBarDarkModePreview() {
    GoogleAuthTheme {
        LoginTopBar()
    }
}