package com.example.cineeaseapp.adapter

import com.example.cineeaseapp.data.Film
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cineeaseapp.R

class ListFilmAdapter(private val listFilm: ArrayList<Film>) :
    RecyclerView.Adapter<ListFilmAdapter.ListViewHolder>() {

    interface OnItemClickCallback {
        fun onItemClicked(data: Film)
    }

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        private var imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)

        fun bind(film: Film) {
            tvName.text = film.judul
            imgPhoto.setImageResource(film.poster)

            itemView.setOnClickListener { onItemClickCallback?.onItemClicked(film) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_film, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listFilm.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listFilm[position])
    }
}