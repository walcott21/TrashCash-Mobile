package com.example.trashcash_mobile.network

import com.example.drawernav.models.ScoreboardEntry
import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String? = null,
    @SerializedName("data") val data: Any? = null,
)