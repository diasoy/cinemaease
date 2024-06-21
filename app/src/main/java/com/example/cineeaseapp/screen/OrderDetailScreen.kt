package com.example.cineeaseapp.screen

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.cineeaseapp.data.OrderItem
import com.example.cineeaseapp.data.OrderSnack
import com.example.cineeaseapp.data.OrderTicket
import com.example.cineeaseapp.databinding.ActivityOrderDetailScreenBinding
import java.text.SimpleDateFormat
import java.util.*

class OrderDetailScreen : AppCompatActivity() {

    private lateinit var binding: ActivityOrderDetailScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val order = intent.getSerializableExtra(EXTRA_ORDER) as? OrderItem

        order?.let {
            when (it) {
                is OrderItem.TicketOrder -> {
                    showTicketOrderDetails(it.order)
                }
                is OrderItem.SnackOrder -> {
                    showSnackOrderDetails(it.order)
                }
            }
        }

        binding.buttonBack.setOnClickListener {
            finish()
        }
    }

    private fun showTicketOrderDetails(order: OrderTicket) {
        binding.apply {
            textViewTitle.text = "Ticket Order Details"
            textViewMovieTitle.text = "Movie Title:"
            textViewMovieTitleValue.text = order.movieTitle
            textViewSeat.text = "Seats:"
            textViewSeatValue.text = order.seat
            textViewPrice.text = "Total Price:"
            textViewPriceValue.text = "Rp. ${order.ticketPrice}"
            textViewTransactionNumber.visibility = View.VISIBLE
            textViewTransactionNumberValue.visibility = View.VISIBLE
            textViewTransactionTime.visibility = View.VISIBLE
            textViewTransactionTimeValue.visibility = View.VISIBLE
            textViewTransactionNumberValue.text = order.transactionNumber
            textViewTransactionTimeValue.text = formatDate(order.transactionTime)
        }
    }

    private fun showSnackOrderDetails(order: OrderSnack) {
        binding.apply {
            textViewTitle.text = "Snack Order Details"
            textViewMovieTitle.text = "Snack Name:"
            textViewMovieTitleValue.text = order.snackName
            textViewSeat.text = "Quantity:"
            textViewSeatValue.text = order.quantity.toString()
            textViewPrice.text = "Total Price:"
            textViewPriceValue.text = "Rp. ${order.snackPrice}"
            textViewTransactionNumber.visibility = View.VISIBLE
            textViewTransactionNumberValue.visibility = View.VISIBLE
            textViewTransactionTime.visibility = View.VISIBLE
            textViewTransactionTimeValue.visibility = View.VISIBLE
            textViewTransactionNumberValue.text = order.transactionNumber
            textViewTransactionTimeValue.text = formatDate(order.transactionTime)
        }
    }

    private fun formatDate(timestamp: String): String {
        try {
            // Ubah format tanggal sesuai dengan format yang sesuai dengan `timestamp`
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val date = sdf.parse(timestamp)
            val outputFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
            return outputFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    companion object {
        const val EXTRA_ORDER = "extra_order"
    }
}
