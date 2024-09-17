package com.paulinoo.sshclient.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.paulinoo.sshclient.manager.viewmodel.SSHClientManagerViewModel
import com.paulinoo.sshclient.ui.animations.AnimatedScreen
import com.paulinoo.sshclient.ui.pages.AboutPage
import com.paulinoo.sshclient.ui.pages.CreateClientPage
import com.paulinoo.sshclient.ui.pages.EditClientPage
import com.paulinoo.sshclient.ui.pages.MainScreen
import com.paulinoo.sshclient.ui.pages.SettingsScreen
import com.paulinoo.sshclient.ui.theme.ThemeViewModel

@Composable
fun AppNavigation(
    themeViewModel: ThemeViewModel,
    sshClientManagerViewModel: SSHClientManagerViewModel
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            AnimatedScreen {
                MainScreen(navController, sshClientManagerViewModel)
            }
        }
        composable("settings") {
            AnimatedScreen {
                SettingsScreen(navController, themeViewModel::toggleTheme)
            }
        }
        composable("create") {
            AnimatedScreen {
                CreateClientPage(navController, isNew = true,  sshClientManagerViewModel = sshClientManagerViewModel)
            }
        }
        composable(
            route = "edit/{clientId}",
            arguments = listOf(navArgument("clientId") { type = NavType.IntType })
        ) { backStackEntry ->
            val clientId = backStackEntry.arguments?.getInt("clientId")
            clientId?.let {
                AnimatedScreen {

                    EditClientPage(navController, isNew = false, clientID = it, sshClientManagerViewModel)

                }
            }
        }
        composable(
            route= "about"
        ){
            AnimatedScreen {
                AboutPage(navController)
            }
        }
    }
}