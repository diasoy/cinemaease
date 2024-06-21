package com.example.cineeaseapp.screen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cineeaseapp.R
import com.example.cineeaseapp.data.DatabaseHandlerSnack
import com.example.cineeaseapp.data.OrderSnack
import com.example.cineeaseapp.data.Snack
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DetailSnackScreen : AppCompatActivity() {

    private lateinit var snack: Snack
    private lateinit var db: DatabaseHandlerSnack

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_snack_screen)

        db = DatabaseHandlerSnack(this)

        snack = intent.getSerializableExtra("SNACK") as Snack

        val snackImageView = findViewById<ImageView>(R.id.snack_image)
        val snackNameTextView = findViewById<TextView>(R.id.snack_name)
        val snackDescriptionTextView = findViewById<TextView>(R.id.snack_description)
        val snackPriceTextView = findViewById<TextView>(R.id.snack_price)
        val quantityPicker = findViewById<NumberPicker>(R.id.quantity_picker)
        val totalTextView = findViewById<TextView>(R.id.total_price)
        val buyButton = findViewById<Button>(R.id.buy_button)

        snackImageView.setImageResource(snack.image)
        snackNameTextView.text = snack.name
        snackDescriptionTextView.text = snack.description
        snackPriceTextView.text = getString(R.string.price_format, snack.price)

        quantityPicker.minValue = 1
        quantityPicker.maxValue = 10
        quantityPicker.setOnValueChangedListener { _, _, newVal ->
            val total = newVal * snack.price
            totalTextView.text = getString(R.string.price_format, total)
        }

        val initialTotal = snack.price
        totalTextView.text = getString(R.string.price_format, initialTotal)

        buyButton.setOnClickListener {
            val quantity = quantityPicker.value
            val total = quantity * snack.price
            saveSnackOrderToDatabase(quantity, total)
        }
    }

    private fun saveSnackOrderToDatabase(quantity: Int, total: Int) {
        val transactionNumber = (1000000000..9999999999).random().toString()

        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val transactionTime = currentDateTime.format(formatter)

        val orderSnack = OrderSnack(
            quantity = quantity,
            transactionNumber = transactionNumber,
            snackImage = snack.image.toString(),
            snackPrice = snack.price,
            snackName = snack.name,
            transactionTime = transactionTime
        )

        try {
            val result = db.addOrderSnack(
                orderSnack.quantity,
                orderSnack.transactionNumber,
                orderSnack.snackImage,
                orderSnack.snackPrice,
                orderSnack.snackName,
                orderSnack.transactionTime
            )

            if (result != -1L) {
                Toast.makeText(this, "Snack purchased successfully!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, OrderScreen::class.java))
                finish()
            } else {
                Log.e("DetailSnackScreen", "Failed to insert snack order into database")
                Toast.makeText(this, "Failed to purchase snack!", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Log.e("DetailSnackScreen", "Error saving snack order: ${e.message}")
            Toast.makeText(this, "An error occurred. Please try again.", Toast.LENGTH_SHORT).show()
        }
    }
}
