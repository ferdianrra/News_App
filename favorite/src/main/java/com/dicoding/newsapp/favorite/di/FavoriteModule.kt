package com.dicoding.newsapp.favorite.di

import com.dicoding.newsapp.core.domain.usecase.NewsInteractor
import com.dicoding.newsapp.core.domain.usecase.NewsUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class FavoriteModule {
    @Binds
    abstract fun provideNewsUseCase(newsInteractor: NewsInteractor): NewsUseCase
}
