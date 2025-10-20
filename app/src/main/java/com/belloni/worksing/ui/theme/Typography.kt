package com.belloni.worksing.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.belloni.worksing.R

// Assuming font files are in res/font with these names
val OpenSans = FontFamily(
    Font(R.font.open_sans_regular, FontWeight.Normal)
)

val Antonio = FontFamily(
    Font(R.font.antonio_regular, FontWeight.Normal)
)

// Corrected to WorkSignTypography to avoid conflict and match branding
val WorkSignTypography = Typography(
    // For large titles, like user names - Final, very large size for tablets
    titleLarge = TextStyle(
        fontFamily = Antonio,
        fontWeight = FontWeight.Bold,
        fontSize = 60.sp,
        lineHeight = 64.sp,
        letterSpacing = 0.sp
    ),
    // For smaller body text, like 'tareas nuevas' - Final, very large size for tablets
    bodyLarge = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.5.sp
    )
)
