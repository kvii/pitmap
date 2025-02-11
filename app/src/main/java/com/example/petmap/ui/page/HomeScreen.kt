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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amap.api.maps.model.CameraPosition
import com.amap.api.maps.model.LatLng
import com.example.petmap.R
import com.melody.map.gd_compose.GDMap
import com.melody.map.gd_compose.overlay.MarkerInfoWindowContent
import com.melody.map.gd_compose.overlay.MarkerState
import com.melody.map.gd_compose.poperties.MapUiSettings
import com.melody.map.gd_compose.position.rememberCameraPositionState
import com.melody.map.gd_compose.utils.MapUtils

@Composable
fun HomeScreen(
    navigateToMy: () -> Unit = {},
    navigateToMessage: () -> Unit = {},
) {
    MapUtils.setMapPrivacy(LocalContext.current, true)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(35.9518869, 120.1850354), 16F)
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
            MarkerInfoWindowContent(
                title = "家",
                state = MarkerState(LatLng(35.9518869, 120.1850354)),
                onClick = { it.showInfoWindow(); true },
            )
            MarkerInfoWindowContent(
                title = "狗",
                state = MarkerState(LatLng(35.9518869 + 0.004, 120.1850354 + 0.004)),
                onClick = { it.showInfoWindow(); true },
            )
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
    HomeScreen()
}