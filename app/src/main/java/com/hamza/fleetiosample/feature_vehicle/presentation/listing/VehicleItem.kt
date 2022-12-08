package com.hamza.fleetiosample.feature_vehicle.presentation.listing

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.hamza.fleetiosample.feature_vehicle.domain.model.VehicleItem
import com.hamza.fleetiosample.R

@Composable
fun VehicleItem(
    vehicle: VehicleItem,
    modifier: Modifier
) {
    Row(
        modifier = modifier.padding(8.dp),
    ) {
        VehicleImage(item = vehicle)
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
                .align(Alignment.CenterVertically)
        ) {
            VehicleInfo(item = vehicle)
        }
    }
}

@Composable
fun VehicleImage(item: VehicleItem) {
    AsyncImage(
        placeholder = painterResource(id = R.drawable.ic_car),
        model = item.defaultUrl,
        contentDescription = item.name + " image",
        modifier = Modifier
            .size(70.dp)
            .clip(RoundedCornerShape(5.dp)),
        contentScale = ContentScale.FillBounds,
        error = painterResource(id = R.drawable.ic_car)
    )
}

@Composable
fun VehicleInfo(item: VehicleItem) {
    Text(
        text = item.name,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        color = MaterialTheme.colorScheme.onBackground,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
    )
    Spacer(modifier = Modifier.width(4.dp))
    Text(
        text = item.model,
        fontWeight = FontWeight.Light,
        color = MaterialTheme.colorScheme.onBackground
    )

    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = "(${item.meterName})",
        fontStyle = FontStyle.Italic,
        color = MaterialTheme.colorScheme.onBackground
    )
}