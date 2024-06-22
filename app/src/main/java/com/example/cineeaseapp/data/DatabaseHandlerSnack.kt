package com.example.cineeaseapp.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHandlerSnack(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1 // Increment this if youmodify the database schema later
        private const val DATABASE_NAME = "snack_database"
        private const val TABLE_ORDER_SNACK = "order_snack"

        // Column names for the order_snack table
        private const val KEY_ID = "id"
        private const val KEY_QUANTITY = "quantity"
        private const val KEY_TRANSACTION_NUMBER = "transaction_number"
        private const val KEY_SNACK_IMAGE = "snack_image"
        private const val KEY_SNACK_PRICE = "snack_price"
        private const val KEY_SNACK_NAME = "snack_name"
        private const val KEY_TRANSACTION_TIME = "transaction_time"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Create the order_snack table here
        val createOrderSnackTable = ("CREATE TABLE " + TABLE_ORDER_SNACK + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_QUANTITY + " INTEGER,"
                + KEY_TRANSACTION_NUMBER + " TEXT,"
                + KEY_SNACK_IMAGE + " INTEGER,"
                + KEY_SNACK_PRICE + " INTEGER,"
                + KEY_SNACK_NAME + " TEXT,"
                + KEY_TRANSACTION_TIME + " TEXT" + ")")
        db.execSQL(createOrderSnackTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE $TABLE_ORDER_SNACK ADD COLUMN $KEY_SNACK_IMAGE TEXT")
        }
        if (oldVersion < 4) {
            db.execSQL("ALTER TABLE $TABLE_ORDER_SNACK ADD COLUMN $KEY_TRANSACTION_NUMBER TEXT")
            db.execSQL("ALTER TABLE $TABLE_ORDER_SNACK ADD COLUMN $KEY_TRANSACTION_TIME TEXT")
        }
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ORDER_SNACK")
        onCreate(db)
    }

    fun addOrderSnack(
        quantity: Int,
        transactionNumber:String,
        snackImage: String,
        snackPrice: Int,
        snackName: String,
        transactionTime: String
    ): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_QUANTITY, quantity)
        contentValues.put(KEY_TRANSACTION_NUMBER, transactionNumber)
        contentValues.put(KEY_SNACK_IMAGE, snackImage)
        contentValues.put(KEY_SNACK_PRICE, snackPrice)
        contentValues.put(KEY_SNACK_NAME, snackName)
        contentValues.put(KEY_TRANSACTION_TIME, transactionTime)

        val success = db.insert(TABLE_ORDER_SNACK, null, contentValues)
        db.close()
        return success
    }

    fun getAllOrdersSnack(): ArrayList<OrderSnack> {
        val orderList = ArrayList<OrderSnack>()
        val selectQuery = "SELECT * FROM $TABLE_ORDER_SNACK"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
            cursor?.let {
                if (it.moveToFirst()) {
                    do {
                        val order = OrderSnack(
                            snackName = it.getString(it.getColumnIndex(KEY_SNACK_NAME)) ?: "",
                            snackImage = it.getString(it.getColumnIndex(KEY_SNACK_IMAGE)) ?: "",
                            quantity = it.getInt(it.getColumnIndex(KEY_QUANTITY)),
                            snackPrice = it.getInt(it.getColumnIndex(KEY_SNACK_PRICE)),
                            transactionNumber = it.getString(it.getColumnIndex(KEY_TRANSACTION_NUMBER)) ?: "",
                            transactionTime = it.getString(it.getColumnIndex(KEY_TRANSACTION_TIME)) ?: ""
                        )
                        orderList.add(order)
                    } while (it.moveToNext())
                }
            }
        } catch (e: Exception) {
            Log.e("DatabaseHandlerSnack", "Error while fetching all snack orders", e)
        } finally {
            cursor?.close()
            db.close()
        }
        return orderList
    }

    fun getAllSnackSales(): List<Laporan> {
        val laporanList = mutableListOf<Laporan>()

        val db = this.readableDatabase
        val query = "SELECT $KEY_SNACK_NAME, SUM($KEY_SNACK_PRICE * $KEY_QUANTITY) as total FROM $TABLE_ORDER_SNACK GROUP BY $KEY_SNACK_NAME"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val title = cursor.getString(cursor.getColumnIndex(KEY_SNACK_NAME))
                val total = cursor.getInt(cursor.getColumnIndex("total")).toString()
                val laporan = Laporan(title, total)
                laporanList.add(laporan)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return laporanList
    }

    fun deleteAllOrdersSnack() {
        val db = this.writableDatabase
        db.delete(TABLE_ORDER_SNACK, null, null)
        db.close()
    }
}
