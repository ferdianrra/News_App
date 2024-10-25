package com.dicoding.newsapp.di

import com.dicoding.newsapp.core.domain.usecase.NewsInteractor
import com.dicoding.newsapp.core.domain.usecase.NewsUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds
    abstract fun provideNewsUseCase(newsInteractor: NewsInteractor): NewsUseCase
}