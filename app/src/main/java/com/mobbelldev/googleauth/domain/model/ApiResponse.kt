package com.mobbelldev.googleauth.domain.model

data class ApiResponse(
    val success: Boolean,
    val user: User? = null,
    val message: String? = null,
    @Transient
    val error: Exception? = null,
)
