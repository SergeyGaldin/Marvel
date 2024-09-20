package com.gateway.marvel

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.gateway.marvel.local_preferences.AppSettings
import com.gateway.marvel.main.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class MainActivityTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var appSettings: AppSettings

    @Before
    fun setUpTests() = runTest {
        hiltRule.inject()
    }

    @Test
    fun testOpen() = runTest {
        composeTestRule.onNodeWithText("Настройки").assertIsDisplayed()
    }
}