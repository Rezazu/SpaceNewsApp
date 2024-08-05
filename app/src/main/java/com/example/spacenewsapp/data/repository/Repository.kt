package com.example.spacenewsapp.data.repository

import androidx.paging.PagingData
import com.example.spacenewsapp.data.remote.ResultsItem
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getArticles(): Flow<PagingData<ResultsItem>>
}