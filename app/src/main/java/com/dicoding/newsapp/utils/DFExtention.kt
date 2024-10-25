package com.dicoding.newsapp.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FavoriteScreen( modifier: Modifier = Modifier, navigateBack:() -> Unit,  navigateToDetail: (String) -> Unit) {
    DFUtils.favoriteScreen (modifier, navigateBack, navigateToDetail)
}