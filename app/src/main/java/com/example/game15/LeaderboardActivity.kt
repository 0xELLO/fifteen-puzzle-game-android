package com.example.game15

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class LeaderboardActivity : AppCompatActivity() {


    private lateinit var gameRepository: GameRepository;
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DataRecycleViewAdapter;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)

        gameRepository = GameRepository(this).open();

        recyclerView = findViewById<RecyclerView>(R.id.recyclerViewGameList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = DataRecycleViewAdapter(this, gameRepository);
        recyclerView.adapter = adapter;
    }

    @SuppressLint("NotifyDataSetChanged")
    fun buttonSortByMovesClicked(view: View) {
        adapter.refreshDataMoves()
        recyclerView.setAdapter(null);
        recyclerView.setLayoutManager(null);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    fun buttonSortByTimeClicked(view: View) {
        adapter.refreshData()
        recyclerView.setAdapter(null);
        recyclerView.setLayoutManager(null);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
    }

    override fun onDestroy() {
        super.onDestroy()
        gameRepository.close()
    }


}