package com.example.aimovies.presentation.home.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.aimovies.presentation.ui.theme.AIMoviesTheme
import com.example.aimovies.presentation.ui.theme.MovieYellow

/**
 * Created by A.Elkhami on 22/06/2023.
 */
@Composable
fun ToggleButton(
    modifier: Modifier,
    currentSelection: String,
    buttons: List<String>,
    selectedTint: Color = MovieYellow,
    unselectedTint: Color = Color.Unspecified,
    width : Dp = 120.dp,
    onButtonClick: (String) -> Unit
) {
    Row(
        modifier = modifier.border(
            border = BorderStroke(1.dp, Color.Gray),
            shape = RoundedCornerShape(32.dp)
        )
    ) {
        buttons.forEachIndexed { index, text ->
            val isSelected = currentSelection == text
            val backgroundTint = if (isSelected) selectedTint else unselectedTint
            val textColor = if (isSelected) Color.Black else Color.Unspecified

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .width(width)
                    .clip(
                        if (index == 0) {
                            RoundedCornerShape(topStart = 32.dp, bottomStart = 32.dp)
                        } else {
                            RoundedCornerShape(topEnd = 32.dp, bottomEnd = 32.dp)
                        }
                    )
                    .toggleable(value = isSelected, enabled = true, onValueChange = { selected ->
                        if (selected) {
                            onButtonClick(text)
                        }
                    }
                    )
                    .background(backgroundTint)
                    .padding(vertical = 4.dp, horizontal = 4.dp),
            ) {
                Text(
                    text = text,
                    color = textColor,
                    modifier = Modifier.padding(4.dp),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ToggleButtonPreview() {
    AIMoviesTheme {
        ToggleButton(
            modifier = Modifier, "Selected", listOf("Selected", "Unselected")
        ) {

        }
    }
}