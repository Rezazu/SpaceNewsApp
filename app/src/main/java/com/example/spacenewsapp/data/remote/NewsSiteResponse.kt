package com.example.spacenewsapp.data.remote

import com.google.gson.annotations.SerializedName

data class NewsSiteResponse(
    @SerializedName("news_sites")
    val newsSite: List<String>,
    val version: String
)