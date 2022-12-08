package com.hamza.fleetiosample.feature_vehicle.presentation.listing

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamza.fleetiosample.common.pagination.DefaultPaginator
import com.hamza.fleetiosample.common.wrapper.Resource
import com.hamza.fleetiosample.feature_vehicle.domain.use_case.VehicleUseCases
import com.hamza.fleetiosample.feature_vehicle.domain.util.VehicleFilter
import com.hamza.fleetiosample.feature_vehicle.presentation.listing.filter.SortOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VehicleListingViewModel @Inject constructor(
    private val vehicleUseCases: VehicleUseCases
) : ViewModel() {

    var state by mutableStateOf(VehicleListingState())
        private set

    private var searchJob: Job? = null

    private val paginator = DefaultPaginator(
        initialKey = state.page,
        onLoadUpdate = { state = state.copy(isRefreshing = it) },
        onRequest = { nextPage ->
            vehicleUseCases.getVehicles().invoke(
                page = nextPage,
                filter = state.filters,
                fetchLocal = state.fetchLocal
            )
        },
        getNextKey = { state.page + 1 },
        onError = { state = state.copy(errorMessage = it.message, endReached = true) },
        onSuccess = { item, nextKey ->
            state = state.copy(
                vehicles = state.vehicles + item,
                page = nextKey,
                endReached = item.isEmpty()
            )
        }
    )

    init {
        getVehicles()
    }

    fun onEvent(event: VehicleListingEvent) {
        when(event) {
            is VehicleListingEvent.Refresh -> {
                resetItems()
                state.updateFilters(VehicleFilter())
                state = state.copy()
                getVehicles()
            }
            is VehicleListingEvent.OnSearchQueryChange -> {
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    resetItems()
                    state.updateFilters(filters = VehicleFilter(name = event.query))
                    getVehicles()
                }
            }
        }
    }

    fun getVehicles() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    private fun resetItems() {
        paginator.resetItems()
    }

    fun updateFilterData(name: String,
                         color: String,
                         year: Float,
                         secondaryMeter: Boolean,
                         sortList: List<SortOrder>
    ) {
        state.updateFilters(
            VehicleFilter(
                name = name,
                color = color,
                year = year,
                secondaryMeter = secondaryMeter,
                sort = sortList
            )
        )
        resetItems()
        updateFetchLocal(false)
        getVehicles()
    }

    fun updateFetchLocal(value: Boolean) {
        state = state.copy(fetchLocal = value)
    }
}