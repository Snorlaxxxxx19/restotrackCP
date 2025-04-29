package com.res.restotrack.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.res.restotrack.application.TimeSlotApp
import com.res.restotrack.data.TimeSlot

class TimeSlotViewModel : ViewModel() {
    private val _timeSlots = MutableLiveData<List<TimeSlot>>()
    val timeSlots: LiveData<List<TimeSlot>> = _timeSlots

    init {
        _timeSlots.value = TimeSlotApp.timeSlots
    }

    fun addTimeSlot(startTime: String, endTime: String) {
        val current = _timeSlots.value?.toMutableList() ?: mutableListOf()
        val newId = if (current.isEmpty()) 1 else current.last().id + 1
        current.add(TimeSlot(newId, startTime, endTime))
        _timeSlots.value = current
        TimeSlotApp.timeSlots = current
    }

    fun removeTimeSlot(position: Int) {
        val current = _timeSlots.value?.toMutableList() ?: return
        if (position in current.indices) {
            current.removeAt(position)
            _timeSlots.value = current
            TimeSlotApp.timeSlots = current
        } else {
            Log.e("TimeSlotViewModel", "Invalid position: $position")
        }
    }
}
