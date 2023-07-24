package com.example.aimovies.presentation.ui

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Created by A.Elkhami on 18/07/2023.
 */
data class Dimensions(
    val default: Dp = 0.dp,
    val spaceExtraSmall: Dp = 4.dp,
    val spaceSmall: Dp = 8.dp,
    val spaceMedium: Dp = 16.dp,
    val mainTitleVerticalPadding: Dp = 20.dp,
    val spaceLarge: Dp = 32.dp,
    val spaceExtraLarge: Dp = 64.dp,
    val spaceDoubleExtraLarge: Dp = 128.dp,
    val curvedCornerSize: Dp = 20.dp,
    val ratingIconSize: Dp = 21.dp,
    val loadingIconBorder: Dp = 5.dp,
    val loadingIconSize: Dp = 60.dp,
    val placeholderWidth: Dp = 130.dp,
    val toggleButtonWidth: Dp = 160.dp,
    val placeholderHeight: Dp = 180.dp,
    val discoverMoviesLoaderTopPadding: Dp = 80.dp,
    val fontTitle: TextUnit = 26.sp
)

val LocalSpacing = compositionLocalOf { Dimensions() }