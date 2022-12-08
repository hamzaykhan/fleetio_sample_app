package com.hamza.fleetiosample.common.pagination

interface Paginator<Key, Item> {
    suspend fun loadNextItems()
    fun resetItems()
}