package com.example.flickfinder.di

import com.example.flickfinder.domain.WatchmodeApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.watchmode.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create()) // Add RxJava3 adapter
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): WatchmodeApiService {
        return retrofit.create(WatchmodeApiService::class.java)
    }



}
