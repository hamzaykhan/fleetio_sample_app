package com.hamza.fleetiosample.feature_vehicle.presentation.info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamza.fleetiosample.common.repository.FleetioRepository
import com.hamza.fleetiosample.common.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VehicleInfoViewModel @Inject constructor(
    private val repository: FleetioRepository
): ViewModel() {
    var state by mutableStateOf(VehicleInfoState())
        private set

    init {
        loadComments()
    }

    private fun loadComments() {
        viewModelScope.launch {
            repository.getCommentRepository().getComments().collect { resource ->
                when (resource) {
                    is Resource.Success -> state = state.copy(commentItems = resource.data, isLoading = false)
                    is Resource.Loading -> state = state.copy(isLoading = true)
                    is Resource.Error -> state = state.copy(isLoading = false, error = resource.exception.message)
                }
            }
        }
    }
}