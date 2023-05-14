package com.seback.moviedbcompose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColorPalette = lightColors(
    primary = primary,
    primaryVariant = darkPrimary,
    secondary = accentVariant,
    secondaryVariant = accentVariantDark,
    background = lightPrimaryDarker,
    surface = lightPrimary
)

private val DarkColorPalette = darkColors(
    primary = primary,
    primaryVariant = darkPrimary,
    secondary = accent,
    secondaryVariant = accent,
    background = backgroundDark,
    surface = surfaceDark
)

@Composable
fun MovieDbComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}