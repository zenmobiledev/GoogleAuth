package com.mobbelldev.googleauth.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.mobbelldev.googleauth.data.remote.KtorApi
import com.mobbelldev.googleauth.util.Constant.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.JavaNetCookieJar
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.net.CookieManager
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    // this cookie manager will be used to actually attach a cookie to each and every request that we make from our client
    fun provideCookieManager(): CookieManager {
        return CookieManager()
    }

    @Provides
    @Singleton
    fun provideHttpClient(cookieManager: CookieManager): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(
                15,
                TimeUnit.SECONDS
            )
            .connectTimeout(
                15,
                TimeUnit.SECONDS
            )
            .cookieJar(JavaNetCookieJar(cookieManager))
            .build()
    }

    @Provides
    @Singleton
    @OptIn(ExperimentalSerializationApi::class)
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val contentType = MediaType.get("application/json")
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory(contentType = contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideKtorApi(retrofit: Retrofit): KtorApi {
        return retrofit.create(KtorApi::class.java)
    }
}