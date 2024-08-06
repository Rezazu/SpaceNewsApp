package com.example.spacenewsapp.data.remote

import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.MutableStateFlow

data class Article(
	val summary: String,
    @SerializedName("news_site")
    val newsSite: String,
	val featured: Boolean,
    @SerializedName("updated_at")
    val updatedAt: String,
	@SerializedName("image_url")
	val imageUrl: String,
	val id: Int,
	val title: String,
    @SerializedName("published_at")
    val publishedAt: String,
	val url: String,
	val launches: List<Any?>,
	val events: List<Any?>
)

