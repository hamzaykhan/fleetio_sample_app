package com.hamza.fleetiosample.feature_vehicle.presentation.listing

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.hamza.fleetiosample.R
import com.hamza.fleetiosample.feature_vehicle.presentation.listing.filter.FilterBottomSheet
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Destination(start = true)
@Composable
fun VehicleListing(
    navigator: DestinationsNavigator,
    viewModel: VehicleListingViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val state = viewModel.state
    val searchQueryText = remember {
        mutableStateOf(TextFieldValue(state.filters.name))
    }
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = state.isRefreshing
    )
    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            FilterBottomSheet(
                viewModel = viewModel,
                bottomSheetState = sheetState
            )
        },
        sheetBackgroundColor = MaterialTheme.colorScheme.background,
        sheetPeekHeight = 0.dp,
        backgroundColor = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = searchQueryText.value,
                            onValueChange = {
                                searchQueryText.value = it
                                viewModel.onEvent(
                                    VehicleListingEvent.OnSearchQueryChange(it.text)
                                )
                            },
                            modifier = Modifier.weight(1f),
                            placeholder = {
                                Text(text = "Search...")
                            },
                            maxLines = 1,
                            singleLine = true,
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.ic_filter),
                            tint = MaterialTheme.colorScheme.onBackground,
                            contentDescription = "Filter Vehicles Icon",
                            modifier = Modifier.clickable {
                                scope.launch {
                                    if(sheetState.isCollapsed) {
                                        sheetState.expand()
                                    } else {
                                        sheetState.collapse()
                                    }
                                }
                            }
                        )
                    }
                }
            }

            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                    viewModel.onEvent(VehicleListingEvent.Refresh)
                }
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.vehicles.size) { i ->
                        val vehicle = state.vehicles[i]
                        if (i >= state.vehicles.size - 1 && !state.endReached && !state.isRefreshing) {
                            viewModel.getVehicles()
                        }
                        VehicleItem(
                            vehicle = vehicle,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    // navigation to detail screen
                                }
                        )
                        if(i < state.vehicles.size) {
                            Divider(modifier = Modifier.padding(
                                horizontal = 16.dp
                            ))
                        }
                    }
                }
            }
        }
    }
}