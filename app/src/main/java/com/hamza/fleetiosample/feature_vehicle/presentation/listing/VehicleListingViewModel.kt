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
import kotlinx.coroutines.withTimeout
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
                fetchRemote = state.fetchRemote
            )
        },
        getNextKey = { state.page + 1 },
        onError = { state = state.copy(errorMessage = it.message, endReached = true) },
        onSuccess = { item, nextKey ->
            state = state.copy(
                fetchRemote = false,
                vehicles = if (state.page > 1) state.vehicles + item else item,
                page = nextKey
            )
        },
        onEmpty = {state = state.copy(endReached = true)}
    )

    init {
        getVehicles()
    }

    fun onEvent(event: VehicleListingEvent) {
        when(event) {
            is VehicleListingEvent.Refresh -> {
                state = state.copy(fetchRemote = true, page = 1, filters = VehicleFilter())
                resetItems()
                getVehicles()
            }
            is VehicleListingEvent.OnSearchQueryChange -> {
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    state = state.copy(page = 1)
                    resetItems()
                    state = state.copy(
                        filters = state.filters.copy(name = event.query)
                    )
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
        state = state.copy(page = 1,
            filters = VehicleFilter(
                name = name,
                color = color,
                year = year,
                secondaryMeter = secondaryMeter,
                sort = sortList
            )
        )
        resetItems()
        getVehicles()
    }
}