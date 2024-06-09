package com.example.cineeaseapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cineeaseapp.databinding.ItemBannerBinding

class BannerAdapter(private val banners: List<Int>) : RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {

    inner class BannerViewHolder(private val binding: ItemBannerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(banner: Int) {
            binding.imgBanner.setImageResource(banner)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val binding = ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BannerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return banners.size
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        holder.bind(banners[position])
    }
}