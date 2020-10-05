package com.example.graymatter.Social;

import android.net.ParseException;

import org.json.JSONException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public class GameSessionAccess {

    GameSessionMapper gsMapper;

    public GameSessionAccess(String dbPath){
        gsMapper = new GameSessionMapper(dbPath);
    }

    public void storeGameSession(int score, String gameType) throws ParseException {
        LocalDate date = LocalDate.now(); //solve timezone stuff
        GameSession gs = new GameSession(getNewGameID(), score, gameType, date);
        gsMapper.insert(gs);
    }

    protected void removeGameSession(int gameID){
        Optional<GameSession> gs = gsMapper.find(gameID);
        if (!gs.isPresent()){
            throw new DataMapperException("gameID does not match GameSession");
        } else {
            gsMapper.delete(gs.get());
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
