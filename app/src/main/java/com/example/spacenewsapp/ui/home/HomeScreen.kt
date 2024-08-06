package com.example.spacenewsapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.spacenewsapp.data.remote.ResultsItem
import com.example.spacenewsapp.ui.Screen
import com.example.spacenewsapp.ui.theme.SpaceNewsAppTheme

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {

    val articles by viewModel.articles.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(articles.size) {
                articles[it].let { article ->
                    ArticleItem(
                        article = article,
                        onItemClick = { navController.navigate(Screen.DetailScreen.route + "/${article.id}") }
                    )
                }
            }
        }
    }

}

@Composable
fun ArticleItem(
    modifier: Modifier = Modifier,
    article : ResultsItem,
    onItemClick: (ResultsItem) -> Unit
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp)
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.background)
            .clickable { onItemClick(article) }
    ) {
        Row (
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
                    .data(article.image_url)
                    .build(),
                contentDescription = null
            )
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                )
                Text(
                    text = article.summary,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ArticleItemPreview() {
    SpaceNewsAppTheme {
        ArticleItem(
            article = ResultsItem(
                title = "Article title",
                summary = "Ini adalah tulisan summary yang berisi",
                image_url = "",
                news_site = "",
                featured = true,
                updated_at = "",
                id = 10,
                published_at = "",
                url = "",
                launches = emptyList(),
                events = emptyList()
            ),
            onItemClick = {}
        )
    }
}