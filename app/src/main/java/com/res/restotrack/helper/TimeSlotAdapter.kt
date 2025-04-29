package com.res.restotrack.helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.res.restotrack.R
import com.res.restotrack.data.TimeSlot

class TimeSlotAdapter(
    private var timeSlots: List<TimeSlot>,
    private val onItemClick: (TimeSlot) -> Unit,
    private val onLongClick: (Int) -> Unit
) : RecyclerView.Adapter<TimeSlotAdapter.TimeSlotViewHolder>() {

    inner class TimeSlotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val timeSlotText: TextView = itemView.findViewById(R.id.timeSlotText)

        init {

            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(timeSlots[position])
                }
            }

            itemView.setOnLongClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onLongClick(position)
                }
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeSlotViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_time_slot, parent, false)
        return TimeSlotViewHolder(view)
    }

    override fun onBindViewHolder(holder: TimeSlotViewHolder, position: Int) {
        val timeSlot = timeSlots[position]
        holder.timeSlotText.text = "${timeSlot.startTime} - ${timeSlot.endTime}"
    }

    override fun getItemCount(): Int = timeSlots.size

    fun updateTimeSlots(newTimeSlots: List<TimeSlot>) {
        val diffCallback = TimeSlotDiffCallback(timeSlots, newTimeSlots)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        timeSlots = newTimeSlots
        diffResult.dispatchUpdatesTo(this)
    }

    fun removeItemAt(position: Int) {
        if (position in timeSlots.indices) {
            val newList = timeSlots.toMutableList().apply { removeAt(position) }
            updateTimeSlots(newList)
        }
    }
}


class TimeSlotDiffCallback(
    private val oldList: List<TimeSlot>,
    private val newList: List<TimeSlot>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
