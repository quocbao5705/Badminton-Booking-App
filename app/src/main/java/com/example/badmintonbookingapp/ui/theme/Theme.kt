package com.example.badmintonbookingapp.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = BadmiPrimary,
    onPrimary = BadmiTextWhite,
    primaryContainer = BadmiPrimaryContainer,
    onPrimaryContainer = BadmiPrimaryDeep,
    secondary = BadmiMint,
    onSecondary = BadmiTextWhite,
    secondaryContainer = BadmiMintSoft,
    onSecondaryContainer = BadmiPrimaryDark,
    background = BadmiBackground,
    onBackground = BadmiTextPrimary,
    surface = BadmiSurface,
    onSurface = BadmiTextPrimary,
    surfaceVariant = BadmiSurfaceVariant,
    onSurfaceVariant = BadmiTextSecondary,
    outline = BadmiBorder,
    error = BadmiDanger,
    onError = BadmiTextWhite
)

private val DarkColorScheme = darkColorScheme(
    primary = BadmiMint,
    onPrimary = BadmiPrimaryDeep,
    primaryContainer = BadmiPrimaryDark,
    onPrimaryContainer = BadmiMintSoft,
    secondary = BadmiMint,
    onSecondary = BadmiPrimaryDeep,
    background = BadmiPrimaryDeep,
    onBackground = BadmiSurfaceVariant,
    surface = BadmiPrimaryDark,
    onSurface = BadmiSurfaceVariant,
    surfaceVariant = BadmiPrimaryDeep,
    onSurfaceVariant = BadmiBorder,
    outline = BadmiPrimaryLight.copy(alpha = 0.2f),
    error = BadmiDanger,
    onError = BadmiTextWhite
)

@Composable
fun BadmintonBookingAppTheme(
    darkTheme: Boolean = false, // Default to clean light green theme matching Figma
    dynamicColor: Boolean = false, // Keep consistent Figma branding
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window ?: return@SideEffect
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}