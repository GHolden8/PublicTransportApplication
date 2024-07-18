package com.example.mapstest

import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.Toast
import com.example.entities.User
import com.example.UserRepository
import kotlinx.coroutines.*
import java.security.MessageDigest

class createAccount : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var createButton: Button
    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create)

        //Initialize UserRepository
        userRepository = UserRepository(this)

        usernameEditText = findViewById(R.id.editTextUsername)
        passwordEditText = findViewById(R.id.editTextPassword)
        confirmPasswordEditText = findViewById(R.id.confirmPassword)
        createButton = findViewById(R.id.buttonCreate)

        createButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()

            //test****************************************************************************
            getAllUsers()

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 5 || password.length > 25) {
                Toast.makeText(this, "Password must be between 5 and 25 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!password.matches(Regex(".*[0-9].*")) || !password.matches(Regex(".*[!@#$%^&*].*"))) {
                Toast.makeText(this, "Password must contain at least one number and one special character", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //Hash the password
            val hashedPassword = hashPassword(password)

            val newUser = User(username, hashedPassword)

            //Perform database operation off the main thread
            insertUser(newUser)

            //Proceed to MapsActivity
            val intent = Intent(this@createAccount, MapsActivity::class.java)
            startActivity(intent)
            finish()
        }

        val signUpTextView = findViewById<TextView>(R.id.loginHere)
        signUpTextView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        signUpTextView.movementMethod = LinkMovementMethod.getInstance()
    }

    //inserts user by calling insertUser in userRepository
    private fun insertUser(user: User) {
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                userRepository.insertUser(user)
            }
        }
    }

    //hashes users password
    private fun hashPassword(password: String): String {
        //hashes using SHA-256
        val bytes = password.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("", { str, it -> str + "%02x".format(it) })
    }

    //Test**************************************************************************************
    private fun getAllUsers() {
        GlobalScope.launch(Dispatchers.Main) {
            val userList = userRepository.getAllUsers()
            if (userList != null && userList.isNotEmpty()) {
                for (user in userList) {
                    Log.d("CreateAccount", "User ID: ${user.uid}, Name: ${user.name}, Password: ${user.password}")
                }
            } else {
                Log.d("CreateAccount", "No users found in the database.")
            }
        }
    }
}
