package com.example.cineeaseapp.screen

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cineeaseapp.R
import com.example.cineeaseapp.data.Film

class SeatSelectorScreen : AppCompatActivity() {

    companion object {
        const val EXTRA_FILM = "extra_film"
    }

    private lateinit var tvTotalPrice: TextView
    private var selectedSeats: Int = 0
    private var seatPrice: Int = 0
    private val selectedSeatSet = mutableSetOf<Button>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seat_selector)

        val film = intent.getParcelableExtra<Film>(EXTRA_FILM)
        seatPrice = film?.harga ?: 0

        tvTotalPrice = findViewById(R.id.tv_total_price)
        val btnContinue: Button = findViewById(R.id.btn_continue)

        // Create seat buttons for left grid (A1 - H4)
        createSeatButtons(R.id.seat_grid_left, 'A', 1)

        // Create seat buttons for right grid (A5 - H6)
        createSeatButtons(R.id.seat_grid_right, 'A', 5)

        btnContinue.setOnClickListener {
            // Handle the continue action, e.g., navigate to the next screen or show a confirmation
        }
    }


    private fun createSeatButtons(gridId: Int, startingRow: Char, startingCol: Int) {
        val seatGrid: GridLayout = findViewById(gridId)
        val rows = seatGrid.rowCount
        val cols = seatGrid.columnCount

        for (i in 0 until rows) {
            for (j in 0 until cols) {
                val seatButton = Button(this).apply {
                    val seatText = "${(startingRow.toInt() + i).toChar()}${startingCol + j}"
                    text = seatText
                    setBackgroundColor(Color.WHITE)
                    setOnClickListener {
                        toggleSeatSelection(this)
                    }
                }
                seatGrid.addView(seatButton)
            }
        }
    }


    private fun toggleSeatSelection(seatButton: Button) {
        if (selectedSeatSet.contains(seatButton)) {
            // Deselect the seat
            seatButton.setBackgroundColor(Color.WHITE)
            selectedSeatSet.remove(seatButton)
            selectedSeats--
        } else {
            // Select the seat
            seatButton.setBackgroundColor(Color.YELLOW)
            selectedSeatSet.add(seatButton)
            selectedSeats++
        }
        updateTotalPrice()
    }

    private fun updateTotalPrice() {
        val totalPrice = selectedSeats * seatPrice
        tvTotalPrice.text = "Total Price: Rp. $totalPrice"
    }
}
