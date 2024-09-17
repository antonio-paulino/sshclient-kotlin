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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.paulinoo.sshclient.R
import com.paulinoo.sshclient.manager.database.SSHClientManager
import com.paulinoo.sshclient.manager.viewmodel.SSHClientManagerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditClientPage(
    navController: NavHostController,
    isNew: Boolean = false,
    clientID: Int,
    sshClientManagerViewModel: SSHClientManagerViewModel
) {


    var client = remember {
        mutableStateOf<SSHClientManager>(
            SSHClientManager(
                name = "",
                host = "",
                username = "",
                password = "",
                port = "",
                commands = LinkedHashMap()
            )
        )
    }
    val nameController = remember { mutableStateOf(client.value.name) }
    val hostController = remember { mutableStateOf(client.value.host) }
    val usernameController = remember { mutableStateOf(client.value.username) }
    val passwordController = remember { mutableStateOf(client.value.password) }
    val portController = remember { mutableStateOf(client.value.port.toString()) }

    LaunchedEffect(key1 = clientID) {
        val fetchedClient = sshClientManagerViewModel.getById(clientID)
        client.value = fetchedClient ?: SSHClientManager(
            name = "",
            host = "",
            username = "",
            password = "",
            port = "",
            commands = LinkedHashMap()
        )
        // Update controllers with fetched client data
        nameController.value = client.value.name
        hostController.value = client.value.host
        usernameController.value = client.value.username
        passwordController.value = client.value.password
        portController.value = client.value.port
    }


    var isFormValid by remember {
        mutableStateOf(true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = { Text(if (isNew) stringResource(R.string.create_client) else stringResource(R.string.edit_client)) },
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
                        val newClient = client.value.copy(
                            name = nameController.value,
                            host = hostController.value,
                            username = usernameController.value,
                            password = passwordController.value,
                            port = portController.value
                        )

                        sshClientManagerViewModel.update(newClient)

                        navController.navigateUp()

                    }
                }) {
                    // ADD THE SAVE ICON
                    Icon(Icons.Default.Check, contentDescription = "Save")
                }

                IconButton(onClick = {
                    sshClientManagerViewModel.delete(client.value.uid)
                    navController.navigateUp()
                }) {
                    // ADD THE DELETE ICON
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }

            }
        )

        Column(
            Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .weight(weight = 1f, fill = false)
        ) {
            Text(text = stringResource(R.string.general), style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = nameController.value,
                onValueChange = { nameController.value = it },
                label = { Text(stringResource(R.string.name)) },
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

            Text(text = stringResource(R.string.connection), style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(16.dp))

            Row {
                TextField(
                    value = hostController.value,
                    onValueChange = { hostController.value = it },
                    label = { Text(stringResource(R.string.host)) },
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
                    label = { Text(stringResource(R.string.port)) },
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
                label = { Text(stringResource(R.string.username)) },
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
                label = { Text(stringResource(R.string.password)) },
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
                    text = stringResource(R.string.error_form),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

fun validateForm(
    name: String,
    host: String,
    username: String,
    password: String,
    port: String,
): Boolean {
    return name.isNotEmpty() && host.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty()
}
