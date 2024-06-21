package com.example.cineeaseapp.data

import java.io.Serializable

sealed class OrderItem : Serializable {
    data class TicketOrder(val order: OrderTicket) : OrderItem()
    data class SnackOrder(val order: OrderSnack) : OrderItem()
}
