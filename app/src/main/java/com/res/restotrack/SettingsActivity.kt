package com.res.restotrack

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import com.res.restotrack.data.SettingOption
import com.res.restotrack.helper.SettingsListViewAdapter

class SettingsActivity : Activity() {

    lateinit var settingsOptions: MutableList<SettingOption>
    lateinit var settingsAdapter: SettingsListViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_item_setting)

        val backIcon: ImageView = findViewById(R.id.backIcon)
        backIcon.setOnClickListener {
            val intent = Intent(this, HomePage::class.java)
            startActivity(intent)
            finish()
        }

        val settingsListView = findViewById<ListView>(R.id.settings_list_view)

        settingsOptions = mutableListOf(
            SettingOption(R.drawable.person, "Profile", "Manage personal information", "and settings", "Here, you can update your personal details, such as your name, email, and profile picture."),
            SettingOption(R.drawable.ic_security, "Security", "Manage authentication", "and data protection", "Enhance your account protection by managing security settings."),
            SettingOption(R.drawable.ic_privacy, "Privacy", "Control your personal data", "and permissions", "Manage your privacy settings to control how your data is used and shared."),
            SettingOption(R.drawable.coding, "About The Developers", "Meet the creators", "behind this app", "Discover the developers of MemoryLocker"),
            SettingOption(R.drawable.logout, "Logout", "Sign out of the app", "", "Log out from the current session.")
        )

        settingsAdapter = SettingsListViewAdapter(
            this,
            settingsOptions,
            onClickDetails = { setting ->
                showDetailsDialog(setting)
            },
            onClickSetting = { setting ->
                when (setting.title) {
                    "Profile" -> {
                        val intent = Intent(this, EditProfileActivity::class.java)
                        startActivity(intent)
                    }
                    "Security" -> {
                        showSimpleAlert("Security", "This is where you manage your security settings.")
                    }
                    "Privacy" -> {
                        showSimpleAlert("Privacy", "This is where you manage your privacy settings.")
                    }
                    "About The Developers" -> {
                        val intent = Intent(this, DeveloperPage::class.java)
                        startActivity(intent)
                    }
                    "Logout" -> {
                        showLogoutConfirmation()
                    }
                    else -> {
                        // Optional: handle unexpected clicks
                    }
                }
            },
            onLongPressDelete = { _ -> }
        )
        settingsListView.adapter = settingsAdapter
    }

    private fun showDetailsDialog(setting: SettingOption) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(setting.title)
        builder.setMessage(setting.details)
        builder.setPositiveButton("Okay", null)
        builder.show()
    }

    private fun showSimpleAlert(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Okay", null)
            .show()
    }

    private fun showLogoutConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes") { _, _ ->
                logout()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun logout() {

        val sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()


        val intent = Intent(this, FirstLogin::class.java)
        startActivity(intent)
        finish()
    }
}
