package com.dicoding.newsapp.core.di

import android.content.Context
import com.dicoding.newsapp.core.domain.repository.INewsRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [RepositoryModule::class]
)
interface CoreComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): CoreComponent
    }

    fun provideRepository(): INewsRepository
}