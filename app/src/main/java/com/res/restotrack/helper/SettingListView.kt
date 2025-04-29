package com.res.restotrack.helper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.res.restotrack.R
import com.res.restotrack.data.SettingOption


class SettingsListViewAdapter(
    val context: Context,
    val settingsList: List<SettingOption>,
    val onClickDetails: (SettingOption) -> Unit,
    val onClickSetting: (SettingOption) -> Unit,
    val onLongPressDelete: (position: Int) -> Unit
) : BaseAdapter() {

    override fun getView(position: Int, contentView: View?, parent: ViewGroup?): View {
        val view = contentView ?: LayoutInflater.from(context).inflate(
            R.layout.item_list_of_schools,
            parent,
            false
        )

        val iconImageView = view.findViewById<ImageView>(R.id.imageview_logo)
        val titleTextView = view.findViewById<TextView>(R.id.textview_code)
        val subtitleTextView = view.findViewById<TextView>(R.id.textview_desc)
        val additionalInfoTextView = view.findViewById<TextView>(R.id.textview_schedule)
        val detailsTextView = view.findViewById<TextView>(R.id.textview_showmore)

        val setting = settingsList[position]

        iconImageView.setImageResource(setting.icon)
        titleTextView.text = setting.title
        subtitleTextView.text = setting.subtitle
        additionalInfoTextView.text = setting.additionalInfo

        detailsTextView.setOnClickListener {
            onClickDetails(setting)
        }

        view.setOnClickListener {
            onClickSetting(setting)
        }

        view.setOnLongClickListener {
            onLongPressDelete(position)
            true
        }

        return view
    }

    override fun getCount(): Int = settingsList.size

    override fun getItem(position: Int): Any = settingsList[position]

    override fun getItemId(position: Int): Long = position.toLong()
}