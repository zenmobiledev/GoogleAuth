package com.mobbelldev.googleauth.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

@Composable
fun DisplayAlertDialog(
    title: String = "Delete your account?",
    message: String = "Are you sure you want to delete your account?",
    openDialog: Boolean,
    onYesClicked: () -> Unit,
    onDialogClosed: () -> Unit,
) {
    if (openDialog) {
        AlertDialog(
            title = {
                Text(
                    text = title,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = message,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onYesClicked()
                        onDialogClosed()
                    },
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { onDialogClosed() },
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(text = "No")
                }
            },
            onDismissRequest = { onDialogClosed() },
            shape = MaterialTheme.shapes.small
        )
    }
}