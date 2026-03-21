package com.example.flagguesser.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.flagguesser.ui.theme.*

@Composable
fun MainButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .height(46.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .background(
                Brush.horizontalGradient(
                    listOf(PanelColor, BgColor)
                )
            )
            .border(2.dp, StrokeColor, RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )
    }
}