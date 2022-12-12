package com.hamza.fleetiosample.feature_vehicle.presentation.info.comments

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.BottomSheetState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import com.hamza.fleetiosample.feature_vehicle.presentation.destinations.VehicleInfoDestination
import com.hamza.fleetiosample.feature_vehicle.presentation.info.VehicleInfoViewModel
import com.hamza.fleetiosample.feature_vehicle.presentation.listing.VehicleItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetComments(
    viewModel: VehicleInfoViewModel,
    bottomSheetState: BottomSheetState
) {
    val scope = rememberCoroutineScope()
    val state = viewModel.state

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.85f),
        horizontalAlignment = Alignment.Start
    ) {
        Icon(
            imageVector = Icons.Default.Cancel,
            contentDescription = "Close comment screen",
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(16.dp)
                .clickable {
                    scope.launch {
                        bottomSheetState.collapse()
                    }
                }
                .width(32.dp)
                .height(32.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(state.commentItems.size) { i ->
                val comment = state.commentItems[i]
                if (i >= state.commentItems.size - 1 && !state.isEndReached && !state.isLoading) {
                    viewModel.loadComments()
                }
                CommentItem(item = comment)
            }

            item {
                if (state.isLoading) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}