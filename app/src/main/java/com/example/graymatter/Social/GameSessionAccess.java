package com.example.graymatter.Social;

import android.net.ParseException;

import org.json.JSONException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public class GameSessionAccess {

    private GameSessionMapper gsMapper;
    private PlayerAccess playerAccess;

    public GameSessionAccess(String dbPath){
        gsMapper = new GameSessionMapper(dbPath);
        playerAccess = new PlayerAccess(dbPath);
    }

    public void storeGameSession(int score, String gameType) throws ParseException {
        LocalDate date = LocalDate.now(); //solve timezone stuff
        GameSession gs = new GameSession(getNewGameID(), score, gameType, date);
        playerAccess.storeGameID(gs.getGameID());
        gsMapper.insert(gs);
    }

    /**
     * For package-internal cleaning purposes.
     * @param gameID unique gameID of the GameSession.
     */
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

    protected GameSession getGameSession(int gameID){
        Optional<GameSession> opGameSession = gsMapper.find(gameID);
        if(opGameSession.isPresent()){
            return opGameSession.get();
        }
        throw new DataMapperException("gameId does not match GameSession in database.");
    }
}
