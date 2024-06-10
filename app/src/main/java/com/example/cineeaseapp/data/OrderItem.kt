package com.example.cineeaseapp.data

sealed class OrderItem {
    data class TicketOrder(val order: OrderTicket) : OrderItem()
    data class SnackOrder(val order: OrderSnack) : OrderItem()
}