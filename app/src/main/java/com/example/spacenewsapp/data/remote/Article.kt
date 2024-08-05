package com.example.spacenewsapp.data.remote

import com.google.gson.annotations.SerializedName

data class Article(
	val summary: String? = null,
	val newsSite: String? = null,
	val featured: Boolean? = null,
	val updatedAt: String? = null,
	@SerializedName("image_url")
	val imageUrl: String? = null,
	val id: Int? = null,
	val title: String? = null,
	val publishedAt: String? = null,
	val url: String? = null,
	val launches: List<Any?>? = null,
	val events: List<Any?>? = null
)

