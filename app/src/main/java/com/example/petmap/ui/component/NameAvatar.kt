package com.example.petmap.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun NameAvatar(
    name: String,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge,
) {
    Box(modifier = modifier.size(size), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF4CAF50),
                        Color(0xFF81C784),
                    )
                )
            )
        }
        Text(name.take(1), style = textStyle, color = Color.White)
    }
}

@Preview
@Composable
fun NameAvatarPreview() {
    NameAvatar("张三")
}