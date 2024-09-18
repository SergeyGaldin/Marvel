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

    var limitCharacters: Int
        get() = appSettings.limitCharacters
        set(value) {
            appSettings.limitCharacters = value
        }

    var startOffsetCharacters: Int
        get() = appSettings.startOffsetCharacters
        set(value) {
            appSettings.startOffsetCharacters = value
        }

    var limitComics: Int
        get() = appSettings.limitComics
        set(value) {
            appSettings.limitComics = value
        }

    var startOffsetComics: Int
        get() = appSettings.startOffsetComics
        set(value) {
            appSettings.startOffsetComics = value
        }
}