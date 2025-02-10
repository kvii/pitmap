package com.example.petmap.ui.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.petmap.data.repository.Message
import com.example.petmap.data.viewmodels.MyViewModel

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
    Column(modifier = Modifier.fillMaxSize()) {
        messages.forEach {
            Text(text = it.content)
        }
    }
}

@Preview
@Composable
fun MessageScreenPreview() {
    MessageScreenContent(
        listOf(
            Message(
                sendTo = "张三",
                content = "请帮帮kvii找找走丢的狗吧。",
            ),
            Message(
                sendTo = "kvii",
                content = "您的宠物%s已走丢。",
            ),
        )
    )
}