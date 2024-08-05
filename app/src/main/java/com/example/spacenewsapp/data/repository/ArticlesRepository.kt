package com.example.spacenewsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.spacenewsapp.data.remote.Article
import com.example.spacenewsapp.data.remote.ArticleResponse
import com.example.spacenewsapp.data.remote.ArticlesPaging
import com.example.spacenewsapp.data.remote.ResultsItem
import com.example.spacenewsapp.data.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class ArticlesRepository @Inject constructor(
    private val apiService: ApiService
) {
//     fun getArticles(): Flow<PagingData<ResultsItem>> {
//        return Pager(
//            config = PagingConfig(pageSize = 10),
//            pagingSourceFactory = {
//                ArticlesPaging(
//                    apiService = apiService
//                )
//            }
//        ).flow
//    }

    suspend fun getArticles(): List<ResultsItem> {
        return apiService.getArticles().results
    }

    suspend fun getArticleById(news_id: String): Article {
        return apiService.getArticleById(id = news_id)
    }
}