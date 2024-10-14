package com.capco.cineworld.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

// Light theme colors
private val LightColors = lightColorScheme(
    primary = PrimaryColor,
    onPrimary = OnPrimaryColor,
    // Add more colors as needed
)

// Dark theme colors
private val DarkColors = darkColorScheme(
    primary = PrimaryDarkColor,
    onPrimary = OnPrimaryDarkColor,
    // Add more colors as needed
)

@Composable
fun MyAppTheme(
    darkTheme: Boolean = false, // Set to true for dark theme, if needed
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColors
    } else {
        LightColors
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,  // Define typography if needed
        shapes = Shapes,          // Define shapes if needed
        content = content
    )
}
