package com.sagrd.kevin_p1_ap2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.sagrd.kevin_p1_ap2.data.local.entity.Division
import kotlinx.coroutines.flow.Flow

@Dao
interface DivisionDao {
    @Upsert
    suspend fun save(division : Division)
    @Delete
    suspend fun delete(division : Division)
    @Query("SELECT * FROM Divisiones")
    fun getAll(): Flow<List<Division>>
}