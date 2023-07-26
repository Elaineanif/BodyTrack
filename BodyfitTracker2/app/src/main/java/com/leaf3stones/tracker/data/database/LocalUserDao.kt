package com.leaf3stones.tracker.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao()
interface LocalUserDao {
    @Query("SELECT * FROM USERS")
    suspend fun listAllUser() : List<LocalUser>

    @Query("SELECT * FROM USERS WHERE id = :id")
    suspend fun getUserById(id:Int) : LocalUser?

    @Query("SELECT * FROM USERS WHERE username = :username")
    suspend fun getUserByUsername(username:String) : LocalUser?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user:LocalUser)
}