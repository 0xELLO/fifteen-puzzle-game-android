package com.example.game15

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import java.sql.Time

class WinDialog(val _time: Long, val _moves: String): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        val h = (_time / 3600000).toInt()
        val m = (_time - h * 3600000).toInt() / 60000
        val s = (_time - h * 3600000 - m * 60000).toInt() / 1000

        val hh = if (h < 10) "0$h" else h.toString() + ""
        val mm = if (m < 10) "0$m" else m.toString() + ""
        val ss = if (s < 10) "0$s" else s.toString() + ""

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("You Won!")
                .setMessage("Time: $hh:$mm:$ss, Moves: $_moves")
                .setPositiveButton("Ok") {
                        dialog, id ->  dialog.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}