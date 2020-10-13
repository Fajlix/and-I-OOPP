package com.example.graymatter.Model.dataAccess;

import android.net.ParseException;

import com.example.graymatter.Model.dataAccess.dataMapper.DataMapper;
import com.example.graymatter.Model.dataAccess.dataMapper.DataMapperException;
import com.example.graymatter.Model.dataAccess.dataMapperImplementation.GameSessionMapper;
import com.example.graymatter.Model.dataAccess.social.GameSession;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GameSessionAccess {

    private static DataMapper<GameSession> gsMapper;
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

    public static Map<Integer, Integer> getAllScoresIdentifiable(){
        Map<Integer, Integer> scores = new HashMap<>();
        for (GameSession gs: gsMapper.get()){
            scores.put(gs.getGameID(), gs.getScore());
        }
        return scores;
    }

    public List<GameSession> getGameSessionsByType(String gameType) {
        List<GameSession> gs = gsMapper.get();
        List<GameSession> nGs = new ArrayList<>();
        for (GameSession session : gs){
            if (session.getGameType().equals(gameType)){
                nGs.add(session);
            }
        }
        return nGs;
    }

    public int getGameOwnerUserID(int gameID){
        return playerAccess.getGameIDOwner(gameID);
    }
}
