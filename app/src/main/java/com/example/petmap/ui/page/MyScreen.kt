package com.example.petmap.ui.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.petmap.R
import com.example.petmap.data.repository.Home
import com.example.petmap.data.repository.Pet
import com.example.petmap.data.repository.User
import com.example.petmap.data.repository.UserFullInfo
import com.example.petmap.data.viewmodels.MyViewModel
import com.example.petmap.ui.component.NameAvatar
import com.example.petmap.ui.component.PetItem

@Composable
fun MyScreen(viewModel: MyViewModel = viewModel(factory = MyViewModel.Factory)) {
    val userFullInfoState = produceState<UserFullInfo?>(initialValue = null) {
        value = viewModel.userFullInfo()
    }

    userFullInfoState.value?.let { info ->
        MyScreenContent(info)
    } ?: run {
        Text("loading...")
    }
}

@Composable
fun MyScreenContent(info: UserFullInfo) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 48.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            NameAvatar(info.user.userName)
            Spacer(modifier = Modifier.height(8.dp))
            Text(info.user.userName)
        }
        Text(
            stringResource(R.string.pets),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Column {
            info.pets.forEach { PetItem(it) }
        }
    }
}

@Preview
@Composable
fun MyScreenPreview() {
    // 为减少 api 数量，接口多了点冗余数据
    val info = UserFullInfo(
        user = User(
            userName = "kvii",
            password = "pass"
        ),
        home = Home(
            owner = "kvii",
            longitude = 1.0,
            latitude = 1.0,
        ),
        pets = listOf(
            Pet(
                petName = "狗",
                owner = "kvii",
                longitude = 1.0,
                latitude = 1.0,
            )
        )
    )
    MyScreenContent(info)
}