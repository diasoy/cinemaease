package com.example.cineeaseapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Film(
    var judul: String,
    var sinopsis: String,
    var poster: Int,
    var rating: String,
    var tahun: Int,
    var genre: String,
    val harga: String
) : Parcelable {
}