// LoginActivity.kt
package com.example.drawernav
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.drawernav.models.LoginData
import com.example.myapplication.RegistrationActivity
import com.example.trashcash_mobile.network.ApiClient
import com.example.trashcash_mobile.network.ApiInterface
import com.example.trashcash_mobile.network.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.*



class LoginActivity : AppCompatActivity() {
    private lateinit var apiInterface: ApiInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        apiInterface = ApiClient.getApiClient().create(ApiInterface::class.java)

        val user: TextView = findViewById(R.id.email_box)
        val password:TextView = findViewById(R.id.password_box)

        val loginButton = findViewById<Button>(R.id.login_button)
        loginButton.setOnClickListener {
            login(user.text.toString(),password.text.toString())
        }

        val registerButton = findViewById<Button>(R.id.Register_button)
        registerButton.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login(user: String,password:String){
        try {
            val logindata = LoginData(user,password)
            apiInterface.login(logindata).enqueue(
                object : Callback<ApiResponse> {
                    override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
//                      if (response.isSuccessful) {
                            val loginResponse = response.body()
                            val token = loginResponse?.message
                            val token2 = loginResponse?.status
                            val token3 = loginResponse?.data
//                            val tokenType = loginResponse?.type

                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
//                        } else {
//                            Toast.makeText(applicationContext, "Failed to register", Toast.LENGTH_SHORT).show()
//                        }
                    }

                    override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, "Failed to communicate with the server", Toast.LENGTH_SHORT).show()
                    }
                }

            )
        }catch (e: Exception){
            print (e)
        }

    }
}
