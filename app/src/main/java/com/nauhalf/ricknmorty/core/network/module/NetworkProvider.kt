package com.nauhalf.ricknmorty.core.network.module

import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder
import com.nauhalf.ricknmorty.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkProvider(
    private val url: String,
) {

    fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(createOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createOkHttpClient(): OkHttpClient {
        val httpBuilder = OkHttpClient.Builder()
            .readTimeout(3, TimeUnit.MINUTES)
            .writeTimeout(3, TimeUnit.MINUTES)
            .retryOnConnectionFailure(true)

        if(BuildConfig.DEBUG){
            httpBuilder.addInterceptor(getHttpLoggingInterceptor())
        }
        return httpBuilder.build()
    }


    private fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor { message ->
            Log.d("Interceptor", message)
        }
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }
}
