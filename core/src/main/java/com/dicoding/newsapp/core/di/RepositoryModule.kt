package com.dicoding.newsapp.core.di

import com.dicoding.newsapp.core.data.NewsRepository
import com.dicoding.newsapp.core.domain.repository.INewsRepository
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class, DatabaseModule::class])
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(newsRepository: NewsRepository): INewsRepository
}