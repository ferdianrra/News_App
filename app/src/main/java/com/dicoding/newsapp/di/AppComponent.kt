package com.dicoding.newsapp.di

import androidx.lifecycle.ViewModelProvider
import com.dicoding.newsapp.MainActivity
import com.dicoding.newsapp.core.di.CoreComponent
import dagger.Component

@AppScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [AppModule::class, ViewModelModule::class]
)
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): AppComponent
    }

    fun viewModelFactory(): ViewModelProvider.Factory

    fun inject(activity: MainActivity)
}