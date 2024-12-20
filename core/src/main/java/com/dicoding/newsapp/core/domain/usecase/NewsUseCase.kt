package com.dicoding.newsapp.core.domain.usecase

import com.dicoding.newsapp.core.data.Resource
import com.dicoding.newsapp.core.domain.model.News
import kotlinx.coroutines.flow.Flow


interface NewsUseCase {
    fun getNews(): Flow<Resource<List<News>>>
    fun getDetailNews(title: String): Resource<News>
    suspend fun setFavoriteNews(news: News)
    suspend fun deleteFavoriteNews(news: News)
    fun getFavoriteNews(): Flow<Resource<List<News>>>
    suspend fun isFavorite(title: String): Boolean
    fun getNewsByTitle(title: String): Flow<Resource<List<News>>>
}