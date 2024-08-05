package com.example.spacenewsapp.data.retrofit

import com.example.spacenewsapp.data.remote.Article
import com.example.spacenewsapp.data.remote.ArticleResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("articles/")
    suspend fun getArticles(
    ): ArticleResponse

    @GET("articles/{id}/")
    suspend fun getArticleById(
        @Path("id") id: String
    ): Article


}