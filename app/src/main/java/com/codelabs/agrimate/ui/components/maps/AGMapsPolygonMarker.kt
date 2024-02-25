package com.codelabs.agrimate.ui.components.maps

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.Dash
import com.google.android.gms.maps.model.Gap
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMapComposable
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polygon

@Composable @GoogleMapComposable
fun AGMapsPolygonMarker(listLatLng: List<LatLng>) {
    listLatLng.forEach { position ->
        MarkerComposable(state = MarkerState(position), anchor = Offset(0.5f, 0.5f)) {
            Canvas(
                modifier = Modifier
                    .size(16.dp),
                onDraw = {
                    drawCircle(color = Color(0xFF093731), radius = 16f)
                    drawCircle(color = Color(0xFFFF5E5E), radius = 10f)
                })
        }
    }
    if (listLatLng.isNotEmpty()) {
        Polygon(
            points = listLatLng,
            fillColor = Color(71 / 255f, 188 / 255f, 110 / 255f, 0.15f),
            strokeColor = Color(0xFF47BC6E),
            strokePattern = listOf(
                Dash(10f),
                Gap(10f)
            ),
            strokeJointType = JointType.DEFAULT,
            strokeWidth = 5f
        )
    }
}