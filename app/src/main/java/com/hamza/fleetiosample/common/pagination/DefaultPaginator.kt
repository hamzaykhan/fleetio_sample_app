package com.hamza.fleetiosample.common.pagination

import com.hamza.fleetiosample.common.wrapper.Resource
import kotlinx.coroutines.flow.Flow

class DefaultPaginator<Key, Item>(
    private val initialKey: Key,
    private inline val onLoadUpdate: (Boolean) -> Unit,
    private inline val onRequest: suspend (nextKey: Key) -> Flow<Resource<List<Item>>>,
    private inline val getNextKey: (Key) -> Key,
    private inline val onError: suspend (Throwable) -> Unit,
    private inline val onSuccess: suspend (item: List<Item>, nextKey: Key) -> Unit
): Paginator<Key, Item>{

    private var currentKey = initialKey
    private var isMakingRequest = false

    override suspend fun loadNextItems() {
        if (isMakingRequest)
            return

        isMakingRequest = true
        onRequest(currentKey).collect { resource ->
            when (resource) {
                is Resource.Success -> {
                    isMakingRequest = false
                    currentKey = getNextKey(currentKey)
                    onSuccess(resource.data, currentKey)
                    onLoadUpdate(false)
                }
                is Resource.Error -> {
                    isMakingRequest = false
                    onError(resource.exception)
                    onLoadUpdate(false)
                }
                is Resource.Loading -> onLoadUpdate(true)
            }
        }

    }

    override fun resetItems() {
        currentKey = initialKey
    }

}