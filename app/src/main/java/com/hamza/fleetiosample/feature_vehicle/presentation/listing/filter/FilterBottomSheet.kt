package com.hamza.fleetiosample.feature_vehicle.presentation.listing.filter

import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomSheetState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hamza.fleetiosample.R
import com.hamza.fleetiosample.feature_vehicle.domain.util.VehicleFilter
import com.hamza.fleetiosample.feature_vehicle.presentation.listing.VehicleListingViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilterBottomSheet(
    viewModel: VehicleListingViewModel,
    bottomSheetState: BottomSheetState
) {
    val state = viewModel.state
    val nameTextState = remember { mutableStateOf(TextFieldValue(state.filters.name)) }
    val colorTextState = remember { mutableStateOf(TextFieldValue(state.filters.color)) }
    val sliderYear = remember { mutableStateOf(state.filters.year) }
    val secondaryMeterState = remember { mutableStateOf(state.filters.secondaryMeter) }
    val selectedSortOrder: MutableState<List<SortOrder>> = remember { mutableStateOf(state.filters.sort) }

    val scope = rememberCoroutineScope()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            OutlinedTextField(
                value = nameTextState.value,
                onValueChange = { nameTextState.value = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = stringResource(id = R.string.search_by_name_str))
                },
                label = {
                    Text(text = stringResource(id = R.string.vehicle_name_str))
                },
                maxLines = 1,
                singleLine = true
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                value = colorTextState.value,
                                onValueChange = { colorTextState.value = it },
                                placeholder = {
                                    Text(text = stringResource(id = R.string.search_by_color_str))
                                },
                                label = {
                                    Text(text = stringResource(id = R.string.vehicle_color_str))
                                },
                                maxLines = 1,
                                singleLine = true,
                                modifier = Modifier.weight(1f)
                            )

                            Spacer(modifier = Modifier.width(4.dp))

                            Column(
                                modifier = Modifier.padding(
                                    paddingValues = PaddingValues(vertical = 8.dp)
                                )
                            ) {
                                Text(
                                    text = stringResource(id = R.string.secondary_meter_str),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    maxLines = 1
                                )

                                Switch(
                                    checked = secondaryMeterState.value,
                                    onCheckedChange = { secondaryMeterState.value = it }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.width(20.dp))

            Text(
                text = stringResource(id = R.string.year_str, sliderYear.value.toInt()),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth()
            )

            Slider(
                value = sliderYear.value,
                onValueChange = { sliderYear.value = it },
                valueRange = 1990f..2022f,
                steps = 32
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = stringResource(id = R.string.sort_by_str),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth()
            )

            ChipGroup(
                sortOrders = getAllSortOrder(),
                selectedSortOrders = selectedSortOrder.value,
                onSelectedChanged = {
                    val oldList: MutableList<SortOrder> = selectedSortOrder.value.toMutableList()
                    val sortFromString = getSortOrder(it)

                    if(oldList.contains(sortFromString)){
                        oldList.remove(sortFromString)
                    }else{
                        sortFromString?.let { selected ->
                            oldList.add(selected)
                        }
                    }

                    selectedSortOrder.value = oldList
                }
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = {
                    viewModel.updateFilterData(
                        name = nameTextState.value.text,
                        color = colorTextState.value.text,
                        year = sliderYear.value,
                        secondaryMeter = secondaryMeterState.value,
                        sortList = selectedSortOrder.value
                    )

                    scope.launch {
                        bottomSheetState.collapse()
                    }

                }) {
                    Text(
                        text = stringResource(id = R.string.apply_str),
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                TextButton(onClick = {
                    val vehicleFilter = VehicleFilter()
                    nameTextState.value = TextFieldValue(vehicleFilter.name)
                    colorTextState.value = TextFieldValue(vehicleFilter.color)
                    sliderYear.value = vehicleFilter.year
                    secondaryMeterState.value = vehicleFilter.secondaryMeter
                    selectedSortOrder.value = vehicleFilter.sort
                }) {
                    Text(
                        text = stringResource(id = R.string.clear_str),
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}