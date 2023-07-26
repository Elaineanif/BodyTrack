package com.leaf3stones.tracker.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.leaf3stones.tracker.data.BodyInfo

@Dao
interface BodyInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bodyInfo: BodyInfo)

    @Query("SELECT * FROM BODYINFO WHERE ID = :id")
    suspend fun getBodyInfo(id:Int): BodyInfo?
}