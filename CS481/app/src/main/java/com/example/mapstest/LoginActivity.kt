package com.example.mapstest

import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.*

class LoginActivity : AppCompatActivity() {

    private lateinit var userRepository: UserRepository
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        //retrieves fields
        userRepository = UserRepository(this)
        usernameEditText = findViewById(R.id.editTextUsername)
        passwordEditText = findViewById(R.id.editTextPassword)

        val buttonLogin: Button = findViewById(R.id.buttonLogin)
        buttonLogin.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            loginUser(username, password)
        }

        val signUpTextView = findViewById<TextView>(R.id.signUp)
        signUpTextView.setOnClickListener {
            val intent = Intent(this, createAccount::class.java)
            startActivity(intent)
            finish()
        }
        signUpTextView.movementMethod = LinkMovementMethod.getInstance()
    }

    //compares login fields with existing users
    private fun loginUser(username: String, password: String) {
        GlobalScope.launch(Dispatchers.Main) {
            val isAuthenticated = userRepository.authenticateUser(username, password)
            if (isAuthenticated) {
                // Retrieve user ID
                val userId = userRepository.getUserIdByUsername(username)
                if (userId != null) {
                    // Log the user ID for debugging
                    Log.d("LoginActivity", "Authenticated user ID: $userId")

                    // Save user ID to shared preferences
                    val userSessionManager = UserSessionManager(this@LoginActivity)
                    userSessionManager.saveUserUID(userId)

                    // Log the saved user ID for debugging
                    val savedUserId = userSessionManager.getUserUID()
                    Log.d("LoginActivity", "Saved user ID in session: $savedUserId")

                    // Authentication successful, proceed to MapsActivity
                    val intent = Intent(this@LoginActivity, MapsActivity::class.java)
                    startActivity(intent)
                    finish() // Finish the LoginActivity to prevent going back
                } else {
                    // Handle case where user ID is null
                    Log.e("LoginActivity", "Error retrieving user ID")
                    Toast.makeText(this@LoginActivity, "Error retrieving user ID", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Authentication failed
                Log.e("LoginActivity", "Invalid username or password")
                Toast.makeText(this@LoginActivity, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}