package com.example.myapplication.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.myapplication.R


@Immutable
data class MatuleColors(
    val block: Color,
    val text: Color,
    val subTextDark: Color,
    val background:Color,
    val hint: Color,
    val accent: Color
)

@Immutable
data class MatuleTextStyle(
    val headingBold32: TextStyle,
    val subTitleRegular16: TextStyle,
    val bodyRegular16: TextStyle,
    val bodyRegular14: TextStyle,
    val bodyRegular12: TextStyle,
    val headingBold24: TextStyle,
)

val LocalMatuleTypography = staticCompositionLocalOf {
    MatuleTextStyle(
        headingBold32 = TextStyle.Default,
        subTitleRegular16 = TextStyle.Default,
        bodyRegular16 = TextStyle.Default,
        bodyRegular14 = TextStyle.Default,
        bodyRegular12 = TextStyle.Default,
        headingBold24 = TextStyle(fontFamily = matuleFontFamily, fontWeight = FontWeight.Bold, fontSize = 32.sp)
    )
}

val LocalMatuleColors = staticCompositionLocalOf {
    MatuleColors(
        block = Color.Unspecified,
        text = Color.Unspecified,
        subTextDark = Color.Unspecified,
        background = Color.Unspecified,
        hint = Color.Unspecified,
        accent = Color.Unspecified
    )
}

val matuleFontFamily = FontFamily(
    Font(R.font.roboto_serif, FontWeight.Normal),
    Font(R.font.roboto_serif_bold, FontWeight.Bold),
    Font(R.font.roboto_serif_black, FontWeight.Black),
    Font(R.font.roboto_serif_medium, FontWeight.Medium),
    Font(R.font.roboto_serif_extrabold, FontWeight.ExtraBold),
    Font(R.font.roboto_serif_semibold, FontWeight.SemiBold),
)

@Composable
fun MatuleTheme( content: @Composable () -> Unit){
    val matuleColors = MatuleColors(
        block = Color(0xFFFFE5F2),
        text = Color(0xFF660033),
        subTextDark = Color(0xFFC189A5),
        background = Color(0xFFFFE5F2),
        hint = Color(0xFF6A6A6A),
        accent = Color(0xFF660033)
        //F2AFD1
        //subTextDark = Color(0xFF707B81)
        //text = Color(0xFF2B2B2B)
    )
    val matuleTypography = MatuleTextStyle(
        headingBold32 = TextStyle(fontFamily = matuleFontFamily, fontWeight = FontWeight.Bold, fontSize = 32.sp),
        headingBold24 = TextStyle(fontFamily = matuleFontFamily, fontWeight = FontWeight.Bold, fontSize = 24.sp),
        subTitleRegular16 = TextStyle(fontFamily = matuleFontFamily, fontWeight = FontWeight.Normal, fontSize = 16.sp),
        bodyRegular16 = TextStyle(fontFamily = matuleFontFamily, fontWeight = FontWeight.Normal, fontSize = 16.sp),
        bodyRegular14 = TextStyle(fontFamily = matuleFontFamily, fontWeight = FontWeight.Normal, fontSize = 14.sp),
        bodyRegular12 = TextStyle(fontFamily = matuleFontFamily, fontWeight = FontWeight.Normal, fontSize = 12.sp),
    )
    CompositionLocalProvider(
        LocalMatuleColors provides matuleColors,
        LocalMatuleTypography provides matuleTypography,
        content = content
    )
}

object MatuleTheme{
    val colors: MatuleColors
        @Composable
        get() = LocalMatuleColors.current
    val typography
        @Composable
        get() = LocalMatuleTypography.current
}