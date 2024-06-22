// LaporanAdapter.kt
package com.example.cineeaseapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cineeaseapp.R
import com.example.cineeaseapp.data.Laporan

class LaporanAdapter(private var laporanList: List<Laporan>) : RecyclerView.Adapter<LaporanAdapter.LaporanViewHolder>() {

    class LaporanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        val textViewTotal: TextView = itemView.findViewById(R.id.textViewTotal)

        fun bind(laporan: Laporan) {
            textViewTitle.text = laporan.title
            textViewTotal.text = "Rp ${laporan.total}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaporanViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_row_laporan, parent, false)
        return LaporanViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LaporanViewHolder, position: Int) {
        val currentItem = laporanList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount() = laporanList.size

    fun updateData(newLaporanList: List<Laporan>) {
        laporanList = newLaporanList
        notifyDataSetChanged()
    }
}