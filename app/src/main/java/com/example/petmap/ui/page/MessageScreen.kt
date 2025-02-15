package com.example.petmap.ui.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.petmap.data.repository.Message
import com.example.petmap.data.repository.MockPetMapApi
import com.example.petmap.data.viewmodels.MyViewModel
import com.example.petmap.ui.component.MessageItem

@Composable
fun MessageScreen(myViewModel: MyViewModel = viewModel(factory = MyViewModel.Factory)) {
    val messageState = produceState<List<Message>?>(initialValue = null) {
        value = myViewModel.userMessages()
    }

    messageState.value?.let {
        MessageScreenContent(messages = it)
    } ?: run {
        Text("loading...")
    }
}

@Composable
fun MessageScreenContent(
    messages: List<Message>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("消息", style = MaterialTheme.typography.headlineMedium)
        messages.forEach {
            MessageItem(it)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MessageScreenPreview() {
    MessageScreenContent(MockPetMapApi.messages)
}