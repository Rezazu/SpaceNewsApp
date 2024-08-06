package com.example.spacenewsapp.di

import android.content.Context
import android.widget.Space
import androidx.room.Room
import com.example.spacenewsapp.Const.BASE_URL
import com.example.spacenewsapp.Const.DATABASE_NAME
import com.example.spacenewsapp.data.local.SpaceNewsDatabase
import com.example.spacenewsapp.data.repository.ArticlesRepository
import com.example.spacenewsapp.data.retrofit.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideArticlesApi(): ApiService {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val authInterceptor = Interceptor { chain ->
            val req = chain.request()
            val requestHeaders = req.newBuilder()
                .addHeader("Authorization", BASE_URL)
                .build()
            chain.proceed(requestHeaders)
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(interceptor)
            .build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideSpaceNewsDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, SpaceNewsDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideRecentSearchDao(
        database: SpaceNewsDatabase
    ) = database.recentSearchDao()



}