package com.example.trashcash_mobile

import UserDataManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
//import android.telecom.Call
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.trashcash_mobile.databinding.ActivitySignupBinding
import com.example.trashcash_mobile.models.MyApp
import com.example.trashcash_mobile.network.ApiClient
import com.example.trashcash_mobile.network.ApiInterface
import com.example.trashcash_mobile.network.ApiResponse
import com.example.trashcash_mobile.network.SignupData
import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
//import com.google.android.gms.common.api.Response
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var apiInterface: ApiInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiInterface = ApiClient.getApiClient().create(ApiInterface::class.java)

        val progressBar: ProgressBar = findViewById(R.id.progressBar)


        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.buttonSignUp.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val email = binding.editTextEmail.text.toString()
            val address = binding.editTextAddress.text.toString()
            val password = binding.editTextPassword.text.toString()
            val confirmPassword = binding.editTextConfirmPassword.text.toString()

            val signupData = SignupData(name, email, address, password, confirmPassword)

            if(password == confirmPassword){
                progressBar.visibility = View.VISIBLE
                apiInterface.createSignup(signupData).enqueue(object : Callback<ApiResponse> {
                    override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                        // Handle API response on success
                        progressBar.visibility = View.GONE
                        if (response.isSuccessful) {
                            //val signupResponse = response.body()
                            Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()

                            val userDataManager = UserDataManager(MyApp.appContext)
                            userDataManager.address = address
                            userDataManager.email = email
                            userDataManager.name = name

                            var intent = Intent(this@SignupActivity,MainPageActivity::class.java )
                            startActivity(intent)
                        } else {
                            Toast.makeText(applicationContext, "Failed to register", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, "Failed to communicate with the server", Toast.LENGTH_SHORT).show()
                        progressBar.visibility = View.GONE
                    }
                })

            }else{
                Toast.makeText(applicationContext, "The password must match", Toast.LENGTH_SHORT).show()
            }


        }
    }
}



