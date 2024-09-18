package com.gateway.marvel.local_preferences

import android.content.SharedPreferences
import com.gateway.marvel.core.navigation.DestinationMainContent
import javax.inject.Inject

class AppSettings @Inject constructor(
    private val preferences: SharedPreferences
) {
    var startDestination: String
        get() = preferences.getString(
            KEY_START_DESTINATION,
            DestinationMainContent.CHARACTERS_ROUTE
        ).toString()
        set(value) = preferences.edit()
            .putString(KEY_START_DESTINATION, value)
            .apply()

    var limitCharacters: Int
        get() = preferences.getInt(
            KEY_LIMIT_CHARACTERS,
            LIMIT_CHARACTERS
        )
        set(value) = preferences.edit()
            .putInt(KEY_LIMIT_CHARACTERS, value)
            .apply()

    var startOffsetCharacters: Int
        get() = preferences.getInt(
            KEY_START_OFFSET_CHARACTERS,
            START_OFFSET_CHARACTERS
        )
        set(value) = preferences.edit()
            .putInt(KEY_START_OFFSET_CHARACTERS, value)
            .apply()

    var limitComics: Int
        get() = preferences.getInt(
            KEY_LIMIT_COMICS,
            LIMIT_COMICS
        )
        set(value) = preferences.edit()
            .putInt(KEY_LIMIT_COMICS, value)
            .apply()

    var startOffsetComics: Int
        get() = preferences.getInt(
            KEY_START_OFFSET_COMICS,
            START_OFFSET_COMICS
        )
        set(value) = preferences.edit()
            .putInt(KEY_START_OFFSET_COMICS, value)
            .apply()

    companion object {
        private const val KEY_START_DESTINATION = "key_start_destination"
        private const val KEY_LIMIT_CHARACTERS = "key_limit_characters"
        private const val KEY_START_OFFSET_CHARACTERS = "key_start_offset_characters"
        private const val KEY_LIMIT_COMICS = "key_limit_comics"
        private const val KEY_START_OFFSET_COMICS = "key_start_offset_comics"

        private const val LIMIT_CHARACTERS = 20
        private const val START_OFFSET_CHARACTERS = 0
        private const val LIMIT_COMICS = 20
        private const val START_OFFSET_COMICS = 0
    }
}