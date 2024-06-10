package com.example.cineeaseapp.screen

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cineeaseapp.R
import com.example.cineeaseapp.data.Snack

class DetailSnackScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_snack_screen)

        val snack = intent.getSerializableExtra("SNACK") as Snack

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

        // Set default total price
        val initialTotal = snack.price
        totalTextView.text = getString(R.string.price_format, initialTotal)

        buyButton.setOnClickListener {
            val quantity = quantityPicker.value
            val total = quantity * snack.price
            // TODO: Handle the purchase logic here
            println("Purchased $quantity of ${snack.name} for $total")
        }
    }
}
