package com.gateway.marvel.local_preferences.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.gateway.marvel.local_preferences.AppSettings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule {

    @Provides
    fun provideAppSettings(
        preferences: SharedPreferences
    ): AppSettings = AppSettings(preferences)

    @Provides
    fun providePreference(
        @ApplicationContext context: Context
    ): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
}