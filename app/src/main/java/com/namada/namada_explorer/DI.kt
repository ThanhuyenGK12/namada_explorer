package com.namada.namada_explorer

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.namada.namada_explorer.service.RpcService
import com.namada.namada_explorer.service.StakePoolService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DI {
    @Provides
    @Singleton
    fun diOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
        return builder.build()
    }

    @Provides
    @Singleton
    fun diGson(): Gson =
        GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()

    @Provides
    @Singleton
    fun diRpcService(
        factory: Gson,
        okHttpClient: OkHttpClient
    ): RpcService = Retrofit.Builder()
        .baseUrl("https://indexer.huy-tvq.com/")
        .addConverterFactory(GsonConverterFactory.create(factory))
        .client(okHttpClient)
        .build()
        .create()

    @Provides
    @Singleton
    fun diStakePoolService(
        factory: Gson,
        okHttpClient: OkHttpClient
    ): StakePoolService = Retrofit.Builder()
        .baseUrl("https://namada.api.explorers.guru/")
        .addConverterFactory(GsonConverterFactory.create(factory))
        .client(okHttpClient)
        .build()
        .create()

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().also { interceptor ->
            interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        }
}