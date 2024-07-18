package com.paulinoo.sshclient.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFE0E0E0),
    onPrimary = Color.Black,
    secondary = Color(0xFFBDBDBD),
    background = Color(0xFFE0E0E0),
    surface = Color(0xFFE0E0E0),
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onError = Color.White,
    error = Color(0xFFB00020)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF424242),
    onPrimary = Color(0xFFBDBDBD),
    secondary = Color(0xFF616161),
    background = Color(0xFF212121),
    surface = Color(0xFF212121),
    onSecondary = Color.White,
    onBackground = Color.White,
    onError = Color.White,
    error = Color(0xFFCF6679)
)

@Composable
fun SshclientTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    drawerOpen: Boolean = false, // New parameter to indicate drawer state
    content: @Composable () -> Unit
) {
    val colors = when {
        darkTheme -> DarkColorScheme
        drawerOpen -> darkColorScheme( // Apply a darker theme when the drawer is open in light mode
            primary = Color(0xFF424242),
            onPrimary = Color(0xFFBDBDBD),
            secondary = Color(0xFF616161),
            background = Color(0xFF121212), // Darker shade for background
            surface = Color(0xFF121212),
            onSecondary = Color.White,
            onBackground = Color.White,
            onError = Color.White,
            error = Color(0xFFCF6679)
        )
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}



class ThemeViewModel : ViewModel() {
    private val _isDarkTheme = MutableLiveData(false)
    val isDarkTheme: LiveData<Boolean> = _isDarkTheme

    fun toggleTheme() {
        _isDarkTheme.value = !(_isDarkTheme.value ?: false)
    }

    fun setDarkTheme(isDarkTheme: Boolean) {
        _isDarkTheme.value = isDarkTheme
    }
}