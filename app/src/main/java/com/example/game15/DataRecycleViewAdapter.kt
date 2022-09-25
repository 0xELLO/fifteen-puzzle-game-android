package com.example.game15

import android.content.Context
import android.service.autofill.Dataset
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView

class DataRecycleViewAdapter(val context: Context, val repo: GameRepository): RecyclerView.Adapter<DataRecycleViewAdapter.ViewHolder>() {
    lateinit var dataset: List<GameList>

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    fun refreshData() {
        dataset = repo.getAll();
    }

    fun refreshDataMoves() {
        dataset = repo.getAllMoves();
    }

    init {
        refreshData()
    }

    val layoutInflater = LayoutInflater.from(context);


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rowView = layoutInflater.inflate(R.layout.row_view, parent, false);
        return ViewHolder(rowView);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val gameList = dataset[position];
        var stringData = "";
        val textViewData = holder.itemView.findViewById<TextView>(R.id.textViewData)
        stringData = gameList.gameName + " | " + gameList.gameMoves.toString() + " | " +  makeTime(gameList.gameTime)
        textViewData.text = stringData
    }

    fun makeTime(time: Int): String {

        val h = (time / 3600000).toInt()
        val m = (time - h * 3600000).toInt() / 60000
        val s = (time - h * 3600000 - m * 60000).toInt() / 1000

        val hh = if (h < 10) "0$h" else h.toString() + ""
        val mm = if (m < 10) "0$m" else m.toString() + ""
        val ss = if (s < 10) "0$s" else s.toString() + ""

        return "$hh:$mm:$ss"
    }

    override fun getItemCount(): Int {
        return dataset.count()
    }
}