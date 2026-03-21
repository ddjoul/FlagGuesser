package com.example.flagguesser.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.sp
import com.example.flagguesser.R

val Iosevka = FontFamily(
    Font(R.font.iosevka_charon_medium, FontWeight.Medium)
)

val AppTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = Iosevka,
        fontSize = 48.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Iosevka,
        fontSize = 32.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Iosevka,
        fontSize = 30.sp
    )
)