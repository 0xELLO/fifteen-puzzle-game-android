package com.example.game15

import android.util.Log
import org.json.JSONArray

class GameBrain {
    private var _board = arrayOf(
        arrayOf(1, 2, 3, 4), arrayOf(5, 6, 7, 8), arrayOf(9, 10, 11, 12), arrayOf(13, 14, 15, 16))

    private val _winCondition = arrayOf(
    arrayOf(1, 2, 3, 4), arrayOf(5, 6, 7, 8), arrayOf(9, 10, 11, 12), arrayOf(13, 14, 15, 16))

    private var _moveNumber = 0;


    fun getBoard() :Array<Array<Int>> {
        return _board
    }

    fun makeMove(x: Int, y: Int) {

        if (isMoveValid(x, y)) {
            val blankX = getBlankX()
            val blankY = getBlankY()

            _board[blankX][blankY] = _board[x][y]
            _board[x][y] = 16
            _moveNumber++
        }
    }

    private fun isMoveValid(x: Int, y:Int): Boolean {

        val blankX = getBlankX()
        val blankY = getBlankY()

        if ((x !in 0..3) or (y !in 0..3)) {
            return false
        }

        if ((((blankX == x - 1) or (blankX == x + 1)) and (blankY == y))
            or (((blankY == y - 1) or (blankY == y + 1)) and (blankX == x))) {
            return true
        }
        return false
    }

    fun generateRandomBoard() {
        for (n in 1..5) {
            val x = getBlankX() + (-1..1).random()
            val y = getBlankY() + (-1..1).random()

            Log.d("Rand", "$x - $y")

            makeMove(x, y)
        }
        resetMoves()
        //reset();

    }

    private fun getBlankX(): Int {
        for (x in _board.indices) {
            for (y in _board[x].indices) {
                if (_board[x][y] == 16) return x
            }
        }
        return 3;
    }


    private fun getBlankY(): Int {
        for (x in _board.indices) {
            for (y in _board[x].indices) {
                if (_board[x][y] == 16) return y
            }
        }
        return 3;
    }

    public fun chekWinCondition(): Boolean {
        return _board.contentDeepEquals(_winCondition)
    }

    fun reset() {
        _board = arrayOf(
            arrayOf(1, 2, 3, 4), arrayOf(5, 6, 7, 8), arrayOf(9, 10, 11, 12), arrayOf(13, 14, 16, 15))
    }

    fun getBoardJson(): String {
        val jsonArray = JSONArray(getBoard())
        return jsonArray.toString();
    }

    fun restoreBoardFromJson(boardJson: String) {
        val jsonArray = JSONArray(boardJson)
        for (x in 0 until jsonArray.length()) {
            for (y in 0 until (jsonArray[x] as JSONArray).length()){
                _board[x][y] = (jsonArray[x] as JSONArray)[y] as Int
            }
        }
    }

    fun getMoveNumber(): String{
        return _moveNumber.toString()
    }

    fun setMoveNumber(number: Int) {
        _moveNumber = number;
    }

    private fun resetMoves() {
        _moveNumber = 0;
    }
}