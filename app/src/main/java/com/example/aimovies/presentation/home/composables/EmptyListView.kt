package com.example.aimovies.presentation.home.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.aimovies.presentation.ui.LocalSpacing
import com.example.aimovies.presentation.ui.theme.MovieYellow

/**
 * Created by A.Elkhami on 27/07/2023.
 */
@Composable
fun EmptyListView(imageVector: ImageVector, message: String) {
    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.spaceLarge),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector,
            contentDescription = null,
            tint = MovieYellow,
            modifier = Modifier
                .size(spacing.spaceLarge)
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        Text(text = message)
    }
}