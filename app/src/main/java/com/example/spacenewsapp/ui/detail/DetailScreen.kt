package com.example.spacenewsapp.ui.detail

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.spacenewsapp.R
import com.example.spacenewsapp.Service
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
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f),
                contentScale = ContentScale.Crop,
                model = ImageRequest
                    .Builder(context)
                    .data(state.article.imageUrl)
                    .build(),
                contentDescription = null,
                error = painterResource(id = R.drawable.img_space)
            )
            Text(
                text = state.article.title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(16.dp)
            )
            Row (
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_calendar),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                )
                Text(
                    text =  Service.formatDate(state.article.publishedAt) ?: state.article.publishedAt,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }
            Text(
                text = state.article.summary.substringBefore(".") + ".",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailScreenPreview() {
    SpaceNewsAppTheme {

    }
}