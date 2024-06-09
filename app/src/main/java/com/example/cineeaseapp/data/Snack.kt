package com.example.cineeaseapp.data

import java.io.Serializable

data class Snack(
    var name: String,
    val description: String,
    var image: Int,
    var price: Int,

) : Serializable