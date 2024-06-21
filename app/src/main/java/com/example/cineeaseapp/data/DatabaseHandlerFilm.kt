package com.example.cineeaseapp.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandlerFilm(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 4
        private const val DATABASE_NAME = "CineEaseDB"
        private const val TABLE_ORDERS = "order_ticket"
        private const val KEY_ID = "id"
        private const val KEY_MOVIE_TITLE = "movie_title"
        private const val KEY_MOVIE_IMAGE = "movie_image"
        private const val KEY_SEAT = "seat"
        private const val KEY_TICKET_PRICE = "ticket_price"
        private const val KEY_TRANSACTION_NUMBER = "transaction_number"
        private const val KEY_TRANSACTION_TIME = "transaction_time"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createOrdersTable =
            ("CREATE TABLE $TABLE_ORDERS(" +
                    "$KEY_ID INTEGER PRIMARY KEY," +
                    "$KEY_MOVIE_TITLE TEXT," +
                    "$KEY_MOVIE_IMAGE TEXT," +
                    "$KEY_SEAT TEXT," +
                    "$KEY_TICKET_PRICE INTEGER," +
                    "$KEY_TRANSACTION_NUMBER TEXT," +
                    "$KEY_TRANSACTION_TIME TEXT)")
        db.execSQL(createOrdersTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE $TABLE_ORDERS ADD COLUMN $KEY_MOVIE_IMAGE TEXT")
        }
        if (oldVersion < 4) {
            db.execSQL("ALTER TABLE $TABLE_ORDERS ADD COLUMN $KEY_TRANSACTION_NUMBER TEXT")
            db.execSQL("ALTER TABLE $TABLE_ORDERS ADD COLUMN $KEY_TRANSACTION_TIME TEXT")
        }
    }

    fun addOrderTicket(orderTicket: OrderTicket): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(KEY_MOVIE_TITLE, orderTicket.movieTitle)
            put(KEY_MOVIE_IMAGE, orderTicket.movieImage)
            put(KEY_SEAT, orderTicket.seat)
            put(KEY_TICKET_PRICE, orderTicket.ticketPrice)
            put(KEY_TRANSACTION_NUMBER, orderTicket.transactionNumber)
            put(KEY_TRANSACTION_TIME, orderTicket.transactionTime)
        }
        val id = db.insert(TABLE_ORDERS, null, values)
        db.close()
        return id
    }

    fun getAllOrdersTicket(): ArrayList<OrderTicket> {
        val orderList = ArrayList<OrderTicket>()
        val selectQuery = "SELECT * FROM $TABLE_ORDERS"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val order = OrderTicket(
                    movieTitle = cursor.getString(cursor.getColumnIndex(KEY_MOVIE_TITLE)) ?: "",
                    movieImage = cursor.getString(cursor.getColumnIndex(KEY_MOVIE_IMAGE)) ?: "",
                    seat = cursor.getString(cursor.getColumnIndex(KEY_SEAT)) ?: "",
                    ticketPrice = cursor.getInt(cursor.getColumnIndex(KEY_TICKET_PRICE)),
                    transactionNumber = cursor.getString(
                        cursor.getColumnIndex(
                            KEY_TRANSACTION_NUMBER
                        )
                    ) ?: "",
                    transactionTime = cursor.getString(cursor.getColumnIndex(KEY_TRANSACTION_TIME))
                        ?: ""
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
