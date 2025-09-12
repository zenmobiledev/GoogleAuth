package com.mobbelldev.googleauth.presentation.screen.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobbelldev.googleauth.domain.model.MessageBarState
import com.mobbelldev.googleauth.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _signedInState: MutableState<Boolean> = mutableStateOf(value = false)
    val signedInState: State<Boolean> = _signedInState

    private val _messageBarState: MutableState<MessageBarState> =
        mutableStateOf(value = MessageBarState())
    val messageBarState: State<MessageBarState> = _messageBarState

    init {
        viewModelScope.launch {
            repository.readSignedInState().collect { completed ->
                _signedInState.value = completed
            }
        }
    }

    fun saveSignedInState(signedIn: Boolean) {
        viewModelScope.launch(context = Dispatchers.IO) {
            repository.saveSignedInState(signedIn = signedIn)
        }
    }
}