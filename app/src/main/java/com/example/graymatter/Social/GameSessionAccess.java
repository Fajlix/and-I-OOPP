package com.example.graymatter.Social;

import android.net.ParseException;

import org.json.JSONException;

import java.io.IOException;
import java.time.LocalDate;

public class GameSessionAccess {

    GameSessionMapper gsMapper;

    public GameSessionAccess(String dbPath){
        gsMapper = new GameSessionMapper(dbPath);
        //TODO get current player locally somehow
    }

    //TODO testing / remodeling
    public void storeGameSession(int score, String gameType) throws ParseException, JSONException, IOException {
        LocalDate date = LocalDate.now(); //solve timezone stuff
        GameSession gs = new GameSession(getNewGameID(), score, gameType, date);
        DataBaseModel nDb;
        try {
            nDb = newRead();

            for (Player p : nDb.getPlayers()) {
                if (p.getUserID() == currentPlayer.getUserID()) {
                    break;
                }
            }
            GameSession gameSession = new GameSession(newGameSessionNumber(), score, gameType, date);
            currentPlayer.addGameSession(gameSession);
            nDb.incLastGameSessionNumber();
            //store in database
            toWrite = nDb;
            enterData();
            update(currentPlayer);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private int getNewGameID() {
        int topGameID = 0;
        for (GameSession g: gsMapper.get()){ //should throw exception if empty?
            if (g.getGameID() > topGameID){
                topGameID = g.getGameID();
            }
        }
        return topGameID + 1;
    }
}
