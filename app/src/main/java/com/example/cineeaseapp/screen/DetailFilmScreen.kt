package com.example.cineeaseapp.screen

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cineeaseapp.R
import com.example.cineeaseapp.data.Film

class DetailFilmScreen : AppCompatActivity() {

    companion object {
        const val EXTRA_FILM = "extra_film"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_film_screen)

        val film = intent.getParcelableExtra<Film>(EXTRA_FILM)

        val imgPoster: ImageView = findViewById(R.id.img_poster)
        val tvTitle: TextView = findViewById(R.id.tv_title)
        val tvSynopsis: TextView = findViewById(R.id.tv_synopsis)
        val tvRating: TextView = findViewById(R.id.tv_rating)
        val tvYear: TextView = findViewById(R.id.tv_year)
        val tvGenre: TextView = findViewById(R.id.tv_genre)
        val tvPrice: TextView = findViewById(R.id.tv_price)
        val btnBuyTicket: Button = findViewById(R.id.btn_buy_ticket)

        imgPoster.setImageResource(film?.poster ?: 0)
        tvTitle.text = film?.judul
        tvSynopsis.text = film?.sinopsis
        tvRating.text = "Rating \n${film?.rating ?: "N/A"}"
        tvYear.text = "Year \n${film?.tahun ?: "N/A"}"
        tvGenre.text = "Genre \n${film?.genre ?: "N/A"}"
        tvPrice.text = "Harga ticket: Rp. ${film?.harga ?: "N/A"}"

        btnBuyTicket.setOnClickListener {
            val intent = Intent(this, SeatSelectorScreen::class.java)
            intent.putExtra(EXTRA_FILM, film) // Use the directly defined EXTRA_FILM constant
            startActivity(intent)
        }
    }
}
