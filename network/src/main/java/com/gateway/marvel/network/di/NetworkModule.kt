package com.gateway.marvel.network.di

import com.gateway.marvel.network.endpoints.MarvelApi
import com.gateway.marvel.network.utils.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)

    @Provides
    fun provideOkHttpClientBuilder(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .addInterceptor(httpLoggingInterceptor)
        .build()

    @Provides
    fun provideGsonBuilder(): Gson = GsonBuilder()
        .setLenient()
        .serializeNulls()
        .create()

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL_MARVEL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    @Provides
    fun provideMarvelApi(
        retrofit: Retrofit
    ): MarvelApi = retrofit.create(MarvelApi::class.java)
}