package com.res.restotrack

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast

class ForgotPasswordActivity : Activity() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        sharedPreferences = getSharedPreferences("UserProfile", MODE_PRIVATE)

        val inputCurrentPassword: EditText = findViewById(R.id.input_current_password)
        val inputNewPassword: EditText = findViewById(R.id.input_new_password)
        val inputConfirmNewPassword: EditText = findViewById(R.id.input_confirm_new_password)
        val resetPasswordButton: Button = findViewById(R.id.resetPasswordButton)
        val backIcon: ImageView = findViewById(R.id.backIcon)

        backIcon.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        resetPasswordButton.setOnClickListener {
            val currentPassword = inputCurrentPassword.text.toString().trim()
            val newPassword = inputNewPassword.text.toString().trim()
            val confirmNewPassword = inputConfirmNewPassword.text.toString().trim()

            val savedPassword = sharedPreferences.getString("password", "")

            if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (currentPassword != savedPassword) {
                Toast.makeText(this, "Current password is incorrect", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (newPassword.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (newPassword != confirmNewPassword) {
                Toast.makeText(this, "New password does not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val editor = sharedPreferences.edit()
            editor.putString("password", newPassword)
            editor.apply()

            Toast.makeText(this, "Password reset successful!", Toast.LENGTH_LONG).show()

            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("password", newPassword)
            startActivity(intent)
            finish()
        }
    }
}
