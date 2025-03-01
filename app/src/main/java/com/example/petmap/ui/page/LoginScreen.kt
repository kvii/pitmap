package com.example.petmap.ui.page

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.petmap.R
import com.example.petmap.data.viewmodels.UserViewModel
import com.melody.map.gd_compose.utils.MapUtils
import kotlinx.coroutines.launch

const val TAG = "LoginScreen"

@Composable
fun LoginScreen(
    userViewModel: UserViewModel = viewModel(factory = UserViewModel.Factory),
    snackBarHostState: SnackbarHostState,
    navigateToHome: () -> Unit = {},
) {
    // demo 工程 直接同意隐私权限
    val ctx = LocalContext.current
    LaunchedEffect(Unit) { MapUtils.setMapPrivacy(ctx, true) }

    LoginScreenContent(
        initialUsername = userViewModel.loadUserName() ?: "",
        snackBarHostState = snackBarHostState,
        onLogin = userViewModel::login,
        navigateToHome = navigateToHome,
    )
}

@Composable
fun LoginScreenContent(
    initialUsername: String,
    snackBarHostState: SnackbarHostState,
    onLogin: suspend (userName: String, password: String) -> Unit,
    navigateToHome: () -> Unit = {},
) {
    val composableScope = rememberCoroutineScope()
    val userName = remember { mutableStateOf(initialUsername) }
    val password = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            stringResource(R.string.login_title),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
        Spacer(modifier = Modifier.height(32.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = userName.value,
            onValueChange = { userName.value = it },
            label = { Text(stringResource(R.string.user_name)) },
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text(stringResource(R.string.password)) },
            visualTransformation = PasswordVisualTransformation(),
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                composableScope.launch {
                    try {
                        onLogin(userName.value, password.value)
                        navigateToHome()
                    } catch (e: Exception) {
                        Log.e(TAG, "登录失败", e)
                        snackBarHostState.showSnackbar(e.message ?: "Unknown error")
                    }
                }
            }
        ) {
            Text(stringResource(R.string.login))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreenContent(
        "test",
        SnackbarHostState(),
        onLogin = { _, _ -> },
    )
}