package com.mobbelldev.googleauth.presentation.screen.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobbelldev.googleauth.domain.exception.GoogleAccountNotFound
import com.mobbelldev.googleauth.domain.model.ApiRequest
import com.mobbelldev.googleauth.domain.model.ApiResponse
import com.mobbelldev.googleauth.domain.model.MessageBarState
import com.mobbelldev.googleauth.domain.repository.Repository
import com.mobbelldev.googleauth.util.RequestState
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

    private val _apiResponse: MutableState<RequestState<ApiResponse>> =
        mutableStateOf(value = RequestState.Idle)
    val apiResponse: State<RequestState<ApiResponse>> = _apiResponse

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

    fun updateMessageBarState() {
        _messageBarState.value = MessageBarState(error = GoogleAccountNotFound())
    }

    fun verifyTokenOnBackend(request: ApiRequest) {
        _apiResponse.value = RequestState.Loading
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val response = repository.verifyTokenOnBackend(request = request)
                _apiResponse.value = RequestState.Success(data = response)
                _messageBarState.value =
                    MessageBarState(
                        message = response.message,
                        error = response.error
                    )
            }
        } catch (e: Exception) {
            _apiResponse.value = RequestState.Error(error = e)
            _messageBarState.value = MessageBarState(error = e)
        }
    }
}