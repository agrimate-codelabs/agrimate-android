package com.codelabs.agrimate.ui.components.maps

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.model.Dash
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMapComposable
import com.google.maps.android.compose.Polygon

@Composable @GoogleMapComposable
fun AGMapsPolygonDefault(listLatLng: List<LatLng>) {
    if (listLatLng.isNotEmpty()) {
        Polygon(
            points = listLatLng,
            fillColor = Color(71 / 255f, 188 / 255f, 110 / 255f, 0.15f),
            strokeColor = Color(0xFF47BC6E),
            strokePattern = listOf(
                Dash(10f),
            ),
            strokeJointType = JointType.DEFAULT,
            strokeWidth = 5f
        )
    }
}