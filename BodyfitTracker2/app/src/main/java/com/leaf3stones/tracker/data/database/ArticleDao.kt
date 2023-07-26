package com.leaf3stones.tracker.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.leaf3stones.tracker.data.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Query("SELECT * FROM article")
    fun getAllArticle(): Flow<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addArticle(article: Article)

    @Query("SELECT * FROM ARTICLE WHERE articleId = :id")
    suspend fun getArticleById(id: Int) : Article

    @Query("DELETE FROM ARTICLE WHERE articleId = :id")
    suspend fun deleteArticleById(id: Int)

}