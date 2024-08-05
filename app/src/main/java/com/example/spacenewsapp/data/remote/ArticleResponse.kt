package com.example.spacenewsapp.data.remote

data class ArticleResponse(
	val next: String? = null,
	val previous: Any? = null,
	val count: Int? = null,
	val results: List<ResultsItem?>? = null
)

data class LaunchesItem(
	val launchId: String? = null,
	val provider: String? = null
)

data class ResultsItem(
	val summary: String? = null,
	val newsSite: String? = null,
	val featured: Boolean? = null,
	val updatedAt: String? = null,
	val imageUrl: String? = null,
	val id: Int? = null,
	val title: String? = null,
	val publishedAt: String? = null,
	val url: String? = null,
	val launches: List<Any?>? = null,
	val events: List<Any?>? = null
)

