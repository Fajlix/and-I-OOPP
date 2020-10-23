package com.example.graymatter.model.game;

import com.example.graymatter.model.game.chimpGame.ChimpGame;
import com.example.graymatter.model.game.memoryGame.MemoryGame;
import com.example.graymatter.model.game.reactiongame.ReactionTimeGame;
import com.example.graymatter.model.game.towerOfHanoi.TowerOfHanoi;

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
