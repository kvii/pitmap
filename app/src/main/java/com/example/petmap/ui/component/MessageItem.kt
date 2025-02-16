package com.example.petmap.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.petmap.data.repository.Message

@Composable
fun MessageItem(message: Message) {
    Column(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Text(message.sender, style = MaterialTheme.typography.labelMedium)
        Text(message.content, modifier = Modifier.padding(start = 16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun MessageItemPreview() {
    MessageItem(
        Message(
            sender = "kvii",
            receiver = "张三",
            content = "请帮我找找走丢的猫吧。",
        )
    )
}