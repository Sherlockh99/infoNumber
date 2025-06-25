package com.sh.work.infonumber.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sh.work.infonumber.entity.HistoryEntity

@Dao
interface HistoryDao {

    @Query("SELECT * FROM history")
    suspend fun getAll(): List<HistoryEntity>

    @Query("SELECT * FROM history WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): HistoryEntity?

    @Insert
    suspend fun insert(product: HistoryEntity)
}
