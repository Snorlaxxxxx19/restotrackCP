package com.res.restotrack.application

import android.app.Application
import com.res.restotrack.data.TimeSlot

class TimeSlotApp : Application() {
    companion object {
        var timeSlots: MutableList<TimeSlot> = mutableListOf()
    }

    override fun onCreate() {
        super.onCreate()

        timeSlots.addAll(listOf(
            TimeSlot(1, "10:00 AM", "12:00 PM"),
            TimeSlot(2, "12:30 PM", "2:30 PM"),
            TimeSlot(3, "3:00 PM", "5:00 PM")
        ))
    }
}