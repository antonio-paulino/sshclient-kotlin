package com.paulinoo.sshclient.ui.animations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset

@Composable
fun AnimatedScreen(content: @Composable () -> Unit) {
    println("Animation started")
    AnimatedVisibility(
        visible = true,
        enter = slideIn(
            initialOffset = { fullSize -> IntOffset(-fullSize.width, 0) },
            animationSpec = tween(1000)
        ).also { println("Entering: slideIn") },
        exit = slideOut(
            targetOffset = { fullSize -> IntOffset(fullSize.width, 0) },
            animationSpec = tween(1000)
        ).also { println("Exiting: slideOut") }
    ) {
        content()
    }
    println("Animation ended")
}