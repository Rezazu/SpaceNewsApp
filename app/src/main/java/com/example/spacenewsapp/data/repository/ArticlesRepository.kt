package com.example.spacenewsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.room.Query
import com.example.spacenewsapp.data.Result
import com.example.spacenewsapp.data.local.RecentSearch
import com.example.spacenewsapp.data.local.RecentSearchDao
import com.example.spacenewsapp.data.local.SpaceNewsDatabase
import com.example.spacenewsapp.data.remote.Article
import com.example.spacenewsapp.data.remote.ArticleResponse
import com.example.spacenewsapp.data.remote.ArticlesPaging
import com.example.spacenewsapp.data.remote.NewsSiteResponse
import com.example.spacenewsapp.data.remote.ResultsItem
import com.example.spacenewsapp.data.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ArticlesRepository @Inject constructor(
    private val apiService: ApiService,
    private val recentSearchDao: RecentSearchDao
) {

    suspend fun getArticles(): List<ResultsItem> {
        return apiService.getArticles().results
    }

    suspend fun getArticleById(id: String): Flow<Result<Article>> = flow {
        try {
            emit(Result.Loading)
            val article = apiService.getArticleById(id)
            emit(Result.Success(article))
        } catch (e: HttpException) {
            emit(Result.Error(e.localizedMessage ?: "An unexpected error, but a welcome one"))
        } catch (e: IOException) {
            emit(Result.Error("So unicivilized (No Connection!)"))
        }
    }

    suspend fun getNewsSites(): NewsSiteResponse {
        return apiService.getNewsSite()
    }

    suspend fun filterNewsSites(filter: String): List<ResultsItem> {
        return apiService.getArticles().results.filter {
            it.news_site.contains(filter, ignoreCase = true)
        }
    }

    suspend fun searchArticle(query: String): List<ResultsItem> {
        return apiService.getArticles().results.filter {
            it.title.contains(query, ignoreCase = true)
        }
    }

    suspend fun insertRecentSearch(recentSearch: RecentSearch) {
        recentSearchDao.insertRecentSearch(recentSearch)
    }

    fun getAllRecentSearch(): Flow<List<RecentSearch>> {
        return recentSearchDao.getAllRecentSearch()
    }
}