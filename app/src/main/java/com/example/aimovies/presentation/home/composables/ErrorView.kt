package com.example.aimovies.presentation.home.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.aimovies.presentation.ui.LocalSpacing
import com.example.aimovies.presentation.ui.theme.MovieYellow

/**
 * Created by A.Elkhami on 24/07/2023.
 */
@Composable
fun ErrorView(errorMessage: String, onRefresh: () -> Unit) {

    val spacing = LocalSpacing.current

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = errorMessage,
            fontWeight = FontWeight.Bold,
            fontSize = spacing.fontTitle,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(
                    start = spacing.spaceMedium,
                    end = spacing.spaceMedium,
                    top = spacing.spaceLarge
                )
                .fillMaxWidth()
        )
        Button(
            modifier = Modifier.padding(spacing.spaceMedium),
            onClick = { onRefresh() },
            colors = ButtonDefaults.buttonColors(MovieYellow)
        ) {
            Text(text = "Try again", color = Color.Black)
        }
    }
}