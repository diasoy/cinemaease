package com.example.cineeaseapp.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandlerSnack(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 3 // Update to version 3
        private const val DATABASE_NAME = "CineEaseDB"
        private const val TABLE_ORDERS = "order_snack"
        private const val KEY_ID = "id"
        private const val KEY_SNACK_NAME = "snack_name"
        private const val KEY_SNACK_IMAGE = "snack_image"
        private const val KEY_QUANTITY = "quantity"
        private const val KEY_SNACK_PRICE = "snack_price"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createOrdersTable =
            ("CREATE TABLE $TABLE_ORDERS($KEY_ID INTEGER PRIMARY KEY,$KEY_SNACK_NAME TEXT,$KEY_SNACK_IMAGE TEXT,$KEY_QUANTITY INTEGER,$KEY_SNACK_PRICE INTEGER)")
        db.execSQL(createOrdersTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE $TABLE_ORDERS ADD COLUMN $KEY_SNACK_IMAGE TEXT")
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

    fun addOrderSnack(snackName: String, snackImage: String, quantity: Int, snackPrice: Int): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_SNACK_NAME, snackName)
        values.put(KEY_SNACK_IMAGE, snackImage)
        values.put(KEY_QUANTITY, quantity)
        values.put(KEY_SNACK_PRICE, snackPrice)
        val id = db.insert(TABLE_ORDERS, null, values)
        db.close()
        return id
    }

    fun getAllOrdersSnack(): ArrayList<OrderSnack> {
        val orderList = ArrayList<OrderSnack>()
        val selectQuery = "SELECT  * FROM $TABLE_ORDERS"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val order = OrderSnack(
                    id = cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                    snackName = cursor.getString(cursor.getColumnIndex(KEY_SNACK_NAME)) ?: "",
                    snackImage = cursor.getString(cursor.getColumnIndex(KEY_SNACK_IMAGE)) ?: "",
                    quantity = cursor.getInt(cursor.getColumnIndex(KEY_QUANTITY)),
                    snackPrice = cursor.getInt(cursor.getColumnIndex(KEY_SNACK_PRICE))
                )
                orderList.add(order)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return orderList
    }

    fun deleteAllOrders() {
        val db = this.writableDatabase
        db.delete(TABLE_ORDERS, null, null)
        db.close()
    }
}