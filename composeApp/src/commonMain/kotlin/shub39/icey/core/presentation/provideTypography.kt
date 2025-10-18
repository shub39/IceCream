package shub39.icey.core.presentation

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import icecream.composeapp.generated.resources.Res
import icecream.composeapp.generated.resources.manrope
import org.jetbrains.compose.resources.Font

@Composable
fun provideTypography(
    scale: Float = 1f,
): Typography {
    val selectedFont = FontFamily(Font(Res.font.manrope))

    return Typography(
        displayLarge = TextStyle(
            fontFamily = selectedFont,
            fontSize = 57.sp * scale,
            lineHeight = 64.sp * scale,
            letterSpacing = -(0.25).sp,
        ),
        displayMedium = TextStyle(
            fontFamily = selectedFont,
            fontSize = 45.sp * scale,
            lineHeight = 52.sp * scale
        ),
        displaySmall = TextStyle(
            fontFamily = selectedFont,
            fontSize = 36.sp * scale,
            lineHeight = 44.sp * scale
        ),
        headlineLarge = TextStyle(
            fontFamily = selectedFont,
            fontSize = 32.sp * scale,
            lineHeight = 40.sp * scale
        ),
        headlineMedium = TextStyle(
            fontFamily = selectedFont,
            fontSize = 28.sp * scale,
            lineHeight = 36.sp * scale
        ),
        headlineSmall = TextStyle(
            fontFamily = selectedFont,
            fontSize = 24.sp * scale,
            lineHeight = 32.sp * scale
        ),
        titleLarge = TextStyle(
            fontFamily = selectedFont,
            fontSize = 22.sp * scale,
            lineHeight = 28.sp * scale
        ),
        titleMedium = TextStyle(
            fontFamily = selectedFont,
            fontSize = 16.sp * scale,
            lineHeight = 24.sp * scale,
            letterSpacing = 0.15.sp
        ),
        titleSmall = TextStyle(
            fontFamily = selectedFont,
            fontSize = 14.sp * scale,
            lineHeight = 20.sp * scale,
            letterSpacing = 0.1.sp
        ),
        labelLarge = TextStyle(
            fontFamily = selectedFont,
            fontSize = 16.sp * scale,
            lineHeight = 16.sp * scale,
            letterSpacing = 0.1.sp
        ),
        labelMedium = TextStyle(
            fontFamily = selectedFont,
            fontSize = 14.sp * scale,
            lineHeight = 14.sp * scale,
            letterSpacing = 0.5.sp
        ),
        labelSmall = TextStyle(
            fontFamily = selectedFont,
            fontSize = 12.sp * scale,
            lineHeight = 12.sp * scale,
            letterSpacing = 0.5.sp
        ),
        bodyLarge = TextStyle(
            fontFamily = selectedFont,
            fontSize = 16.sp * scale,
            lineHeight = 24.sp * scale,
            letterSpacing = 0.5.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = selectedFont,
            fontSize = 14.sp * scale,
            lineHeight = 20.sp * scale,
            letterSpacing = 0.25.sp
        ),
        bodySmall = TextStyle(
            fontFamily = selectedFont,
            fontSize = 12.sp * scale,
            lineHeight = 16.sp * scale,
            letterSpacing = 0.4.sp
        )
    )
}