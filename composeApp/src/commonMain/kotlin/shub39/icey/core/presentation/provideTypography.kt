package shub39.icey.core.presentation

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import icecream.composeapp.generated.resources.Res
import icecream.composeapp.generated.resources.henny_penny
import icecream.composeapp.generated.resources.schollbell
import org.jetbrains.compose.resources.Font

@Composable
fun provideTypography(
    scale: Float = 1f,
): Typography {
    val titleFont = FontFamily(Font(Res.font.henny_penny))
    val bodyFont = FontFamily(Font(Res.font.schollbell))

    return Typography(
        displayLarge = TextStyle(
            fontFamily = titleFont,
            fontSize = 57.sp * scale,
            lineHeight = 64.sp * scale,
            letterSpacing = -(0.25).sp,
        ),
        displayMedium = TextStyle(
            fontFamily = titleFont,
            fontSize = 45.sp * scale,
            lineHeight = 52.sp * scale
        ),
        displaySmall = TextStyle(
            fontFamily = titleFont,
            fontSize = 36.sp * scale,
            lineHeight = 44.sp * scale
        ),
        headlineLarge = TextStyle(
            fontFamily = titleFont,
            fontSize = 32.sp * scale,
            lineHeight = 40.sp * scale
        ),
        headlineMedium = TextStyle(
            fontFamily = titleFont,
            fontSize = 28.sp * scale,
            lineHeight = 36.sp * scale
        ),
        headlineSmall = TextStyle(
            fontFamily = titleFont,
            fontSize = 24.sp * scale,
            lineHeight = 32.sp * scale
        ),
        titleLarge = TextStyle(
            fontFamily = titleFont,
            fontSize = 22.sp * scale,
            lineHeight = 28.sp * scale
        ),
        titleMedium = TextStyle(
            fontFamily = titleFont,
            fontSize = 16.sp * scale,
            lineHeight = 24.sp * scale,
            letterSpacing = 0.15.sp
        ),
        titleSmall = TextStyle(
            fontFamily = titleFont,
            fontSize = 14.sp * scale,
            lineHeight = 20.sp * scale,
            letterSpacing = 0.1.sp
        ),
        labelLarge = TextStyle(
            fontFamily = bodyFont,
            fontSize = 16.sp * scale,
            lineHeight = 16.sp * scale,
            letterSpacing = 0.1.sp
        ),
        labelMedium = TextStyle(
            fontFamily = bodyFont,
            fontSize = 14.sp * scale,
            lineHeight = 14.sp * scale,
            letterSpacing = 0.5.sp
        ),
        labelSmall = TextStyle(
            fontFamily = bodyFont,
            fontSize = 12.sp * scale,
            lineHeight = 12.sp * scale,
            letterSpacing = 0.5.sp
        ),
        bodyLarge = TextStyle(
            fontFamily = bodyFont,
            fontSize = 16.sp * scale,
            lineHeight = 24.sp * scale,
            letterSpacing = 0.5.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = bodyFont,
            fontSize = 14.sp * scale,
            lineHeight = 20.sp * scale,
            letterSpacing = 0.25.sp
        ),
        bodySmall = TextStyle(
            fontFamily = bodyFont,
            fontSize = 12.sp * scale,
            lineHeight = 16.sp * scale,
            letterSpacing = 0.4.sp
        )
    )
}