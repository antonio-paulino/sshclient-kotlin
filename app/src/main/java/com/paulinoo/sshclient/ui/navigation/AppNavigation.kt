package com.paulinoo.sshclient.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.paulinoo.sshclient.ui.animations.AnimatedScreen
import com.paulinoo.sshclient.ui.pages.CreateClientPage
import com.paulinoo.sshclient.ui.pages.MainScreen
import com.paulinoo.sshclient.ui.pages.SettingsScreen
import com.paulinoo.sshclient.ui.theme.ThemeViewModel

@Composable
fun AppNavigation(themeViewModel: ThemeViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            AnimatedScreen {
                MainScreen(navController)
            }
        }
        composable("settings") {
            AnimatedScreen {
                SettingsScreen(navController, themeViewModel::toggleTheme)
            }
        }
        composable("create") {
            AnimatedScreen {
                CreateClientPage(navController)
            }
        }
    }
}