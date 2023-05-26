package com.example.trashcash_mobile.network


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {
    @POST("new")
    fun createSignup(@Body userData: SignupData): Call<ApiResponse>
}