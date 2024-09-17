package com.gateway.marvel.local_preferences

import android.content.SharedPreferences
import com.gateway.marvel.core.navigation.DestinationMainContent
import com.gateway.marvel.core.utils.SettingsConstants
import javax.inject.Inject

class AppSettings @Inject constructor(
    private val preferences: SharedPreferences
) {
    var startDestination: String
        get() = preferences.getString(
            SettingsConstants.START_DESTINATION,
            DestinationMainContent.CHARACTERS_ROUTE
        ).toString()
        set(value) = preferences.edit()
            .putString(SettingsConstants.START_DESTINATION, value)
            .apply()
}