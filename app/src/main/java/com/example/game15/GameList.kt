package com.example.game15

class GameList {
    var id: Int = 0;
    var gameName: String = "";
    var gameTime: Int = 0;
    var gameMoves: Int = 0;

    constructor(gameName: String, gameTime: Int, gameMoves: Int){
        this.gameName = gameName;
        this.gameTime = gameTime;
        this.gameMoves = gameMoves;
    }
}