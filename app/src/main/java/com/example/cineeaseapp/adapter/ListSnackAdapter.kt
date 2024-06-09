package com.example.cineeaseapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cineeaseapp.R
import com.example.cineeaseapp.data.Snack
import com.example.cineeaseapp.screen.DetailSnackScreen

class ListSnackAdapter(private val listSnack: ArrayList<Snack>) : RecyclerView.Adapter<ListSnackAdapter.SnackViewHolder>() {

    inner class SnackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgPhoto: ImageView = itemView.findViewById(R.id.snack_image)
        var tvName: TextView = itemView.findViewById(R.id.snack_name)
        var tvPrice: TextView = itemView.findViewById(R.id.snack_price)
        var btnBuy: Button = itemView.findViewById(R.id.buy_button)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): SnackViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_snack, viewGroup, false)
        return SnackViewHolder(view)
    }

    override fun onBindViewHolder(holder: SnackViewHolder, position: Int) {
        val snack = listSnack[position]
        holder.imgPhoto.setImageResource(snack.image)
        holder.tvName.text = snack.name
        holder.tvPrice.text = snack.price.toString()

        holder.btnBuy.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailSnackScreen::class.java)
            intent.putExtra("SNACK", snack)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listSnack.size
    }
}
