package com.example.game15

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.SystemClock

class GameRepository(val context: Context) {

    private lateinit var dbHelper : DbHelper
    private lateinit var db: SQLiteDatabase

    fun open(): GameRepository {
        dbHelper = DbHelper(context)
        db = dbHelper.writableDatabase
        return this
    }

    fun add(gameList: GameList) {

        val values = ContentValues()
        values.put(DbHelper.GAME_NAME, gameList.gameName)
        values.put(DbHelper.GAME_MOVES, gameList.gameMoves)
        values.put(DbHelper.GAME_TIME, gameList.gameTime)

        db.insert(DbHelper.GAME_TABLE_NAME, null, values)
    }

    fun getAll(): List<GameList>{

        var result = ArrayList<GameList>();

        val columns = arrayOf(
            DbHelper.GAME_ID,
            DbHelper.GAME_NAME,
            DbHelper.GAME_MOVES,
            DbHelper.GAME_TIME
        )
        // val cursor = db.query(DbHelper.GAME_TABLE_NAME, null, null, null, null  , null, DbHelper.GAME_TIME+" ASC")
        val cursor = db.query(
            DbHelper.GAME_TABLE_NAME,
            columns,
            null,
            null,
            null,
            null,
            DbHelper.GAME_TIME+" ASC"
        )

        while (cursor.moveToNext()) {
            result.add(
                GameList(
                cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.GAME_NAME)),
                cursor.getInt(cursor.getColumnIndexOrThrow(DbHelper.GAME_TIME)),
                cursor.getInt(cursor.getColumnIndexOrThrow(DbHelper.GAME_MOVES)),
            )
            )
        }

        return result;
    }

    fun getAllMoves(): List<GameList>{

        var result = ArrayList<GameList>();

        val columns = arrayOf(
            DbHelper.GAME_ID,
            DbHelper.GAME_NAME,
            DbHelper.GAME_MOVES,
            DbHelper.GAME_TIME
        )
        // val cursor = db.query(DbHelper.GAME_TABLE_NAME, null, null, null, null  , null, DbHelper.GAME_TIME+" ASC")
        val cursor = db.query(
            DbHelper.GAME_TABLE_NAME,
            columns,
            null,
            null,
            null,
            null,
            DbHelper.GAME_MOVES+" ASC"
        )

        while (cursor.moveToNext()) {
            result.add(
                GameList(
                    cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.GAME_NAME)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(DbHelper.GAME_TIME)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(DbHelper.GAME_MOVES)),
                )
            )
        }

        return result;
    }


    fun close(){
        dbHelper.close()
    }
}

