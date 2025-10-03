package com.mobbelldev.googleauth.data.remote

import com.mobbelldev.googleauth.domain.model.ApiRequest
import com.mobbelldev.googleauth.domain.model.ApiResponse
import com.mobbelldev.googleauth.domain.model.UserUpdate
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface KtorApi {
    @POST("/token_verification")
    suspend fun verifyTokenOnBackend(
        @Body request: ApiRequest,
    ): ApiResponse

    @GET("/get_user_info")
    suspend fun getUserInfo(): ApiResponse

    @PUT("/update_user_info")
    suspend fun updateUserInfo(
        @Body request: UserUpdate,
    ): ApiResponse

    @DELETE("/delete_user")
    suspend fun deleteUser(): ApiResponse

    @GET("/sign_out")
    suspend fun clearSession(): ApiResponse
}