package com.example.spacenewsapp.ui.home

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.room.Query
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.spacenewsapp.R
import com.example.spacenewsapp.data.local.RecentSearch
import com.example.spacenewsapp.data.remote.ResultsItem
import com.example.spacenewsapp.ui.Screen
import com.example.spacenewsapp.ui.theme.SpaceNewsAppTheme
import kotlin.reflect.KFunction1

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {

    val articles by viewModel.articles.collectAsState(initial = emptyList())
    val recentSearch by viewModel.getAllRecentSearch().collectAsState(initial = emptyList())
    val filter by viewModel.filter.collectAsState()
    val newsSites by viewModel.newsSite
    val query by viewModel.query.collectAsState(initial = "")

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SearchBar(
            query = query,
            onSearch = viewModel::onSearch,
            onQueryChange = viewModel::onQueryChanged,
            recentSearch = recentSearch
        )
        Text(
            text = "Filter News Site",
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
        )
        newsSites.newsSite?.let {
            FilterDropDown(
                newsSites = it,
                modifier = Modifier.padding(16.dp),
                onValueChanged = viewModel::filterNewsSites,
                filter = filter
            )
        }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onSearch: (String) -> Unit,
    onQueryChange: (String) -> Unit,
    recentSearch: List<RecentSearch>,
    modifier: Modifier = Modifier
) {
    var active by rememberSaveable { mutableStateOf(false) }
    var text by remember {
        mutableStateOf(query)
    }
    androidx.compose.material3.SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = {
            onSearch(query)
            active = false
        },
        active = active,
        onActiveChange = { active = it },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        placeholder = {
            Text(text = "Search News")
        },
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp),
        content = {
            recentSearch.forEach {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onSearch(it.query)
                            active = false
                        }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_recent),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                    )
                    Text(
                        text = it.query,
                        modifier = Modifier
                            .padding(16.dp)
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDropDown(
    newsSites: MutableList<String>,
    modifier: Modifier,
    onValueChanged: (String) -> Unit,
    filter: String,
    context: Context = LocalContext.current
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    var selectedText by remember {
        mutableStateOf(filter)
    }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded},
        ) {
            TextField(
                value = selectedText,
                onValueChange = { },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)},
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                newsSites.forEach { newsSite ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = newsSite,
                                color = Color.Black
                            )
                        },
                        onClick = {
                            selectedText = newsSite
                            expanded = false
                            onValueChanged(selectedText)

                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
    }

}

@Composable
fun ArticleItem(
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
                contentDescription = null,
                error = painterResource(id = R.drawable.img_space)
            )
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(
                        vertical = 16.dp,
                        horizontal = 8.dp
                    )
            ) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                )
                Row (
                    modifier = Modifier
                        .padding(4.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_planet),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                    )
                    Text(
                        text = article.news_site,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .padding(4.dp)
                    )
                }
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