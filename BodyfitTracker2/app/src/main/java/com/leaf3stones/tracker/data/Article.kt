package com.leaf3stones.tracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article")
data class Article(
    @PrimaryKey(autoGenerate = true)
    val articleId:Int = 0,
    val authorId: Int,
    val title:String,
    val content:String
)
