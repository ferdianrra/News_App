package com.dicoding.newsapp.core.di

import android.content.Context
import androidx.room.Room
import com.dicoding.newsapp.core.data.source.local.room.NewsDao
import com.dicoding.newsapp.core.data.source.local.room.NewsDatabase
import dagger.Module
import dagger.Provides
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): NewsDatabase {
        val passPhrase: ByteArray = SQLiteDatabase.getBytes("MyPassword".toCharArray())
        return Room.databaseBuilder(
            context,
            NewsDatabase::class.java, "News.db"
        )
            .openHelperFactory(SupportFactory(passPhrase))
            .fallbackToDestructiveMigration()
            .build()
}
    @Provides
    fun proviedNewsDao(database: NewsDatabase): NewsDao = database.newsDao()
}