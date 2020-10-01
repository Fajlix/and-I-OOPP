package com.example.graymatter.Social;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataBaseModel {
    public List<Player> players = new ArrayList<>();
    public Map<String, Integer> numbers;

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
}
