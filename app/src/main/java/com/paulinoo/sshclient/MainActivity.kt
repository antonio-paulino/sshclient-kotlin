package com.paulinoo.sshclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.paulinoo.sshclient.ui.theme.SshclientTheme
import com.paulinoo.sshclient.ui.theme.ThemeViewModel
import androidx.compose.runtime.livedata.observeAsState
import com.paulinoo.sshclient.ui.navigation.AppNavigation
import com.paulinoo.sshclient.ui.theme.PreferenceManager


class MainActivity : ComponentActivity() {
    private val themeViewModel by viewModels<ThemeViewModel>()
    private lateinit var preferenceManager: PreferenceManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceManager = PreferenceManager(this)
        val isDarkThemePref = preferenceManager.getThemePreference()
        themeViewModel.setDarkTheme(isDarkThemePref)

        setContent {
            val isDarkTheme = themeViewModel.isDarkTheme.observeAsState(isDarkThemePref)
            SshclientTheme(darkTheme = isDarkTheme.value) {
                AppNavigation(themeViewModel)
            }
        }

        // Observe changes in the theme preference from the ViewModel and save it
        themeViewModel.isDarkTheme.observe(this) { isDark ->
            preferenceManager.saveThemePreference(isDark)
        }
    }
}







