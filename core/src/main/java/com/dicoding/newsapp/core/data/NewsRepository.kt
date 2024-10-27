package com.dicoding.newsapp.core.data

import com.dicoding.newsapp.core.data.source.local.LocalDataSource
import com.dicoding.newsapp.core.data.source.remote.RemoteDataSource
import com.dicoding.newsapp.core.data.source.remote.network.ApiResponse
import com.dicoding.newsapp.core.domain.model.News
import com.dicoding.newsapp.core.domain.repository.INewsRepository
import com.dicoding.newsapp.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
): INewsRepository {
    private var newsList = listOf<News>()
    private var favNewsList = listOf<News>()
    override  fun getGeneralNews(): Flow<Resource<List<News>>> {
       return remoteDataSource.getListGeneral().map {apiResponse ->
           when(apiResponse) {
               is ApiResponse.Success -> {
                   newsList = DataMapper.mapResponseToDomain(apiResponse.data)
                   Resource.Success(newsList)
               }
               is ApiResponse.Empty -> {
                   Resource.Error("Empty")
               }
               is ApiResponse.Error -> {
                   Resource.Error(apiResponse.errorMessage)
               }
           }
       }
    }

    override fun getNewsByTitle(title: String): Flow<Resource<List<News>>> {
        return remoteDataSource.getListByTitle(title).map {apiResponse ->
            when(apiResponse) {
                is ApiResponse.Success -> {
                    newsList = DataMapper.mapResponseToDomain(apiResponse.data)
                    Resource.Success(newsList)
                }
                is ApiResponse.Empty -> {
                    Resource.Error("Empty")
                }
                is ApiResponse.Error -> {
                    Resource.Error(apiResponse.errorMessage)
                }
            }
        }
    }

    override fun getDetailNews(title: String): Resource<News> {
        val combinedList = newsList + favNewsList
        val news = combinedList.firstOrNull { it.title == title }
        return if (news != null) {
            Resource.Success(news)
        } else {
            Resource.Error("News not found")
        }
    }

    override suspend fun setFavoriteNews(news: News) {
        val newsMap = DataMapper.mapDomainToEntity(news)
        localDataSource.insertNews(newsMap)
    }

    override suspend fun deleteFavoriteNews(news: News) {
        val newsMap = DataMapper.mapDomainToEntity(news)
        localDataSource.deleteNews(newsMap)
    }

    override fun getFavoriteNews(): Flow<Resource<List<News>>> {
        return flow {
            try {
                emit(Resource.Loading())
                val favoriteNewsList = localDataSource.getFavoriteNews().first()
                favNewsList = DataMapper.mapEntitiesToDomain(favoriteNewsList)
                emit(Resource.Success(favNewsList))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Error occurred"))
            }
        }
    }

    override suspend fun isFavorite(title: String): Boolean {
        return localDataSource.isFavorite(title)
    }
}