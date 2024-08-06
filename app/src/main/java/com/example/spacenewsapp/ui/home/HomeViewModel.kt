package com.example.spacenewsapp.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.example.spacenewsapp.data.Result
import com.example.spacenewsapp.data.local.RecentSearch
import com.example.spacenewsapp.data.remote.Article
import com.example.spacenewsapp.data.remote.NewsSiteResponse
import com.example.spacenewsapp.data.remote.ResultsItem
import com.example.spacenewsapp.data.repository.ArticlesRepository
import com.example.spacenewsapp.ui.detail.DetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val articlesRepository: ArticlesRepository
):ViewModel() {

    private var _articles = MutableStateFlow<List<ResultsItem>>(emptyList())
    val articles: StateFlow<List<ResultsItem>> get() = _articles

    private var _newsSite = mutableStateOf(HomeState())
    val newsSite: State<HomeState> = _newsSite

    private val _filter = MutableStateFlow("")
    val filter : StateFlow<String> get() = _filter

    private val _query = MutableStateFlow("")
    val query : StateFlow<String> get() = _query

    init {
        viewModelScope.launch {
            getArticles()
            getNewsSites()
        }
    }

    fun getArticles(){
        viewModelScope.launch {
            val response = articlesRepository.getArticles()
            _articles.value = response
        }
    }

    fun getNewsSites(){
        viewModelScope.launch {
            val response = articlesRepository.getNewsSites().newsSite.toMutableList()
            response.add(0,"")
            _newsSite.value = HomeState(newsSite = response)
        }
    }

    fun filterNewsSites(filter: String) {
        viewModelScope.launch {
            if (articlesRepository.filterNewsSites(filter).isEmpty()) {
                _articles.value = articlesRepository.getArticles()
            } else {
                _articles.value = articlesRepository.filterNewsSites(filter)
            }
            _filter.value = filter

        }
    }

    fun onSearch(query: String) {
        viewModelScope.launch {
            val searchResult = articlesRepository.searchArticle(query)
            val recentSearch = RecentSearch(query)
            _articles.value = searchResult
            articlesRepository.insertRecentSearch(recentSearch)
        }
    }

    fun onQueryChanged(query: String) {
        _query.value = query
    }

    fun getAllRecentSearch(): Flow<List<RecentSearch>> {
        return articlesRepository.getAllRecentSearch()
    }

}

data class HomeState (
    val isLoading: Boolean = false,
    val articles: ResultsItem? = null,
    val newsSite: MutableList<String>? = null,
    val error: String = ""
)