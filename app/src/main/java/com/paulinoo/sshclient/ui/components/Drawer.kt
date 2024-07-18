package com.paulinoo.sshclient.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paulinoo.sshclient.R

@Composable
fun MyDrawer(
    onSshClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    val isDarkTheme = MaterialTheme.colorScheme.background.luminance() < 0.5
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxHeight()
    ) {
        // Header
        Box(
            modifier = Modifier
                .height(100.dp)
                .background(MaterialTheme.colorScheme.primary)
                .fillMaxWidth()
                .padding(bottom = 2.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "SSH Client",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 30.sp
                )
            )


        }
        HorizontalDivider(color = MaterialTheme.colorScheme.onPrimary, thickness = 1.dp)

        // SSH Option
        DrawerTile(
            title = "SSH",
            icon = if ( isDarkTheme ) painterResource(id = R.drawable.icon_light) else painterResource(id = R.drawable.icon),
            onClick = onSshClick
        )

        // Settings Option
        DrawerTile(
            title = "Settings",
            icon = Icons.Default.Settings,
            onClick = onSettingsClick
        )
    }
}