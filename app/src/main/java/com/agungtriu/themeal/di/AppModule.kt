package com.agungtriu.themeal.di

import android.content.Context
import androidx.room.Room
import com.agungtriu.themeal.data.local.AppDatabase
import com.agungtriu.themeal.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideApiService(): ApiService =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .build()
            .create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "MEALS")
            .fallbackToDestructiveMigration()
            .build()
}