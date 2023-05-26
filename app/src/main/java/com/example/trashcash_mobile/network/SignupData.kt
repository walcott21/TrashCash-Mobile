package com.example.trashcash_mobile.network

data class SignupData(
    val name: String,
    val email: String,
    val address: String,
    val password: String,
    val confirmPassword: String
)