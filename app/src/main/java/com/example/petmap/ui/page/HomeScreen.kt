package com.example.petmap.ui.page

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.amap.api.maps.model.CameraPosition
import com.example.petmap.R
import com.example.petmap.data.repository.MockPetMapApi
import com.example.petmap.data.repository.UserFullInfo
import com.example.petmap.data.viewmodels.MyViewModel
import com.melody.map.gd_compose.GDMap
import com.melody.map.gd_compose.overlay.MarkerInfoWindowContent
import com.melody.map.gd_compose.overlay.MarkerState
import com.melody.map.gd_compose.poperties.MapUiSettings
import com.melody.map.gd_compose.position.rememberCameraPositionState

@Composable
fun HomeScreen(
    viewModel: MyViewModel = viewModel(factory = MyViewModel.Factory),
    navigateToMy: () -> Unit = {},
    navigateToMessage: () -> Unit = {},
) {
    val fullInfo: UserFullInfo? by viewModel.fullInfo.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.loop()
    }
    HomeScreenContent(
        fullInfo,
        navigateToMy,
        navigateToMessage,
    )
}

@Composable
fun HomeScreenContent(
    fullInfo: UserFullInfo? = null,
    navigateToMy: () -> Unit = {},
    navigateToMessage: () -> Unit = {},
) {
    val isInit = remember { mutableStateOf(true) }
    val cameraPositionState = rememberCameraPositionState {}
    fullInfo?.home?.let {
        if (isInit.value) {
            cameraPositionState.position = CameraPosition.fromLatLngZoom(it.latLang, 16F)
            isInit.value = false
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GDMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(
                isZoomGesturesEnabled = true,
                isScrollGesturesEnabled = true,
            ),
        ) {
            fullInfo?.home?.let { home ->
                MarkerInfoWindowContent(
                    title = "家",
                    state = MarkerState(home.latLang),
                    onClick = { it.showInfoWindow(); true },
                )
            }
            fullInfo?.pets?.forEach { pet ->
                MarkerInfoWindowContent(
                    title = pet.petName,
                    state = MarkerState(pet.latLang),
                    onClick = { it.showInfoWindow(); true },
                )
            }
        }
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
    HomeScreenContent(
        fullInfo = MockPetMapApi.userFullInfo,
    )
}