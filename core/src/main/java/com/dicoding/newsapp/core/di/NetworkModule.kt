package com.dicoding.newsapp.core.di

import com.dicoding.newsapp.core.BuildConfig
import com.dicoding.newsapp.core.data.source.remote.network.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {
    private val hostname = "newsapi.org"
    private val certificatePinner = CertificatePinner.Builder()
        .add(hostname, "sha256/p/RZhoZj/bpjprMDMoNVQ04tZgIX0kd39gI9NxT6w8k=")
        .add(hostname, "sha256/o3acJR7JExJvqoEhsElholZyQx4woNrA5d3b3DDQgh8=")
        .add(hostname, "sha256/kIdp6NNEd8wsugYyyIYFsi1ylMCED3hZbSR8ZFsa/A4=")
        .build()
    @Provides
    fun providesOkHttpClient(): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor { chain ->
                val original = chain.request()
                val originalHttpUrl = original.url
                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("apiKey", BuildConfig.API_KEY)
                    .build()
                val requestBuilder = original.newBuilder().url(url)
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }

    @Provides
    fun provideApiService(client: OkHttpClient): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}