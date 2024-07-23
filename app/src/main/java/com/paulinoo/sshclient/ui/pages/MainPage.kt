package com.paulinoo.sshclient.ui.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.paulinoo.sshclient.manager.database.SSHClientManager
import com.paulinoo.sshclient.manager.viewmodel.SSHClientManagerViewModel
import com.paulinoo.sshclient.ui.components.MyDrawer
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController, sshClientManagerViewModel: SSHClientManagerViewModel) {
    var currentDestination by remember { mutableStateOf("Home") }
    val sshClients by sshClientManagerViewModel.allClients.observeAsState(listOf())

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Box(modifier = Modifier.fillMaxWidth(0.8f)) {
                    MyDrawer(
                        onSshClick = {
                            currentDestination = "SSH"
                            scope.launch {
                                drawerState.close()
                            }
                            navController.navigate("main")
                        },
                        onSettingsClick = {
                            currentDestination = "Settings"
                            scope.launch {
                                drawerState.close()
                            }
                            navController.navigate("settings")
                        }
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = {
                                scope.launch {
                                    if (drawerState.isClosed) {
                                        drawerState.open()
                                    } else {
                                        drawerState.close()
                                    }
                                }
                            }) {
                                Icon(Icons.Filled.Menu, contentDescription = "Menu")
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "SSH Client")
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { navController.navigate("create") },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            },
            content = { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    LazyColumn(modifier = Modifier.padding(16.dp)) {
                        sshClients.let { clients ->
                            items(clients.size) { sshClient ->
                                CustomListItem(sshClient = clients[sshClient], onClick = {
                                    val currentClient = clients[sshClient]
                                    println(
                                        "UID: ${currentClient.uid} - Client: ${currentClient.name} - ${currentClient.host} - ${currentClient.username} - ${currentClient.password} - ${currentClient.port} - ${currentClient.commands}"
                                    )
                                    navController.navigate("edit/${currentClient.uid}")
                                })
                                HorizontalDivider()
                            }
                        }
                    }
                }
            }
        )
    }
}


@Composable
fun CustomListItem(sshClient: SSHClientManager, onClick: () -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = sshClient.name,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = { onClick() }) {
            Icon(Icons.Default.Edit, contentDescription = "Edit")
        }
    }
}



