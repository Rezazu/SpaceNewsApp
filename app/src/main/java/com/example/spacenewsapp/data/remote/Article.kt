package com.example.spacenewsapp.data.remote

import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.MutableStateFlow

data class Article(
	val summary: String? = null,
    @SerializedName("news_site")
    val newsSite: String? = null,
	val featured: Boolean? = null,
    @SerializedName("updated_at")
    val updatedAt: String? = null,
	@SerializedName("image_url")
	val imageUrl: String? = null,
	val id: Int? = null,
	val title: String? = null,
    @SerializedName("publised_at")
    val publishedAt: String? = null,
	val url: String? = null,
	val launches: List<Any?>? = null,
	val events: List<Any?>? = null
)

