package com.mobbelldev.googleauth.data.repository

import com.mobbelldev.googleauth.data.remote.KtorApi
import com.mobbelldev.googleauth.domain.model.ApiRequest
import com.mobbelldev.googleauth.domain.model.ApiResponse
import com.mobbelldev.googleauth.domain.model.UserUpdate
import com.mobbelldev.googleauth.domain.repository.DataStoreOperations
import com.mobbelldev.googleauth.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val dataStoreOperations: DataStoreOperations,
    private val ktorApi: KtorApi,
) :
    Repository {
    override suspend fun saveSignedInState(signedIn: Boolean) =
        dataStoreOperations.saveSignedInState(signedIn = signedIn)

    override fun readSignedInState(): Flow<Boolean> =
        dataStoreOperations.readSignedInState()

    override suspend fun verifyTokenOnBackend(request: ApiRequest): ApiResponse {
        return try {
            ktorApi.verifyTokenOnBackend(request = request)
        } catch (e: Exception) {
            ApiResponse(success = false, error = e)
        }
    }

    override suspend fun getUserInfo(): ApiResponse {
        return try {
            ktorApi.getUserInfo()
        } catch (e: Exception) {
            ApiResponse(success = false, error = e)
        }
    }

    override suspend fun updateUserInfo(request: UserUpdate): ApiResponse {
        return try {
            ktorApi.updateUserInfo(request = request)
        } catch (e: Exception) {
            ApiResponse(success = false, error = e)
        }
    }

    override suspend fun deleteUser(): ApiResponse {
        return try {
            ktorApi.deleteUser()
        } catch (e: Exception) {
            ApiResponse(success = false, error = e)
        }
    }

    override suspend fun clearSession(): ApiResponse {
        return try {
            ktorApi.clearSession()
        } catch (e: Exception) {
            ApiResponse(success = false, error = e)
        }
    }
}