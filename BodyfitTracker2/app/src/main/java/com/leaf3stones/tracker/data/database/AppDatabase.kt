package com.leaf3stones.tracker.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.leaf3stones.tracker.data.Article
import com.leaf3stones.tracker.data.BodyInfo

@Database(entities = [LocalUser::class, BodyInfo::class, Article::class], version = 5, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao() : LocalUserDao
    abstract fun bodyInfoDao() : BodyInfoDao
    abstract fun articleDao() : ArticleDao
}