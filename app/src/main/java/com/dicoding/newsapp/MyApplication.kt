package com.dicoding.newsapp

import android.app.Application
import com.dicoding.newsapp.core.di.CoreComponent
import com.dicoding.newsapp.core.di.DaggerCoreComponent
import com.dicoding.newsapp.di.AppComponent
import com.dicoding.newsapp.di.DaggerAppComponent


open class MyApplication : Application() {
    val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent.factory().create(applicationContext)
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(coreComponent)
    }
}