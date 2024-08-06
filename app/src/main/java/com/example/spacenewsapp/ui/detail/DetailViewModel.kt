package com.example.spacenewsapp.ui.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spacenewsapp.Const.PARAM_ID
import com.example.spacenewsapp.data.Result
import com.example.spacenewsapp.data.remote.Article
import com.example.spacenewsapp.data.remote.ResultsItem
import com.example.spacenewsapp.data.repository.ArticlesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val articlesRepository: ArticlesRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = mutableStateOf(DetailState())
    val state: State<DetailState> = _state

    init {
        viewModelScope.launch {
            savedStateHandle.get<String>(PARAM_ID)?.let { id ->
                getArticleById(id)
            }
        }
    }

    private suspend fun getArticleById(id: String) {
        articlesRepository.getArticleById(id).onEach { result ->
            when(result) {
                is Result.Success -> {
                    _state.value = DetailState(article = result.data)
                }
                is Result.Error -> {
                    _state.value = DetailState(error =
                    "An unexpected error, but a welcome one")
                }
                is Result.Loading -> {
                    _state.value = DetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}

data class DetailState (
    val isLoading: Boolean = false,
    val article: Article? = null,
    val error: String = ""
)