package com.res.restotrack

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream

class EditProfileActivity : Activity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var profileImage: ImageView
    private val PICK_IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val settingsIcon = findViewById<ImageView>(R.id.settingsIcon)
        settingsIcon.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        sharedPreferences = getSharedPreferences("UserProfile", MODE_PRIVATE)

        val firstName = findViewById<EditText>(R.id.edit_first_name)
        val lastName = findViewById<EditText>(R.id.edit_last_name)
        val username = findViewById<EditText>(R.id.edit_username)
        val bio = findViewById<EditText>(R.id.edit_bio)
        val email = findViewById<EditText>(R.id.edit_email)

        val saveButton = findViewById<Button>(R.id.editProfileSaveButton)
        val discardButton = findViewById<Button>(R.id.editProfileDiscardButton)
        profileImage = findViewById(R.id.profileImage)
        val changeProfileText = findViewById<TextView>(R.id.changeProfileText)

        val receivedFirstName = intent.getStringExtra("firstName") ?: sharedPreferences.getString("firstName", "Unknown")
        val receivedLastName = intent.getStringExtra("lastName") ?: sharedPreferences.getString("lastName", "User")
        val receivedUsername = intent.getStringExtra("username") ?: sharedPreferences.getString("username", "NoUsername")
        val receivedEmail = intent.getStringExtra("email") ?: sharedPreferences.getString("email", "No email")
        val receivedBio = intent.getStringExtra("bio") ?: sharedPreferences.getString("bio", "No bio provided") // ✅ Load bio

        firstName.setText(receivedFirstName)
        lastName.setText(receivedLastName)
        username.setText(receivedUsername)
        email.setText(receivedEmail)
        bio.setText(receivedBio)

        profileImage.setOnClickListener { openGallery() }
        changeProfileText.setOnClickListener { openGallery() }

        saveButton.setOnClickListener {
            val updatedFirstName = firstName.text.toString().trim()
            val updatedLastName = lastName.text.toString().trim()
            val updatedUsername = username.text.toString().trim()
            val updatedEmail = email.text.toString().trim()
            val updatedBio = bio.text.toString().trim()

            if (updatedFirstName.isEmpty() || updatedLastName.isEmpty() || updatedUsername.isEmpty() || updatedEmail.isEmpty() || updatedBio.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                saveUserData(updatedFirstName, updatedLastName, updatedUsername, updatedBio, updatedEmail)
                Toast.makeText(this, "Profile Updated!", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, Profile::class.java)
                startActivity(intent)
                finish()
            }
        }

        discardButton.setOnClickListener {
            Toast.makeText(this, "Changes Discarded", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri: Uri? = data.data
            if (imageUri != null) {
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                profileImage.setImageBitmap(bitmap)
                saveProfileImage(bitmap)
            }
        }
    }

    private fun saveProfileImage(bitmap: Bitmap) {
        val file = File(filesDir, "profile.jpg")
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()

        val editor = sharedPreferences.edit()
        editor.putString("profileImage", file.absolutePath)
        editor.apply()
    }

    private fun saveUserData(firstName: String, lastName: String, username: String, bio: String, email: String) {
        val editor = sharedPreferences.edit()
        editor.putString("firstName", firstName)
        editor.putString("lastName", lastName)
        editor.putString("username", username)
        editor.putString("bio", bio)
        editor.putString("email", email)
        editor.apply()
    }
}
