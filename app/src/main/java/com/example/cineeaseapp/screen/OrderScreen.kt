package com.example.cineeaseapp.screen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cineeaseapp.R
import com.example.cineeaseapp.adapter.OrderAdapter
import com.example.cineeaseapp.data.DatabaseHandlerFilm
import com.example.cineeaseapp.data.DatabaseHandlerSnack
import com.example.cineeaseapp.data.OrderItem

class OrderScreen : Fragment() {

    private lateinit var orderAdapter: OrderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_order_screen, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.order_recycler_view)

        val dbFilm = DatabaseHandlerFilm(requireContext())
        val dbSnack = DatabaseHandlerSnack(requireContext())
        val orderListTicket = dbFilm.getAllOrdersTicket().map { OrderItem.TicketOrder(it) }
        val orderListSnack = dbSnack.getAllOrdersSnack().map { OrderItem.SnackOrder(it) }
        val orderList = orderListTicket + orderListSnack

        orderAdapter = OrderAdapter(orderList) { order ->
            val intent = Intent(context, OrderDetailScreen::class.java)
            intent.putExtra(OrderDetailScreen.EXTRA_ORDER, order)
            startActivity(intent)
        }

        recyclerView.adapter = orderAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

//        dbSnack.deleteAllOrdersSnack()
//        dbFilm.deleteAllOrdersTicket()

        return view
    }
}
