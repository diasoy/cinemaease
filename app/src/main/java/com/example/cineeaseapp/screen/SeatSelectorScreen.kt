package com.example.cineeaseapp.screen

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cineeaseapp.R
import com.example.cineeaseapp.data.DatabaseHandlerFilm
import com.example.cineeaseapp.data.Film
import com.example.cineeaseapp.screen.DetailFilmScreen.Companion.EXTRA_FILM

class SeatSelectorScreen : AppCompatActivity() {

    private lateinit var film: Film
    private var selectedSeats: Int = 0
    private val selectedSeatSet = mutableSetOf<Button>()
    private lateinit var tvTotalPrice: TextView // Move the declaration to the class scope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seat_selector)

        film = intent.getParcelableExtra(EXTRA_FILM)!!

        tvTotalPrice = findViewById(R.id.tv_total_price) // Initialize tvTotalPrice here
        val btnContinue: Button = findViewById(R.id.btn_continue)

        createSeatButtons(R.id.seat_grid_left, 'A', 1)
        createSeatButtons(R.id.seat_grid_right, 'A', 5)

        btnContinue.setOnClickListener {
            saveTicketOrderToDatabase()
        }
    }

    private fun createSeatButtons(gridId: Int, startingRow: Char, startingCol: Int) {
        val seatGrid: GridLayout = findViewById(gridId)
        val rows = seatGrid.rowCount
        val cols = seatGrid.columnCount
        val seatSize = resources.getDimensionPixelSize(R.dimen.seat_button_size)
        val seatMargin = resources.getDimensionPixelSize(R.dimen.seat_button_margin)

        for (i in 0 until rows) {
            for (j in 0 until cols) {
                val seatButton = Button(this).apply {
                    val seatText = "${(startingRow.toInt() + i).toChar()}${startingCol + j}"
                    text = seatText
                    setBackgroundColor(Color.WHITE)
                    textSize = 10f
                    setOnClickListener {
                        toggleSeatSelection(this)
                    }
                    layoutParams = GridLayout.LayoutParams().apply {
                        width = seatSize
                        height = seatSize
                        setMargins(seatMargin, seatMargin, seatMargin, seatMargin)
                    }
                }
                seatGrid.addView(seatButton)
            }
        }
    }

    private fun toggleSeatSelection(seatButton: Button) {
        if (selectedSeatSet.contains(seatButton)) {
            seatButton.setBackgroundColor(Color.WHITE)
            selectedSeatSet.remove(seatButton)
            selectedSeats--
        } else {
            seatButton.setBackgroundColor(Color.YELLOW)
            selectedSeatSet.add(seatButton)
            selectedSeats++
        }
        updateTotalPrice()
    }


    private fun updateTotalPrice() {
        val totalPrice = selectedSeats * film.harga
        tvTotalPrice.text = "Total Price: Rp. $totalPrice"
    }

    private fun saveTicketOrderToDatabase() {
        val db = DatabaseHandlerFilm(this)
        val selectedSeatString = selectedSeatSet.joinToString(", ") { it.text.toString() }
        val totalPrice = selectedSeats * film.harga
        val orderId = db.addOrderTicket(film.judul, film.poster.toString(), selectedSeatString, totalPrice) // Convert film.poster to String
        if (orderId != -1L) {
            Toast.makeText(this, "Ticket purchased successfully!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, OrderScreen::class.java))
            finish()
        } else {
            Toast.makeText(this, "Failed to purchase ticket!", Toast.LENGTH_SHORT).show()
        }
    }
}
