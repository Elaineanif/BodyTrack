package com.leaf3stones.tracker.mainpage

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leaf3stones.tracker.data.Article
import com.leaf3stones.tracker.data.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {
    private val repo = UserRepository()

    private val _articles : MutableState<List<Article>> = mutableStateOf(listOf())


    val article
        get() = {
            ->
            viewModelScope.launch {
            repo.getAllArticle().collect {
                _articles.value  = it
            }
        }
        _articles.value
    }

    init {
        viewModelScope.launch {
            repo.getAllArticle().collect {
                _articles.value  = it
            }
        }
    }
}