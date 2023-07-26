package com.leaf3stones.tracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "bodyInfo")
data class BodyInfo(
    @PrimaryKey
    val id:Int,
    val name:String,
    val age:Int,
    val weight:Float,
    val height:Float,
    val emailAddress:String,
    val birthday:Date
)