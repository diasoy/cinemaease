package com.example.cineeaseapp.data

import android.os.Parcel
import android.os.Parcelable

data class Film(
    val judul: String,
    val sinopsis: String,
    val rating: Double,
    val tahun: Int,
    val genre: String,
    val harga: Int,
    val poster: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(judul)
        parcel.writeString(sinopsis)
        parcel.writeDouble(rating)
        parcel.writeInt(tahun)
        parcel.writeString(genre)
        parcel.writeInt(harga)
        parcel.writeInt(poster)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Film> {
        override fun createFromParcel(parcel: Parcel): Film {
            return Film(parcel)
        }

        override fun newArray(size: Int): Array<Film?> {
            return arrayOfNulls(size)
        }
    }
}
