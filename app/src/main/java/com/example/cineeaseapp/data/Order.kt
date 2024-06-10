package com.example.cineeaseapp.data

data class OrderTicket(
    val id: Int,
    val movieTitle: String,
    val movieImage: String,
    val seat: String,
    val ticketPrice: Int,
)

data class OrderSnack(
    val id: Comparable<*>,
    val snackName: String,
    val snackImage: String,
    val quantity: Int,
    val snackPrice: Int
)