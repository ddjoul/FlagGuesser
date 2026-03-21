package com.example.flagguesser.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flagguesser.ui.theme.PanelColor

@Composable
fun TopPanel(
    title: String,
    showBack: Boolean = false,
    showNext: Boolean = false,
    onBack: () -> Unit = {},
    onNext: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(122.dp)
            .background(
                PanelColor,
                RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)
            )
    ) {

        if (showBack) {
            Text(
                "<",
                fontSize = 64.sp,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp)
                    .clickable { onBack() }
            )
        }

        if (showNext) {
            Text(
                ">",
                fontSize = 54.sp,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 16.dp)
                    .clickable { onNext() }
            )
        }

        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}