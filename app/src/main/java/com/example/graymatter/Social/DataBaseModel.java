package com.example.graymatter.Social;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataBaseModel {
    private List<Player> players = new ArrayList<>();
    private Map<String, Integer> numbers;
    private List<GameSession> gameSessions;

    protected DataBaseModel(){
    }

    protected List<Player> getPlayers() {
        return players;
    }

    protected void setPlayers(List<Player> players) {
        this.players = players;
    }

    protected int getLastGameSessionNumber() {
        return numbers.get("lastGameSessionNumber");
    }

    protected void incLastGameSessionNumber(){
        Integer toInc = numbers.get("lastGameSessionNumber");
        numbers.put("lastGameSessionNumber", toInc + 1);
    }

    protected int getLastUserID() {
        return numbers.get("lastUserID");
    }

    protected void incLastUserID(){
        Integer toInc = numbers.get("lastUserID");
        numbers.put("lastUserID", toInc + 1);
    }

    protected int getCurrentPlayer(){
        return numbers.get("currentPlayer");
    }

    protected List<GameSession> getGameSessions() {
        return gameSessions;
    }

    public void setGameSessions(List<GameSession> arr) {
        gameSessions = arr;
    }
}
