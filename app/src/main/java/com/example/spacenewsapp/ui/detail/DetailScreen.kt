package com.example.spacenewsapp.ui.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.spacenewsapp.data.remote.Article
import com.example.spacenewsapp.data.remote.ResultsItem
import com.example.spacenewsapp.ui.theme.SpaceNewsAppTheme

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    article: Article
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AsyncImage(
            modifier = Modifier
                .size(128.dp)
                .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop,
            model = ImageRequest
                .Builder(context)
                .data(article.imageUrl)
                .build(),
            contentDescription = null
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailScreenPreview() {
    SpaceNewsAppTheme {
        DetailScreen(
            article = Article(
                title = "Article title",
                summary = "Ini adalah tulisan summary yang berisi",
                imageUrl = "",
                newsSite = "",
                featured = true,
                updatedAt = "",
                id = 10,
                publishedAt = "",
                url = "",
                launches = emptyList(),
                events = emptyList()
            ),
        )
    }
}