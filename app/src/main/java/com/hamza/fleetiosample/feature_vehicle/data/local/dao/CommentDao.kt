package com.hamza.fleetiosample.feature_vehicle.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hamza.fleetiosample.feature_vehicle.data.local.entity.CommentEntity

@Dao
interface CommentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(
        commentEntities: List<CommentEntity>
    )

    @Query(
        """
            SELECT * 
            FROM commententity
        """
    )
    suspend fun getComments(): List<CommentEntity>

    @Query(
        """
            SELECT * 
            FROM commententity
            WHERE page == :page
        """
    )
    suspend fun getComments(page: Int): List<CommentEntity>

}