package com.example.petmap.ui.page

import android.widget.TextView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.petmap.R

@Composable
fun HomeScreen(
    navigateToMy: () -> Unit = {},
    navigateToMessage: () -> Unit = {},
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // TODO: 地图
        AndroidView(
            factory = { context ->
                TextView(context).apply {
                    text = context.getString(R.string.app_name)
                }
            }
        )
        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            IconButton(onClick = navigateToMy) {
                Icon(Icons.Filled.Home, stringResource(R.string.my))
            }
            Spacer(modifier = Modifier.height(8.dp))
            IconButton(onClick = navigateToMessage) {
                Icon(Icons.Filled.Email, stringResource(R.string.messages))
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}