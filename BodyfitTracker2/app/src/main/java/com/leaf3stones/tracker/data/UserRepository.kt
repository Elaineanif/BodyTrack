package com.leaf3stones.tracker.data

import androidx.room.Room
import com.leaf3stones.tracker.App
import com.leaf3stones.tracker.data.database.AppDatabase
import com.leaf3stones.tracker.data.database.ArticleDao
import com.leaf3stones.tracker.data.database.BodyInfoDao
import com.leaf3stones.tracker.data.database.LocalUser
import com.leaf3stones.tracker.data.database.LocalUserDao

class UserRepository {
    private val localUserDao: LocalUserDao
    private val bodyInfoDao: BodyInfoDao
    private val articleDao: ArticleDao

    init {
        val db = Room.databaseBuilder(
            App.getAppContext(), AppDatabase::class.java, "users"
        ).fallbackToDestructiveMigration().build()
        localUserDao = db.userDao()
        bodyInfoDao = db.bodyInfoDao()
        articleDao = db.articleDao()
    }

    suspend fun getUserById(id:Int) : User? {
        val localUser = localUserDao.getUserById(id)
        return if (localUser == null) {
            null
        } else {
            User(
                username = localUser.username,
                password = localUser.password,
                id = localUser.id,
                isAdmin = localUser.isAdmin
            )
        }
    }

    suspend fun addUser(user: User) {
        val localUser = LocalUser(
            username = user.username!!, isAdmin = user.isAdmin!!, password = user.password!!
        )
        localUserDao.addUser(localUser)
    }


    suspend fun getBodyInfoById(id:Int): BodyInfo? {
        return bodyInfoDao.getBodyInfo(id)
    }

    suspend fun getUserByUsername(username: String): User? {
        val localUser = localUserDao.getUserByUsername(username)
        return if (localUser == null) {
            null
        } else {
            User(
                username = localUser.username,
                password = localUser.password,
                id = localUser.id,
                isAdmin = localUser.isAdmin
            )
        }
    }

    suspend fun updateBodyInfo(info: BodyInfo) {
        bodyInfoDao.insert(info)
    }

    suspend fun addArticle(article: Article) {
        articleDao.addArticle(article)
    }

    suspend fun deleteArticle(id:Int) {
        articleDao.deleteArticleById(id)
    }

    fun getAllArticle() = articleDao.getAllArticle()

    suspend fun getArticleById(id:Int) = articleDao.getArticleById(id)
}