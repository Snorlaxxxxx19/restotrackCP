package com.res.restotrack

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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
            it.getStringExtra("username").let { username -> Username.setText(username) }
            it.getStringExtra("firstname").let { firstname -> Firstname.setText(firstname) }
            it.getStringExtra("lastname").let { lastname -> Lastname.setText(lastname) }
            it.getStringExtra("password").let { password -> Password.setText(password) }
            it.getStringExtra("cpass").let { cpass -> Confirmpassword.setText(cpass) }
        }

        submit.setOnClickListener {
            if (Username.text.isNullOrEmpty() || Firstname.text.isNullOrEmpty()
                || Lastname.text.isNullOrEmpty() || Password.text.isNullOrEmpty()
                || Confirmpassword.text.isNullOrEmpty()) {
                Toast.makeText(this, "Please fill out all information", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            val intent = Intent(this, FillLogin::class.java).apply {
                putExtra("username", Username.text.toString())
                putExtra("firstname", Firstname.text.toString())
                putExtra("lastname", Lastname.text.toString())
                putExtra("password", Password.text.toString())
                putExtra("cpass", Confirmpassword.text.toString())
            }
            startActivity(intent)
        }
    }
}