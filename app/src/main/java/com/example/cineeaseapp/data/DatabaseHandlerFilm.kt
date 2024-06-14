package com.example.cineeaseapp.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandlerFilm(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 3 // Update to version 3
        private const val DATABASE_NAME = "CineEaseDB"
        private const val TABLE_ORDERS = "order_ticket"
        private const val KEY_ID = "id"
        private const val KEY_MOVIE_TITLE = "movie_title"
        private const val KEY_MOVIE_IMAGE = "movie_image"
        private const val KEY_SEAT = "seat"
        private const val KEY_TICKET_PRICE = "ticket_price"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createOrdersTable = ("CREATE TABLE $TABLE_ORDERS($KEY_ID INTEGER PRIMARY KEY,$KEY_MOVIE_TITLE TEXT,$KEY_MOVIE_IMAGE TEXT,$KEY_SEAT TEXT,$KEY_TICKET_PRICE INTEGER)")
        db.execSQL(createOrdersTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if(oldVersion < 2){
            db.execSQL("ALTER TABLE $TABLE_ORDERS ADD COLUMN $KEY_MOVIE_IMAGE TEXT")
        }
        // Handle other upgrades here if needed
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle downgrade logic here
        if(newVersion < oldVersion){
            db.execSQL("DROP TABLE IF EXISTS $TABLE_ORDERS")
            onCreate(db)
        }
    }

    fun addOrderTicket(movieTitle: String, movieImage: String, seat: String, ticketPrice: Int): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_MOVIE_TITLE, movieTitle)
        values.put(KEY_MOVIE_IMAGE, movieImage)
        values.put(KEY_SEAT, seat)
        values.put(KEY_TICKET_PRICE, ticketPrice)
        val id = db.insert(TABLE_ORDERS, null, values)
        db.close()
        return id
    }

    fun getAllOrdersTicket(): ArrayList<OrderTicket> {
        val orderList = ArrayList<OrderTicket>()
        val selectQuery = "SELECT  * FROM $TABLE_ORDERS"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val order = OrderTicket(
                    id = cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                    movieTitle = cursor.getString(cursor.getColumnIndex(KEY_MOVIE_TITLE)) ?: "",
                    movieImage = cursor.getString(cursor.getColumnIndex(KEY_MOVIE_IMAGE)) ?: "",
                    seat = cursor.getString(cursor.getColumnIndex(KEY_SEAT)) ?: "",
                    ticketPrice = cursor.getInt(cursor.getColumnIndex(KEY_TICKET_PRICE)),
                )
                orderList.add(order)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return orderList
    }

    fun deleteAllOrdersTicket() {
        val db = this.writableDatabase
        db.delete(TABLE_ORDERS, null, null)
        db.close()
    }
}
