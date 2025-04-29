package com.res.restotrack

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.res.restotrack.data.TimeSlot
import com.res.restotrack.helper.TimeSlotAdapter
import com.res.restotrack.viewmodel.TimeSlotViewModel

class TimeActivityActivity : AppCompatActivity(), LifecycleOwner {

    private lateinit var viewModel: TimeSlotViewModel
    private lateinit var adapter: TimeSlotAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var addButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_activity)

        recyclerView = findViewById(R.id.recyclerView)
        addButton = findViewById(R.id.addButton)

        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            .create(TimeSlotViewModel::class.java)

        setupRecyclerView()
        setupAddButton()
    }

    private fun setupRecyclerView() {
        adapter = TimeSlotAdapter(
            emptyList(),
            onItemClick = { timeSlot ->
                showReservationConfirmation(timeSlot)
            },
            onLongClick = { position ->
                showDeleteConfirmation(position)
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        viewModel.timeSlots.observe(this) { timeSlots ->
            adapter.updateTimeSlots(timeSlots)
        }
    }

    private fun showReservationConfirmation(timeSlot: TimeSlot) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_time_slot_confirmation, null)

        dialogView.findViewById<TextView>(R.id.timeSlot).text =
            "${timeSlot.startTime} - ${timeSlot.endTime}"

        val alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true)
            .create()

        dialogView.findViewById<Button>(R.id.positiveButton).setOnClickListener {

            val intent = Intent(this, numberpicker::class.java)
            startActivity(intent)
            alertDialog.dismiss()
        }

        dialogView.findViewById<Button>(R.id.negativeButton).setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    private fun setupAddButton() {
        addButton.setOnClickListener {
            addNewTimeSlot()
        }
    }

    private fun addNewTimeSlot() {
        val lastSlot = viewModel.timeSlots.value?.lastOrNull() ?: TimeSlot(0, "8:00 AM", "10:00 AM")

        val (timePart, ampm) = lastSlot.endTime.split(" ")
        val (hours, minutes) = timePart.split(":").map { it.toInt() }

        var newHours = hours
        var newMinutes = minutes + 30
        var newAMPM = ampm

        if (newMinutes >= 60) {
            newHours += 1
            newMinutes -= 60
        }

        if (newHours >= 12) {
            if (newHours > 12) newHours -= 12
            newAMPM = if (ampm == "AM") "PM" else "AM"
        }

        val newStartTime = String.format("%d:%02d %s", newHours, newMinutes, newAMPM)

        var endHours = newHours + 2
        var endMinutes = newMinutes
        var endAMPM = newAMPM

        if (endHours >= 12) {
            if (endHours > 12) endHours -= 12
            endAMPM = if (newAMPM == "AM") "PM" else "AM"
        }

        val newEndTime = String.format("%d:%02d %s", endHours, endMinutes, endAMPM)

        viewModel.addTimeSlot(newStartTime, newEndTime)
        recyclerView.smoothScrollToPosition(adapter.itemCount - 1)
    }

    private fun showDeleteConfirmation(position: Int) {
        AlertDialog.Builder(this)
            .setTitle("Delete Time Slot")
            .setMessage("Are you sure you want to delete this time slot?")
            .setPositiveButton("Delete") { _, _ ->
                viewModel.removeTimeSlot(position)
                adapter.removeItemAt(position) // Remove item from the adapter
                Toast.makeText(this, "Time slot deleted", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
