package com.example.graymatter.Social;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class dbModel {
    public List<Player> players = new ArrayList<>();
    int lastGameSessionNumber;

    protected dbModel(){
    }

    protected List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getLastGameSessionNumber() {
        return lastGameSessionNumber;
    }

    public void incLastGameSessionNumber(){
        lastGameSessionNumber++;
    }
}
