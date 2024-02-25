package com.codelabs.agrimate.ui.components.maps

import android.view.MotionEvent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.unit.dp
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.GoogleMapComposable
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AGMaps(
    modifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState,
    onMapTouched: (() -> Unit)? = null,
    onMapLoaded: (() -> Unit)? = null,
    onMyLocationButtonClick: (() -> Boolean)? = null,
    content: @Composable @GoogleMapComposable (() -> Unit)?
) {
    GoogleMap(
        modifier = modifier
            .fillMaxWidth()
            .height(254.dp)
            .pointerInteropFilter(onTouchEvent = {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        onMapTouched?.invoke()
                        false
                    }

                    else -> {
                        true
                    }
                }
            }),
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(
            myLocationButtonEnabled = true,
            zoomControlsEnabled = false,
        ),
        properties = MapProperties(
            isMyLocationEnabled = false,
            isBuildingEnabled = false,
            mapType = MapType.SATELLITE,
            isTrafficEnabled = false,
        ),
        onMapLoaded = onMapLoaded,
        onMyLocationButtonClick = onMyLocationButtonClick
    ) {
        content?.invoke()
    }
}