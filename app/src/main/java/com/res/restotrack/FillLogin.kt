package com.res.restotrack

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class FillLogin : Activity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_login)

        val et_username = findViewById<EditText>(R.id.username)
        val et_password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.loginButton_2)

        sharedPreferences = getSharedPreferences("UserProfile", MODE_PRIVATE)

        val passedUsername = intent.getStringExtra("username") ?: ""
        val passedPassword = intent.getStringExtra("password") ?: ""

        if (passedUsername.isNotEmpty()) et_username.setText(passedUsername)
        if (passedPassword.isNotEmpty()) et_password.setText(passedPassword)

        login.setOnClickListener {
            val inputUsername = et_username.text.toString().trim()
            val inputPassword = et_password.text.toString().trim()

            if (inputUsername.isEmpty() || inputPassword.isEmpty()) {
                Toast.makeText(this, "Please put your information", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!inputUsername.contains("@gmail.com")) {
                Toast.makeText(this, "Username must be a valid Gmail address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (inputPassword.length < 8) {
                Toast.makeText(this, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val storedUsername = sharedPreferences.getString("username", "")
            val storedPassword = sharedPreferences.getString("password", "")

            if (inputUsername == storedUsername && inputPassword == storedPassword) {
                val intent = Intent(this, Landing::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
