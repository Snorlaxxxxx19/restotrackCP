package com.res.restotrack

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.gridlayout.widget.GridLayout

class TableSelection : Activity() {
    private lateinit var tableGrid: GridLayout
    private var selectedTable = -1
    private lateinit var proceedButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table_selection)

        tableGrid = findViewById(R.id.tableGrid)
        proceedButton = findViewById(R.id.proceedButton)


        tableGrid.removeAllViews()

        createTableCards()

        proceedButton.setOnClickListener {
            if (selectedTable != -1) {
                Toast.makeText(this, "Selected Table: $selectedTable", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please select a table first!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createTableCards() {
        val displayMetrics = resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val cardSize = (screenWidth / 3) - 48

        for (i in 1..9) {
            val card = CardView(this).apply {
                layoutParams = GridLayout.LayoutParams().apply {
                    width = 285
                    height = cardSize
                    columnSpec = GridLayout.spec((i - 1) % 3)
                    rowSpec = GridLayout.spec((i - 1) / 3)
                    setMargins(16, 16, 16, 16)
                }
                radius = 20f
                cardElevation = 10f
                setCardBackgroundColor(Color.WHITE)
                tag = i
                isClickable = true
                isFocusable = true
            }

            val tableText = TextView(this).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                text = "Table $i"
                gravity = Gravity.CENTER
                textSize = 18f
                setTextColor(Color.BLACK)
            }

            card.addView(tableText)
            card.setOnClickListener {
                handleTableSelection(card, i)
            }

            tableGrid.addView(card)
        }
    }

    private fun handleTableSelection(selectedCard: CardView, tableNumber: Int) {
        for (i in 0 until tableGrid.childCount) {
            val card = tableGrid.getChildAt(i) as CardView
            card.setCardBackgroundColor(Color.WHITE)
        }
        selectedCard.setCardBackgroundColor(Color.parseColor("#D84040"))
        selectedTable = tableNumber
    }
}