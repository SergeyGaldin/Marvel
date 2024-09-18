package com.gateway.marvel.local_preferences

import android.content.SharedPreferences
import com.gateway.marvel.core.navigation.DestinationMainContent
import com.gateway.marvel.core.utils.CommonConstants
import com.gateway.marvel.core.utils.SettingsConstants
import javax.inject.Inject

class AppSettings @Inject constructor(
    private val preferences: SharedPreferences
) {
    var startDestination: String
        get() = preferences.getString(
            SettingsConstants.KEY_START_DESTINATION,
            DestinationMainContent.CHARACTERS_ROUTE
        ).toString()
        set(value) = preferences.edit()
            .putString(SettingsConstants.KEY_START_DESTINATION, value)
            .apply()

    var limitCharacters: Int
        get() = preferences.getInt(
            SettingsConstants.KEY_LIMIT_CHARACTERS,
            CommonConstants.LIMIT_CHARACTERS
        )
        set(value) = preferences.edit()
            .putInt(SettingsConstants.KEY_LIMIT_CHARACTERS, value)
            .apply()

    var startOffsetCharacters: Int
        get() = preferences.getInt(
            SettingsConstants.KEY_START_OFFSET_CHARACTERS,
            CommonConstants.START_OFFSET_CHARACTERS
        )
        set(value) = preferences.edit()
            .putInt(SettingsConstants.KEY_START_OFFSET_CHARACTERS, value)
            .apply()

    var limitComics: Int
        get() = preferences.getInt(
            SettingsConstants.KEY_LIMIT_COMICS,
            CommonConstants.LIMIT_COMICS
        )
        set(value) = preferences.edit()
            .putInt(SettingsConstants.KEY_LIMIT_COMICS, value)
            .apply()

    var startOffsetComics: Int
        get() = preferences.getInt(
            SettingsConstants.KEY_START_OFFSET_COMICS,
            CommonConstants.START_OFFSET_COMICS
        )
        set(value) = preferences.edit()
            .putInt(SettingsConstants.KEY_START_OFFSET_COMICS, value)
            .apply()
}