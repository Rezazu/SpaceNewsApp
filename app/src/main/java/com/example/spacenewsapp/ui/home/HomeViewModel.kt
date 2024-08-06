package com.example.spacenewsapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spacenewsapp.data.remote.ResultsItem
import com.example.spacenewsapp.data.repository.ArticlesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val articlesRepository: ArticlesRepository
):ViewModel() {

    private var _articles = MutableStateFlow<List<ResultsItem>>(emptyList())
    val articles: StateFlow<List<ResultsItem>> get() = _articles

    fun getArticles(){
        viewModelScope.launch {
            _articles.value = articlesRepository.getArticles()
        }
    }

    init {
        viewModelScope.launch {
            getArticles()
        }
    }
}