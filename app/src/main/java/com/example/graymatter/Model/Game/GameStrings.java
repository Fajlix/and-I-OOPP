package com.example.graymatter.Model.Game;

import com.example.graymatter.Model.Game.ChimpGame.ChimpGame;
import com.example.graymatter.Model.Game.MemoryGame.MemoryGame;
import com.example.graymatter.Model.Game.ReactionGame.ReactionTimeGame;
import com.example.graymatter.Model.Game.TowerOfHanoi.TowerOfHanoi;

public class GameStrings {

    public GameStrings(){


    }

    public static String getAllGames(){
        return getChimpString() + " " + getMemoryString() + " " + getReactionString() + " " + getTowerString();
    }

    public static String getReactionString(){
        return ReactionTimeGame.getGameString();
    }
    public static String getChimpString(){
        return ChimpGame.getGameString();
    }
    public static String getMemoryString(){
        return MemoryGame.getGameString();
    }
    public static String getTowerString(){
        return TowerOfHanoi.getGameString();
    }


}
