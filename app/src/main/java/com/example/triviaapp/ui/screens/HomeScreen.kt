package com.example.triviaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.triviaapp.ui.theme.TriviaAppTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToQuestion: () -> Unit
) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical=100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Trivia",
                fontSize = 64.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Spacer(
                modifier = Modifier.weight(0.5f)
            )
            Button(
                onClick = onNavigateToQuestion
            ) {
                Text(
                    "Let's Play",
                    fontSize = 16.sp
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(onNavigateToQuestion = {})
}