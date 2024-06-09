package com.example.cineeaseapp.data

import android.content.Context
import com.example.cineeaseapp.R

class FilmData(context: Context) {
    private val filmNames = arrayOf(
        context.getString(R.string.film_name1),
        context.getString(R.string.film_name2),
        context.getString(R.string.film_name3),
        context.getString(R.string.film_name4),
        context.getString(R.string.film_name5)
    )
    private val filmDetails = arrayOf(
        context.getString(R.string.film_description1),
        context.getString(R.string.film_description2),
        context.getString(R.string.film_description3),
        context.getString(R.string.film_description4),
        context.getString(R.string.film_description5)
    )
    private val filmPosters = intArrayOf(
        R.drawable.ant_man,
        R.drawable.black_panther,
        R.drawable.captain_marvel,
        R.drawable.doctor_strange,
        R.drawable.end_game
    )
    private val filmRatings = arrayOf(
        "4.9 / 5",
        "4.8 / 5",
        "4.7 / 5",
        "4.6 / 5",
        "4.5 / 5"
    )
    private val filmReleaseYears = intArrayOf(
        2015,
        2018,
        2019,
        2019,
        2019
    )
    private val filmGenres = arrayOf(
        "Action",
        "Action",
        "Action",
        "Action",
        "Action"
    )
    private val filmPrices = intArrayOf(
        40000,
        40000,
        35000,
        35000,
        40000
    )

    val listFilm: ArrayList<Film>
        get() {
            val list = arrayListOf<Film>()
            for (position in filmNames.indices) {
                val film = Film(
                    judul = filmNames[position],
                    sinopsis = filmDetails[position],
                    poster = filmPosters[position],
                    rating = filmRatings[position],
                    tahun = filmReleaseYears[position],
                    genre = filmGenres[position],
                    harga = filmPrices[position].toString()
                )
                list.add(film)
            }
            return list
        }
}