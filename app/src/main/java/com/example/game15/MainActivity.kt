package com.example.game15

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Chronometer
import android.widget.TextView
import android.os.SystemClock
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class MainActivity : AppCompatActivity() {

    private val brain = GameBrain()
    private lateinit var chronometer: Chronometer
    private lateinit var moveNumberTextView: TextView
    private lateinit var startButton: Button
    private var timeBase = 0L;
    private lateinit var gameRepository: GameRepository;

    private var gameStarted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("TESTMEM", "onCrate")

        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return

        // restore board
        val boardJson = sharedPref.getString("state", null)
        if (boardJson != null) {
            brain.restoreBoardFromJson(boardJson)
        }
        // restore moves
        val savedMoves = sharedPref.getInt("Moves", 0)
        // restore time
        val savedTime = sharedPref.getLong("Time", 0)
        timeBase = savedTime

        chronometer = findViewById<Chronometer>(R.id.chornometerChrono)
        moveNumberTextView = findViewById<TextView>(R.id.textViewMovesNumber)
        startButton = findViewById(R.id.buttonStart)

        chronometer.base = SystemClock.elapsedRealtime() + timeBase
        moveNumberTextView.text = savedMoves.toString()
        brain.setMoveNumber(savedMoves)

        updateUi()
    }

    override fun onStop() {
        super.onStop()
        Log.d("TESTMEM", "onStop")

        chronometer.stop()

        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return

        with(sharedPref.edit()) {
            putString("state", brain.getBoardJson())
            putInt("Moves", brain.getMoveNumber().toInt())
            putLong("Time", chronometer.base - SystemClock.elapsedRealtime())
            commit()
        }
    }

    private fun updateUi() {
        for (x in brain.getBoard().indices) {
            for (y in brain.getBoard()[x].indices) {
                val id = resources.getIdentifier("buttonGame$x$y", "id", packageName)
                val button = findViewById<Button>(id)
                val number =  brain.getBoard()[x][y]

                if (number == 16) {
                    button.backgroundTintList = ColorStateList.valueOf(Color.rgb(13,13,13))
                    button.text = " "

                } else {
                    button.backgroundTintList = ColorStateList.valueOf(Color.rgb(29,29,29))
                    button.text = brain.getBoard()[x][y].toString()

                }
            }
        }

        moveNumberTextView.text = brain.getMoveNumber()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun buttonGameClicked(view: View){
        val idStr = resources.getResourceEntryName(view.id)
        val x = idStr[10].toString().toInt()
        val y = idStr[11].toString().toInt()

        brain.makeMove(x, y)

        updateUi()


        if (!gameStarted) {
            chronometer.base = SystemClock.elapsedRealtime() + timeBase
            chronometer.start()
            gameStarted = true;
            startButton.text = "PAUSE"
        }

        if (brain.chekWinCondition()) {
            gameStarted = false;
            val myDialogFragment = WinDialog((
                   SystemClock.elapsedRealtime() - chronometer.base), brain.getMoveNumber())
            val manager = supportFragmentManager
            myDialogFragment.show(manager, "myDialog")

            val Format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            val date = LocalDateTime.now().format(Format)

            gameRepository = GameRepository(this).open();

            gameRepository.add(GameList(date.toString(), (SystemClock.elapsedRealtime() - chronometer.base).toInt(), brain.getMoveNumber().toInt(),))

            //gameRepository.add(date.toString(), brain.getMoveNumber(), (SystemClock.elapsedRealtime() - chronometer.base).toInt())

            buttonResetGame(view);
        }

        updateUi()

    }

    fun buttonStartGame(view: View) {
        if (!gameStarted) {
            chronometer.base = SystemClock.elapsedRealtime() + timeBase
            chronometer.start()
            gameStarted = true;
            startButton.text = "PAUSE"

        } else {
            startButton.text = "START"
            chronometer.stop()
            timeBase = chronometer.base - SystemClock.elapsedRealtime()
            gameStarted = false;
        }

    }

    fun buttonResetGame(view: View) {
        brain.generateRandomBoard()
        moveNumberTextView.text =  "0"
        gameStarted = false;
        startButton.text = "START"
        chronometer.stop()
        chronometer.base = SystemClock.elapsedRealtime()
        timeBase = 0;

        updateUi()
    }

    fun buttonLeaderboard(view: View){
        val intent = Intent(this, LeaderboardActivity::class.java)
        startActivity(intent)
    }


    private fun updateMoveCount(number: String) {
        val id = resources.getIdentifier("textViewMovesNumber", "id", packageName)
        val textView = findViewById<TextView>(id)

        textView.text = number;
    }
}