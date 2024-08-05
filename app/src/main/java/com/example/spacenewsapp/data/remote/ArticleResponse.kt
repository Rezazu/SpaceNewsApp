package com.example.spacenewsapp.data.remote

data class ArticleResponse(
	val next: String,
	val previous: Any,
	val count: Int,
	val results: List<ResultsItem>
)

data class LaunchesItem(
	val launchId: String? = null,
	val provider: String? = null
)

data class ResultsItem(
	val summary: String,
	val news_site: String,
	val featured: Boolean,
	val updated_at: String,
	val image_url: String,
	val id: Int,
	val title: String,
	val published_at: String,
	val url: String,
	val launches: List<Any>,
	val events: List<Any>
)

