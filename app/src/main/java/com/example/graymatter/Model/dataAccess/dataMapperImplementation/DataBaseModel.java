package com.example.graymatter.Model.dataAccess.dataMapperImplementation;

import com.example.graymatter.Model.dataAccess.social.GameSession;
import com.example.graymatter.Model.dataAccess.social.Player;

import java.util.ArrayList;
import java.util.List;

public class DataBaseModel {
    private List<Player> players = new ArrayList<>();
    private List<GameSession> gameSessions;

    protected DataBaseModel(){
    }

    protected List<Player> getPlayers() {
        return players;
    }

    protected void setPlayers(List<Player> players) {
        this.players = players;
    }

    protected List<GameSession> getGameSessions() {
        return gameSessions;
    }

    public void setGameSessions(List<GameSession> arr) {
        gameSessions = arr;
    }
/*
    public <T> List<?> get(Class<? extends BaseDataMapper> aClass) {
        switch (aClass){
            case Player.class:
                return getPlayers();
            case GameSession.class:
                return getGameSessions();
            default:
                return null;
        }
    }
    public <T> void set(List<T> field) {
        switch (field.getClass()){
            case List<Player>.class:
                setPlayers((List<Player>) field);
                return;
            case List<GameSession>.class:
                setGameSessions((List<GameSession>) field);
        }
    }
*/
}
