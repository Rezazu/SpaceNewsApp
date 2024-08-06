package com.example.spacenewsapp.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.spacenewsapp.data.remote.Article
import com.example.spacenewsapp.data.remote.ResultsItem
import com.example.spacenewsapp.ui.theme.SpaceNewsAppTheme

@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    val context = LocalContext.current

    state.article?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AsyncImage(
                modifier = Modifier
                    .background(Color.Gray)
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop,
                model = ImageRequest
                    .Builder(context)
                    .data(state.article.imageUrl)
                    .build(),
                contentDescription = null
            )
            Text(
                text = state.article.title!!,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
            )
            Text(
                text = state.article.summary!!,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailScreenPreview() {
    SpaceNewsAppTheme {
//        DetailScreen(
//            article = Article(
//                title = "Article title",
//                summary = "Ini adalah tulisan summary yang berisi",
//                imageUrl = "",
//                newsSite = "",
//                featured = true,
//                updatedAt = "",
//                id = 10,
//                publishedAt = "",
//                url = "",
//                launches = emptyList(),
//                events = emptyList()
//            ),
//        )
    }
}