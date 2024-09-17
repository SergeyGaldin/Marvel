package com.gateway.marvel.feature_settings

import androidx.lifecycle.ViewModel
import com.gateway.marvel.local_preferences.AppSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val appSettings: AppSettings
) : ViewModel() {
    var startDestination: String
        get() = appSettings.startDestination
        set(value) {
            appSettings.startDestination = value
        }
}