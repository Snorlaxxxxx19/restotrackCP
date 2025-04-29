package com.res.restotrack

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast

class numberpicker : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_numberpicker)

        val spinner: Spinner = findViewById(R.id.peopleSpinner)
        val prcdButton = findViewById<Button>(R.id.btnProceed)

        prcdButton.setOnClickListener{
            val intent = Intent(this, TableSelection::class.java)
            startActivity(intent)
        }

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.people_options,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selected = parent.getItemAtPosition(position).toString()
                Toast.makeText(this@numberpicker, "Selected: $selected people", Toast.LENGTH_SHORT).show()
            }




            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }
}
