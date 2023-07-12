package com.example.trashcash_mobile.network


import com.example.drawernav.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiInterface {
    @Headers("Content-Type: application/json")
    @POST("signup")
    fun createSignup(@Body userData: SignupData): Call<ApiResponse>

    @POST("login")
    fun login(@Body user: LoginData): Call<ApiResponse>

    @GET("collectionpoints")
    fun getCollectionPoints(@Body userData: SignupData): Call<ApiResponse>

    @GET("rewards")
    fun getRewards(): Call<List<RewardsModel>>

    @GET("trashlist")
    fun getTrashList(): Call<List<TrashList>>

    @GET("scoreboard")
    fun getScoreboardData(): Call<ScoreboardEntry>

    @GET("rewards_history")
    fun getHistoricalRewards(): Call<List<RewardHistory>>

    @POST("collectPrize")
    fun collectPrize(@Body userData: SignupData): Call<ApiResponse>
}