package com.example.flagguesser.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.flagguesser.ui.theme.BgColor

@Composable
fun BaseScreen(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
    ) {
        content()
    }
}