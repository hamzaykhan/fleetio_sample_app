package com.hamza.fleetiosample.feature_vehicle.presentation.info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamza.fleetiosample.common.pagination.DefaultPaginator
import com.hamza.fleetiosample.feature_vehicle.domain.use_case.GetComments
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VehicleInfoViewModel @Inject constructor(
    private val commentUseCase: GetComments
): ViewModel() {
    var state by mutableStateOf(VehicleInfoState())
        private set

    private val paginator = DefaultPaginator(
        initialKey = state.page,
        onLoadUpdate = { state = state.copy(isLoading = it) },
        onRequest = { nextPage ->
            commentUseCase.invoke(
                page = nextPage,
                sort = state.sortFormat,
                fetchServer = state.fetchServer
            )
        },
        getNextKey = { state.page + 1 },
        onError = { state = state.copy(error = it.message, isEndReached = true) },
        onSuccess = { item, nextKey ->
            state = state.copy(
                commentItems = if (state.page > 1) state.commentItems + item else item,
                page = nextKey
            )
        },
        onEmpty = {state = state.copy(isEndReached = true)}
    )

    init {
        loadComments()
    }

    fun loadComments() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }
}