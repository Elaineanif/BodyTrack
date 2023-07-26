package com.leaf3stones.tracker.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class LocalUser(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val username:String,
    val password:String,
    val isAdmin:Boolean,
)
