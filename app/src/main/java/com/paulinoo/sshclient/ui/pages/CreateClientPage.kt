package com.paulinoo.sshclient.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.paulinoo.sshclient.manager.database.SSHClientManager
import com.paulinoo.sshclient.manager.viewmodel.SSHClientManagerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateClientPage(
    navController: NavHostController,

    isNew: Boolean = true,
    client: SSHClientManager = SSHClientManager(0,"Test", "", "", "", "22", LinkedHashMap()),
    sshClientManagerViewModel: SSHClientManagerViewModel
) {
    val nameController = remember { mutableStateOf(client.name) }
    val hostController = remember { mutableStateOf(client.host) }
    val usernameController = remember { mutableStateOf(client.username) }
    val passwordController = remember { mutableStateOf(client.password) }
    val portController = remember { mutableStateOf(client.port) }

    var isFormValid by remember {
        mutableStateOf(true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = { Text(if (isNew) "Create Client" else "Edit Client") },
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            actions = {
                IconButton(onClick = {
                    isFormValid = validateForm(
                        nameController.value, hostController.value, usernameController.value,
                        passwordController.value, portController.value
                    )
                    if (isFormValid) {
                        val newClient = SSHClientManager(
                            name = nameController.value,
                            host = hostController.value,
                            username = usernameController.value,
                            password = passwordController.value,
                            port = portController.value,
                            commands = LinkedHashMap()
                        )

                        // Save the new client
                        sshClientManagerViewModel.insert(newClient)
                        navController.navigateUp()
                    }
                }) {
                    // ADD THE SAVE ICON
                    Icon(Icons.Default.Check, contentDescription = "Save")
                }
            }
        )

        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "General", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = nameController.value,
                onValueChange = { nameController.value = it },
                label = { Text("Name") },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.primary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                    focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onSecondary,
                    focusedIndicatorColor = MaterialTheme.colorScheme.onPrimary,
                    cursorColor = MaterialTheme.colorScheme.onPrimary,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.onPrimary,
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Connection", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(16.dp))

            Row {
                TextField(
                    value = hostController.value,
                    onValueChange = { hostController.value = it },
                    label = { Text("Host") },
                    modifier = Modifier.weight(1f),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.primary,
                        unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                        focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSecondary,
                        focusedIndicatorColor = MaterialTheme.colorScheme.onPrimary,
                        cursorColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
                Spacer(modifier = Modifier.width(16.dp))
                TextField(
                    value = portController.value,
                    onValueChange = { portController.value = it },
                    label = { Text("Port") },
                    modifier = Modifier.weight(0.2f),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.primary,
                        unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                        focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSecondary,
                        focusedIndicatorColor = MaterialTheme.colorScheme.onPrimary,
                        cursorColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = usernameController.value,
                onValueChange = { usernameController.value = it },
                label = { Text("Username") },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.primary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                    focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onSecondary,
                    focusedIndicatorColor = MaterialTheme.colorScheme.onPrimary,
                    cursorColor = MaterialTheme.colorScheme.onPrimary
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = passwordController.value,
                onValueChange = { passwordController.value = it },
                label = { Text("Password") },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.primary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                    focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onSecondary,
                    focusedIndicatorColor = MaterialTheme.colorScheme.onPrimary,
                    cursorColor = MaterialTheme.colorScheme.onPrimary
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (!isFormValid) {
                Text(
                    text = "Please fill out all fields correctly.",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}