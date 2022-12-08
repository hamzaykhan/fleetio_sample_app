package com.hamza.fleetiosample.feature_vehicle.presentation.info

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.hamza.fleetiosample.R
import com.hamza.fleetiosample.feature_vehicle.domain.model.CommentItem
import com.hamza.fleetiosample.feature_vehicle.domain.model.VehicleItem
import com.hamza.fleetiosample.feature_vehicle.presentation.info.comments.CommentItem
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun VehicleInfo(
    item: VehicleItem,
    viewModel: VehicleInfoViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()

    Box(
        Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Column(
            Modifier
                .verticalScroll(state = scrollState)
                .fillMaxWidth()
        ) {
            VehicleInfoImage(item = item)
            VehiclePrimaryContent(item = item)
            VehicleSpecInfo(item = item)
            VehicleLocationOnMap(item = item)

            if (viewModel.state.commentItems.isNotEmpty())
                VehicleComment(item = viewModel.state.commentItems.first())
        }
    }
}

@Composable
fun VehicleInfoImage(item: VehicleItem) {
    AsyncImage(
        placeholder = painterResource(id = R.drawable.ic_car),
        model = item.defaultUrl,
        contentDescription = item.name + " image",
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clip(RoundedCornerShape(5.dp)),
        contentScale = ContentScale.Crop,
        error = painterResource(id = R.drawable.ic_car)
    )
}

@Composable
fun VehiclePrimaryContent(item: VehicleItem) {
    Column(Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = item.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = item.make,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = 1
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = item.model,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Composable
fun VehicleSpecInfo(item: VehicleItem) {
    Text(
        text = "Specification: ",
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        color = MaterialTheme.colorScheme.onBackground,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        modifier = Modifier.padding(
            PaddingValues(horizontal = 16.dp)
        )
    )

    Spacer(modifier = Modifier.width(8.dp))
    
    val map = mutableMapOf<String, String>()
    map["Vin"] = item.vin
    map["Engine Brand"] = item.specs.engineBrand
    map["Cargo Volume"] = item.specs.cargoVolume.toString()
    map["Engine Stroke"] = item.specs.engineStrokes.toString()
    map["Fuel Tank Capacity"] = item.specs.fuelTankCapacity.toString()
    map["Transmission Gear"] = item.specs.transmissionGear.toString()
    map["Engine Description"] = item.specs.engineDescription

    VehicleSpecInfoItem(items = map)
}

@Composable
fun VehicleSpecInfoItem(items: Map<String, String>) {
    items.forEach {
        Row(Modifier.padding(PaddingValues(
            horizontal = 16.dp
        ))) {
            Text(
                text = it.key,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .weight(1f)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    .padding(4.dp)
            )

            Text(
                text = it.value,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .weight(1f)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    .padding(4.dp),
                textAlign = TextAlign.End
            )
        }
    }
}

@Composable
fun VehicleLocationOnMap(item: VehicleItem) {
    Text(
        text = "Vehicle Location: ",
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        color = MaterialTheme.colorScheme.onBackground,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        modifier = Modifier.padding(
            16.dp
        )
    )
    Box(
        Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(PaddingValues(top = 8.dp))
    ) {
        val location = LatLng(item.location.latitude, item.location.longitude)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(location, 24f)
        }
        GoogleMap(
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = location),
                title = item.location.getFormattedAddress()
            )
        }
    }
    Spacer(modifier = Modifier.width(16.dp))
}

@Composable
fun VehicleComment(item: CommentItem) {
    Text(
        text = "Comments: ",
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        color = MaterialTheme.colorScheme.onBackground,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        modifier = Modifier.padding(
            PaddingValues(
                horizontal = 16.dp
            )
        )
    )
    Spacer(modifier = Modifier.width(8.dp))

    CommentItem(item = item)

    Spacer(modifier = Modifier.width(8.dp))
    
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Button(onClick = {  }, modifier = Modifier) {
            Text(text = "View Comments")
        }
    }
}