package com.example.movieexplorer.SQLite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context, factory:SQLiteDatabase.CursorFactory?)
    : SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

        companion object {
            private val DATABASE_NAME = "MovieExplorer"

            private val DATABASE_VERSION = 1

            val TABLE_NAME = "favourite_movie"

            val TITLE_COL = "Title"
        }


    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE $TABLE_NAME ($TITLE_COL TEXT PRIMARY KEY)")
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addFavouriteMovie(title: String){
        val values = ContentValues()

        values.put(TITLE_COL, title)

        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getFavouriteMovie(): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    fun getFavouriteMovie(title: String): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $TITLE_COL = '$title'", null)
    }

    fun removeFavouriteMovie(title: String){
        val db = this.writableDatabase
        db.execSQL("DELETE FROM $TABLE_NAME WHERE $TITLE_COL = '$title'")
    }
}