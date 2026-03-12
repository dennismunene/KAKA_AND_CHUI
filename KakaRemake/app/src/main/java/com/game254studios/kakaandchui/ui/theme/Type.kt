package com.game254studios.kakaandchui.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.game254studios.kakaandchui.R

val CartoonFont = FontFamily(
    Font(R.font.cartoon_regular, FontWeight.Normal)
)

val KakaTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = CartoonFont,
        fontWeight = FontWeight.Bold,
        fontSize = 48.sp
    ),
    displayMedium = TextStyle(
        fontFamily = CartoonFont,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = CartoonFont,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = CartoonFont,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp
    ),
    titleLarge = TextStyle(
        fontFamily = CartoonFont,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    titleMedium = TextStyle(
        fontFamily = CartoonFont,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = CartoonFont,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = CartoonFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    labelLarge = TextStyle(
        fontFamily = CartoonFont,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    )
)
