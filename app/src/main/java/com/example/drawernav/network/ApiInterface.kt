package com.example.trashcash_mobile.network


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {
    @POST("/signup")
    fun createSignup(@Body userData: SignupData): Call<ApiResponse>

    @POST("/login")
    fun login(@Body userData: SignupData): Call<ApiResponse>

    @GET("/collectionpoints")
    fun getCollectionPoints(@Body userData: SignupData): Call<ApiResponse>

    @GET("/rewards")
    fun getRewards(@Body userData: SignupData): Call<ApiResponse>

    @GET("/trashlist")
    fun getTrashList(@Body userData: SignupData): Call<ApiResponse>

    @GET("/scoreboard")
    fun getScoreboardData(@Body userData: SignupData): Call<ApiResponse>

    @GET("/historicalRewards")
    fun getHistoricalRewards(@Body userData: SignupData): Call<ApiResponse>

    @POST("/collectPrize")
    fun collectPrize(@Body userData: SignupData): Call<ApiResponse>
}