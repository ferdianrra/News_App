package com.dicoding.newsapp.favorite

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.newsapp.MyApplication
import com.dicoding.newsapp.core.domain.model.News
import com.dicoding.newsapp.favorite.di.DaggerFavoriteComponent
import com.dicoding.newsapp.ui.common.UiState
import com.dicoding.newsapp.ui.component.NewsItem
import com.dicoding.newsapp.ui.component.ShowError
import com.dicoding.newsapp.ui.component.ShowLoading
import com.dicoding.newsapp.ui.home.HomeViewModel
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    navigateBack:() -> Unit,
    navigateToDetail: (String) -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxWidth(),
        topBar = {
            AppBar(onBackClick = navigateBack, modifier)
        }
    ) {innerPadding ->
        val context = LocalContext.current
        val coreComponent = (context.applicationContext as MyApplication).coreComponent
        val favoriteComponent = DaggerFavoriteComponent.factory().create(coreComponent)
        val viewModelFactory = remember { favoriteComponent.viewModelFactory() }
        val viewModel: FavoriteViewModel = viewModel(
            factory = viewModelFactory
        )
        val uiState by viewModel.uiState.collectAsState()
        LaunchedEffect(Unit) {
            viewModel.showFavoriteNews()
        }
        when(uiState) {
            is UiState.Error -> {
                ShowError {
                    viewModel.viewModelScope.launch {
                        viewModel.showFavoriteNews()
                    }
                }
            }
            UiState.Loading ->  { ShowLoading() }
            is UiState.Success -> {
                FavoriteContent(
                    modifier=modifier.padding(innerPadding),
                    listNews = (uiState as UiState.Success<List<News>>).data as List<News>,
                    navigateToDetail = navigateToDetail
                )
            }
        }
    }
}

@Composable
fun FavoriteContent(
    modifier: Modifier = Modifier,
    listNews: List<News>,
    navigateToDetail: (String) -> Unit
) {
    Log.e("FavoriteScreen", listNews.toString())
    LazyColumn(
        modifier = modifier
    ) {
        items(listNews) { data ->
            NewsItem(
                modifier = Modifier.clickable {
                    navigateToDetail(data.title.toString())
                },
                imageUrl = data.imageUrl!!,
                title = data.title!!,
                author = data.author!!,
                date = data.date!!,

            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CenterAlignedTopAppBar(
        modifier = modifier.fillMaxWidth(),
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "Favorite",
                )
        },
        actions = {
            Spacer(modifier = Modifier.width(50.dp)) // Sesuaikan dengan ukuran IconButton untuk balancing
        }
    )
}