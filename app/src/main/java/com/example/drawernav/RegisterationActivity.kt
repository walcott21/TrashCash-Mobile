// RegistrationActivity.kt
package com.example.myapplication
import UserDataManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.drawernav.LoginActivity
import com.example.drawernav.MainActivity
import com.example.drawernav.R
import com.example.trashcash_mobile.models.MyApp
import com.example.trashcash_mobile.network.ApiInterface
import com.example.trashcash_mobile.network.ApiResponse
import com.example.trashcash_mobile.network.SignupData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


data class ValidationResult(
    val status: Boolean,
    val errorMessage: String
)

class RegistrationActivity : AppCompatActivity() {

    private lateinit var etFullName: EditText
    private lateinit var etUsername: EditText
    private lateinit var etAddress: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPhoneNumber: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var tvLogin: TextView

    private lateinit var apiInterface: ApiInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        etFullName = findViewById(R.id.et_full_name)
        etUsername = findViewById(R.id.et_username)
        etAddress = findViewById(R.id.editTextAddress)
        etEmail = findViewById(R.id.et_email)
        etPhoneNumber = findViewById(R.id.et_phone_number)
        etPassword = findViewById(R.id.et_password)
        etConfirmPassword = findViewById(R.id.editTextConfirmPassword)
        btnRegister = findViewById(R.id.btn_register)
        tvLogin = findViewById(R.id.tv_login)
        val progressBar: ProgressBar = findViewById(R.id.progressBar)

        btnRegister.setOnClickListener {
            registerUser(progressBar)
        }

        tvLogin.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun registerUser(progressBar: ProgressBar ) {
        val fullName = etFullName.text.toString()
        val username = etUsername.text.toString()
        val email = etEmail.text.toString()
        val phoneNumber = etPhoneNumber.text.toString()
        val address = etAddress.text.toString()
        val confirmPassword = etConfirmPassword.text.toString()
        val password = etPassword.text.toString()

        val signupData = SignupData(fullName,username,phoneNumber, email, address, password, confirmPassword)
        val validationResult = validateSignupData(signupData)

        if(validationResult.status){
            progressBar.visibility = View.VISIBLE
            createNewUser(signupData)
            progressBar.visibility = View.GONE
        }else{
            Toast.makeText(applicationContext, validationResult.errorMessage, Toast.LENGTH_SHORT).show()
        }

    }

    private fun createNewUser(signupData:SignupData){
        apiInterface.createSignup(signupData).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()

                    val userDataManager = UserDataManager(MyApp.appContext)
                    userDataManager.address = signupData.address
                    userDataManager.email = signupData.email
                    userDataManager.name = signupData.name

                    val intent = Intent(this@RegistrationActivity, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(applicationContext, "Failed to register", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "Failed to communicate with the server", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun validateSignupData(signupData: SignupData): ValidationResult {
        if (signupData.password != signupData.confirmPassword) {
            return ValidationResult(false, "Passwords do not match.")
        }

        if (!isEmailValid(signupData.email)) {
            return ValidationResult(false, "Invalid email format.")
        }

        if (signupData.address.isNullOrBlank() || signupData.name.isNullOrBlank()) {
            return ValidationResult(false, "Name and address cannot be empty.")
        }

        return ValidationResult(true, "") // Validation successful
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun isEmailValid(email: String): Boolean {
        val emailRegex = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")
        return emailRegex.matches(email)
    }
}