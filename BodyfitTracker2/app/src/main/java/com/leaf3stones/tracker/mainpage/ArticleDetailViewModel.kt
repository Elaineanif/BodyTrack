package com.leaf3stones.tracker.mainpage

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leaf3stones.tracker.data.Article
import com.leaf3stones.tracker.data.User
import com.leaf3stones.tracker.data.UserRepository
import com.leaf3stones.tracker.data.getUserId
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ArticleDetailViewModel : ViewModel() {
    private val repo = UserRepository()

    private val _article = mutableStateOf(Article(0, authorId = -1, title = "", content = ""))
    val article get() = _article.value

    private val _user: MutableState<User?> = mutableStateOf(null)
    val author get() = _user.value

    private val _saveMessage: MutableState<String?> = mutableStateOf(null)
    val saveMessage get() = _saveMessage.value

    private val _editMessage: MutableState<String?> = mutableStateOf(null)
    val editMessage get() = _editMessage.value

    private val _editable = mutableStateOf(false)
    val editable get() = _editable.value

     fun checkContextEditable() {
        var ok =  false
        runBlocking {
            val job = launch {
                val currUser = repo.getUserById(getUserId())
                if (currUser!!.isAdmin == true || author!!.id == currUser.id) {
                    ok = true
                } else {
                    _editMessage.value = "This is not your post or you're not admin!"
                }
            }
            job.join()
        }
        _editable.value = ok
    }

    private fun checkValid(): Boolean {
        return  article.title.isNotEmpty() && article.content.isNotEmpty()
    }

    fun setTitle(title: String) {
        _article.value = _article.value.copy(title = title)
    }

    fun setContent(context: String) {
        _article.value = _article.value.copy(content = context)
    }

    fun loadArticle(articleId: Int?) {
        if (articleId == null) {
            //newly created article
            runBlocking {
                val job = launch {
                        val fetchedUser = repo.getUserById(getUserId())
                        _user.value = fetchedUser
                        _article.value = _article.value.copy(authorId = getUserId())
                }
                job.join()
            }
        } else {
            runBlocking {
                val job = launch {
                    val fetchedArticle = repo.getArticleById(articleId)
                    _article.value = fetchedArticle

                    val fetchedUser = repo.getUserById(article.authorId)
                    _user.value = fetchedUser
                }
                job.join()
            }
        }
        checkContextEditable()
    }

    fun trySave()  {
        if (!checkValid()) {
            _saveMessage.value = "don't leave anything blank!!"
        } else {
            runBlocking {
                val job = launch {
                    viewModelScope.launch {
                        repo.addArticle(article)
                    }
                }
                job.join()
            }
            _saveMessage.value = "saved!"
        }
    }

    fun onSaveMessageDisplay() {
        _saveMessage.value = null
    }

    fun onEditMessageDisplay() {
        _editMessage.value = null
    }

    fun removeCurrent() {
        runBlocking {
            val job = launch {
                repo.deleteArticle(article.articleId)
            }
            job.join()
        }
    }
}