package com.gateway.marvel.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.gateway.marvel.local_preferences.AppSettings
import com.gateway.marvel.ui_kit.theme.MarvelTheme

import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var appSettings: AppSettings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MarvelTheme {
                MainScreen(
                    startDestination = appSettings.startDestination
                )
            }
        }
    }
}