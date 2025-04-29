package com.res.restotrack

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class Registration : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val Username = findViewById<EditText>(R.id.username_r)
        val Firstname = findViewById<EditText>(R.id.firstName)
        val Lastname = findViewById<EditText>(R.id.lastName)
        val Password = findViewById<EditText>(R.id.password_r)
        val Confirmpassword = findViewById<EditText>(R.id.confirmpassword)
        val submit = findViewById<Button>(R.id.singup_2)

        intent?.let {
            it.getStringExtra("username")?.let { username -> Username.setText(username) }
            it.getStringExtra("firstname")?.let { firstname -> Firstname.setText(firstname) }
            it.getStringExtra("lastname")?.let { lastname -> Lastname.setText(lastname) }
            it.getStringExtra("password")?.let { password -> Password.setText(password) }
            it.getStringExtra("cpass")?.let { cpass -> Confirmpassword.setText(cpass) }
        }

        submit.setOnClickListener {
            val usernameText = Username.text.toString().trim()
            val firstNameText = Firstname.text.toString().trim()
            val lastNameText = Lastname.text.toString().trim()
            val passwordText = Password.text.toString()
            val confirmPasswordText = Confirmpassword.text.toString()

            if (usernameText.isEmpty() || firstNameText.isEmpty() || lastNameText.isEmpty() ||
                passwordText.isEmpty() || confirmPasswordText.isEmpty()) {
                Toast.makeText(this, "Please fill out all information", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!usernameText.contains("@gmail.com")) {
                Toast.makeText(this, "Username must be a valid Gmail address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (passwordText.length < 8) {
                Toast.makeText(this, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (passwordText != confirmPasswordText) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            val sharedPreferences = getSharedPreferences("UserProfile", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("username", usernameText)
            editor.putString("password", passwordText)
            editor.putString("firstName", firstNameText)
            editor.putString("lastName", lastNameText)
            editor.putString("email", usernameText) // Treating Gmail as email
            editor.putString("bio", "No bio provided")
            editor.apply()


            val intent = Intent(this, FillLogin::class.java).apply {
                putExtra("username", usernameText)
                putExtra("firstname", firstNameText)
                putExtra("lastname", lastNameText)
                putExtra("password", passwordText)
                putExtra("cpass", confirmPasswordText)
            }
            startActivity(intent)
        }
    }
}
