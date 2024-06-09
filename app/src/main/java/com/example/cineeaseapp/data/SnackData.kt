package com.example.cineeaseapp.data

import android.content.Context
import com.example.cineeaseapp.R

class SnackData(context: Context) {
    private val snackNames = arrayOf(
        context.getString(R.string.snack_name1),
        context.getString(R.string.snack_name2),
        context.getString(R.string.snack_name3),
        context.getString(R.string.snack_name4),
    )
    private val snackDetails = arrayOf(
        context.getString(R.string.snack_description1),
        context.getString(R.string.snack_description2),
        context.getString(R.string.snack_description3),
        context.getString(R.string.snack_description4),
    )
    private val snackImages = intArrayOf(
        R.drawable.bucket_popcorn,
        R.drawable.box_popcorn,
        R.drawable.coca_cola,
        R.drawable.ice_chocolate,
    )
    private val snackPrices = intArrayOf(
        20000,
        25000,
        30000,
        15000,
        10000
    )

    val listSnack: ArrayList<Snack>
        get() {
            val list = arrayListOf<Snack>()
            for (position in snackNames.indices) {
                val snack = Snack(
                    snackNames[position],
                    snackDetails[position],
                    snackImages[position],
                    snackPrices[position]
                )
                list.add(snack)
            }
            return list
        }
}