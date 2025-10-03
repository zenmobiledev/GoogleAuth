package com.mobbelldev.googleauth.presentation.screen.profile

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mobbelldev.googleauth.R
import com.mobbelldev.googleauth.presentation.theme.topAppBarBackgroundColor
import com.mobbelldev.googleauth.presentation.theme.topAppBarContentColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopBar(
    onSave: () -> Unit,
    onDeleteAllConfirmed: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.text_profile),
                color = topAppBarContentColor
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = topAppBarBackgroundColor
        ),
        actions = {
            ProfileTopBarActions(
                onSave = onSave,
                onDeleteAllConfirmed = onDeleteAllConfirmed
            )
        }
    )
}

@Composable
fun ProfileTopBarActions(
    onSave: () -> Unit,
    onDeleteAllConfirmed: () -> Unit,
) {
    SaveAction(onSave = onSave)
    DeleteAllAction(onDeleteAllConfirmed = onDeleteAllConfirmed)
}

@Composable
fun SaveAction(onSave: () -> Unit) {
    IconButton(onClick = onSave) {
        Icon(
            painter = painterResource(R.drawable.baseline_save_24),
            contentDescription = stringResource(R.string.hint_save_icon),
            tint = topAppBarContentColor
        )
    }
}

@Composable
fun DeleteAllAction(onDeleteAllConfirmed: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(
            painter = painterResource(R.drawable.baseline_more_vert_24),
            contentDescription = stringResource(R.string.hint_delete_all_icon),
            tint = topAppBarContentColor
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onDeleteAllConfirmed()
                },
                text = {
                    Text(
                        text = stringResource(R.string.text_delete_account),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            )
        }
    }
}

@Preview
@Composable
private fun ProfileTopBarPreview() {
    ProfileTopBar(
        onSave = {},
        onDeleteAllConfirmed = {}
    )
}