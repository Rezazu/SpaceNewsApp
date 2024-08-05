package com.example.spacenewsapp.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.PagingSource.LoadResult
import com.example.spacenewsapp.data.retrofit.ApiService

class ArticlesPaging (
    private val apiService: ApiService
): PagingSource <Int, ResultsItem>() {
    override fun getRefreshKey(state: PagingState<Int, ResultsItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1)?:anchorPage?.nextKey?.minus(1)
        }    }

    private var totalArticleCount = 0
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultsItem> {
        val page = params.key ?: 1
        return try {
            val articleResponse = apiService.getArticles()
            totalArticleCount += articleResponse.results.size!!
            val articles = articleResponse.results
            LoadResult.Page(
                data = articles,
                nextKey = if (totalArticleCount == articleResponse.count) null else page + 1,
                prevKey = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(
                throwable =  e
            )
        }    }
}