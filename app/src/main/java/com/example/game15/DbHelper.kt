package com.example.game15

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "app.db"
        const val DATABASE_VERSION = 2

        const val GAME_TABLE_NAME = "PERSONS"

        const val GAME_ID = "_id"

        const val GAME_NAME = "name"
        const val GAME_TIME = "time"
        const val GAME_MOVES = "moves"

        const val SQL_CREATE_TABLE =
            "create table $GAME_TABLE_NAME(" +
                    "$GAME_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$GAME_NAME  TEXT NOT NULL, " +
                    "$GAME_TIME INTEGER NOT NULL, " +
                    "$GAME_MOVES INTEGER NOT NULL);"

        const val SQL_DELETE_TABLES = "DROP TABLE IF EXISTS $GAME_TABLE_NAME ";
    }
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_TABLES)
        onCreate(db)
    }
}
