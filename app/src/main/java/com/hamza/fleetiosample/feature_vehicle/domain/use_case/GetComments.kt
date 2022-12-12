package com.hamza.fleetiosample.feature_vehicle.domain.use_case

import com.hamza.fleetiosample.common.repository.FleetioRepository
import com.hamza.fleetiosample.common.wrapper.Resource
import com.hamza.fleetiosample.feature_vehicle.domain.model.CommentItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetComments @Inject constructor(
    private val repository: FleetioRepository
) {

    suspend operator fun invoke(
        page: Int,
        sort: String,
        fetchServer: Boolean
    ): Flow<Resource<List<CommentItem>>> {
        return repository.getCommentRepository()
            .getComments(
                page = page,
                sort = sort,
                fetchServer = fetchServer
            )
    }

}