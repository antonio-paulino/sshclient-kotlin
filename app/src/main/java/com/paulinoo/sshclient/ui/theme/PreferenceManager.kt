package com.paulinoo.sshclient.ui.theme

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences("theme_preferences", Context.MODE_PRIVATE)

    companion object {
        private const val DARK_THEME_KEY = "DARK_THEME"
    }

    fun saveThemePreference(isDarkTheme: Boolean) {
        preferences.edit().putBoolean(DARK_THEME_KEY, isDarkTheme).apply()
    }

    fun getThemePreference(): Boolean {
        return preferences.getBoolean(DARK_THEME_KEY, false) // Default to light theme
    }
}