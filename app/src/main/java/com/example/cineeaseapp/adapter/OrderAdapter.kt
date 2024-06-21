package com.example.cineeaseapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cineeaseapp.R
import com.example.cineeaseapp.data.OrderItem
import com.example.cineeaseapp.data.OrderSnack
import com.example.cineeaseapp.data.OrderTicket

class OrderAdapter(
    private val orderList: List<OrderItem>,
    private val onItemClick: (OrderItem) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_TICKET = 1
        private const val VIEW_TYPE_SNACK = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_TICKET) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_row_order_film, parent, false)
            TicketViewHolder(view, onItemClick)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_row_order_snack, parent, false)
            SnackViewHolder(view, onItemClick)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val orderItem = orderList[position]) {
            is OrderItem.TicketOrder -> (holder as TicketViewHolder).bind(orderItem.order)
            is OrderItem.SnackOrder -> (holder as SnackViewHolder).bind(orderItem.order)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (orderList[position]) {
            is OrderItem.TicketOrder -> VIEW_TYPE_TICKET
            is OrderItem.SnackOrder -> VIEW_TYPE_SNACK
        }
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    inner class TicketViewHolder(
        itemView: View,
        private val onItemClick: (OrderItem) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val ivFilmImage: ImageView = itemView.findViewById(R.id.iv_film_image)
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        private val tvPrice: TextView = itemView.findViewById(R.id.tv_price)

        fun bind(order: OrderTicket) {
            val resId = itemView.context.resources.getIdentifier(order.movieImage, "drawable", itemView.context.packageName)
            if (resId != 0) {
                ivFilmImage.setImageResource(resId)
            } else {
                ivFilmImage.setImageResource(R.drawable.ant_man) // Replace with your default image resource
            }
            tvTitle.text = order.movieTitle
            tvPrice.text = "Total Seat: ${order.seat}, Total Price: Rp. ${order.ticketPrice}"

            itemView.setOnClickListener {
                onItemClick(OrderItem.TicketOrder(order))
            }
        }
    }

    inner class SnackViewHolder(
        itemView: View,
        private val onItemClick: (OrderItem) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val ivSnackImage: ImageView = itemView.findViewById(R.id.iv_snack_image)
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        private val tvPrice: TextView = itemView.findViewById(R.id.tv_price)

        fun bind(order: OrderSnack) {
            val resId = itemView.context.resources.getIdentifier(order.snackImage, "drawable", itemView.context.packageName)
            if (resId != 0) {
                ivSnackImage.setImageResource(resId)
            } else {
                ivSnackImage.setImageResource(R.drawable.bucket_popcorn) // Replace with your default image resource
            }
            tvTitle.text = order.snackName
            tvPrice.text = "Quantity: ${order.quantity}, Total Price: Rp. ${order.snackPrice}"

            itemView.setOnClickListener {
                onItemClick(OrderItem.SnackOrder(order))
            }
        }
    }


}
