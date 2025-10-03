package com.mobbelldev.googleauth.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.mobbelldev.googleauth.data.remote.KtorApi
import com.mobbelldev.googleauth.data.repository.DataStoreOperationsImpl
import com.mobbelldev.googleauth.data.repository.RepositoryImpl
import com.mobbelldev.googleauth.domain.repository.DataStoreOperations
import com.mobbelldev.googleauth.domain.repository.Repository
import com.mobbelldev.googleauth.util.Constant.PREFERENCES_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideDataStorePreferences(@ApplicationContext context: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(produceFile = { context.preferencesDataStoreFile(name = PREFERENCES_NAME) })

    @Provides
    @Singleton
    fun provideDataStoreOperations(dataStore: DataStore<Preferences>): DataStoreOperations =
        DataStoreOperationsImpl(dataStore = dataStore)

    @Provides
    @Singleton
    fun provideRepository(
        dataStoreOperations: DataStoreOperations,
        ktorApi: KtorApi,
    ): Repository =
        RepositoryImpl(
            dataStoreOperations = dataStoreOperations,
            ktorApi = ktorApi
        )
}