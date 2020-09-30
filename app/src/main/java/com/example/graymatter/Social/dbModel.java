package com.example.graymatter.Social;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class dbModel {
    public List<Player> players = new ArrayList<>();
    public Map<String, Integer> numbers;

    protected dbModel(){
    }

    protected List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getLastGameSessionNumber() {
        return numbers.get("lastGameSessionNumber");
    }

    public void incLastGameSessionNumber(){
        Integer toInc = numbers.get("lastGameSessionNumber");
        numbers.put("lastGameSessionNumber", toInc + 1);
    }

    public int getLastUserID() {
        return numbers.get("lastUserID");
    }

    public void incLastUserID(){
        Integer toInc = numbers.get("lastUserID");
        numbers.put("lastUserID", toInc + 1);
    }
}
