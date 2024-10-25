package com.dicoding.newsapp.favorite.di

import androidx.lifecycle.ViewModelProvider
import com.dicoding.newsapp.MainActivity
import com.dicoding.newsapp.core.di.CoreComponent
import com.dicoding.newsapp.di.AppScope
import dagger.Component

@AppScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [FavoriteModule::class, FavoriteViewModelModule::class]
)

interface FavoriteComponent {

    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): FavoriteComponent
    }

    fun viewModelFactory(): ViewModelProvider.Factory

    fun inject(activity: MainActivity)

}