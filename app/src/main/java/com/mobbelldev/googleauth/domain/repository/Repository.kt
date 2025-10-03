package com.mobbelldev.googleauth.domain.repository

import com.mobbelldev.googleauth.domain.model.ApiRequest
import com.mobbelldev.googleauth.domain.model.ApiResponse
import com.mobbelldev.googleauth.domain.model.UserUpdate
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun saveSignedInState(signedIn: Boolean)
    fun readSignedInState(): Flow<Boolean>
    suspend fun verifyTokenOnBackend(request: ApiRequest): ApiResponse
    suspend fun getUserInfo(): ApiResponse
    suspend fun updateUserInfo(request: UserUpdate): ApiResponse
    suspend fun deleteUser(): ApiResponse
    suspend fun clearSession(): ApiResponse
}