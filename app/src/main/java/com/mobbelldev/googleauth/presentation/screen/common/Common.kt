package com.mobbelldev.googleauth.presentation.screen.common

import android.app.Activity
import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.mobbelldev.googleauth.util.Constant.CLIENT_WEB_ID

@Composable
fun StartActivityForResult(
    key: Any,
    onResultReceived: (String) -> Unit,
    onDialogDismissed: () -> Unit,
    launcher: (ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>) -> Unit,
) {
    val tag = "StartActivityForResult"
    val activity = LocalActivity.current as Activity
    val activityLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            try {
                if (result.resultCode == Activity.RESULT_OK) {
                    val onTapClient = Identity.getSignInClient(activity)
                    val credentials = onTapClient.getSignInCredentialFromIntent(result.data)
                    val tokenId = credentials.googleIdToken
                    if (tokenId != null) {
                        onResultReceived(tokenId)
                        Log.i(tag, "Sign-in success. Token retrieved")
                    } else {
                        Log.w(tag, "Sign-in success but tokenId is null")
                    }
                } else {
                    Log.d(tag, "User dismissed One-Tap dialog (black scrim clicked)")
                    onDialogDismissed()
                }
            } catch (e: ApiException) {
                when (e.statusCode) {
                    CommonStatusCodes.CANCELED -> {
                        Log.d(tag, "One-Tap dialog canceled by user")
                        onDialogDismissed()
                    }

                    CommonStatusCodes.NETWORK_ERROR -> {
                        Log.w(tag, "One-Tap failed due to network error")
                        onDialogDismissed()
                    }

                    else -> {
                        Log.e(tag, "Unexpected error. Code=${e.statusCode}, Message=${e.message}")
                        onDialogDismissed()
                    }
                }
            }
        }
    LaunchedEffect(key1 = key) {
        launcher(activityLauncher)
    }
}

fun signIn(
    activity: Activity,
    launchActivityResult: (IntentSenderRequest) -> Unit,
    accountNotFound: () -> Unit,
) {
    val tag = "signIn"
    val oneTapClient = Identity.getSignInClient(activity)
    val signInRequest = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(CLIENT_WEB_ID)
                .setFilterByAuthorizedAccounts(true)
                .build()
        )
        .setAutoSelectEnabled(true)
        .build()

    oneTapClient.beginSignIn(signInRequest)
        .addOnSuccessListener { result ->
            try {
                launchActivityResult(
                    IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                )
            } catch (e: Exception) {
                Log.d(tag, "Couldn't sign in because: ${e.message}")
            }
        }
        .addOnFailureListener { exception ->
            Log.d(tag, "Failure sign in: ${exception.message}")
            signUp(
                activity = activity,
                launchActivityResult = launchActivityResult,
                accountNotFound = accountNotFound
            )
        }
}

fun signUp(
    activity: Activity,
    launchActivityResult: (IntentSenderRequest) -> Unit,
    accountNotFound: () -> Unit,
) {
    val tag = "tag"
    val oneTapClient = Identity.getSignInClient(activity)
    val signInRequest = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(CLIENT_WEB_ID)
                .setFilterByAuthorizedAccounts(false)
                .build()
        )
        .build()

    oneTapClient.beginSignIn(signInRequest)
        .addOnSuccessListener { result ->
            try {
                launchActivityResult(
                    IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                )
            } catch (e: Exception) {
                Log.d(tag, "Couldn't sign up because: ${e.message}")
            }
        }
        .addOnFailureListener { exception ->
            Log.d(tag, "sign up: ${exception.message}")
            accountNotFound()
        }
}