package com.example.cineeaseapp.data


import java.io.Serializable

// OrderTicket.kt
data class OrderTicket(
    val movieTitle: String,
    val movieImage: String,
    val seat: String,
    val ticketPrice: Int,
    val transactionNumber: String,
    val transactionTime: String
) : Serializable


data class OrderSnack(
    val snackName: String,
    val snackImage: String,
    val quantity: Int,
    val snackPrice: Int,
    val transactionNumber: String,
    val transactionTime: String
) : Serializable
