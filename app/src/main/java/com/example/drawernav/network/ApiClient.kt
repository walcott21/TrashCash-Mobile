package com.example.trashcash_mobile.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://349fa854-1c37-46ad-9dfb-0c507d235aaa.mock.pstmn.io/"

    private val retrofit: Retrofit by lazy {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                val response = chain.proceed(request)
                val responseBody = response.peekBody(Long.MAX_VALUE)
                val modifiedResponse = response.newBuilder()
                    .body(ResponseBody.create(responseBody.contentType(), responseBody.string()))
                    .build()
                modifiedResponse
            }
            .build()

        val gson = GsonBuilder()
            .setLenient()
            .create()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun getApiClient(): Retrofit {
        return retrofit
    }
}
