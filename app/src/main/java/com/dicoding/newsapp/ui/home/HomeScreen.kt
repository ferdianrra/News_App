@file:Suppress("UNCHECKED_CAST")

package com.dicoding.newsapp.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.newsapp.MyApplication
import com.dicoding.newsapp.core.domain.model.News
import com.dicoding.newsapp.ui.common.UiState
import com.dicoding.newsapp.ui.component.NewsItem
import com.dicoding.newsapp.ui.component.SearchBar
import com.dicoding.newsapp.ui.component.ShowError
import com.dicoding.newsapp.ui.component.ShowLoading
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit
) {
    val context = LocalContext.current
    val appComponent = (context.applicationContext as MyApplication).appComponent
    val viewModelFactory = remember { appComponent.viewModelFactory() }
    val viewModel: HomeViewModel = viewModel(
        factory = viewModelFactory
    )
    val uiState by viewModel.uiState.collectAsState()
    when(uiState) {
        is UiState.Error -> {
            ShowError( onClick = {
                viewModel.viewModelScope.launch {
                    viewModel.getNews()
                }
            } )
        }
        UiState.Loading -> {
            ShowLoading()
        }
        is UiState.Success -> {
            HomeContent(listNews = (uiState as UiState.Success<List<News>>).data as List<News>, navigateToDetail = navigateToDetail, viewModel = viewModel )
        }
    }
    viewModel.uiState.collectAsState().value.let {

    }
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    listNews: List<News>,
    navigateToDetail:(String) -> Unit,
    viewModel: HomeViewModel
) {
    val filteredList = listNews.filter { item ->
        item.imageUrl != null && item.title != null && item.author != null && item.date != null && item.content != null
    }
    LazyColumn {
        item {
            SearchBar(
                onSearch = { title ->
                   viewModel.viewModelScope.launch {
                       viewModel.getNewsByTitle(title)
                   }
                }
            )
        }
        items(filteredList) { data ->
            val title = data.title.toString()
            NewsItem(
                imageUrl = data.imageUrl.toString(),
                title = title,
                author = data.author.toString(),
                date = data.date.toString(),
                modifier = modifier.clickable {
                    navigateToDetail(title)
                }
            )
        }
    }
}
