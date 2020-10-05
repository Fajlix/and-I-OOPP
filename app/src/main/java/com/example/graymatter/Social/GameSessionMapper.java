package com.example.graymatter.Social;

import android.net.ParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;
import java.util.Optional;

public class GameSessionMapper implements DataMapper<GameSession>{
    //parametriserad mapper?? går det?
    String dbPath;
    Gson gson = new Gson();


    protected GameSessionMapper(String dbPath){
        this.dbPath = dbPath;
      //  this.gson = gson; hmm
    }

    @Override
    public Optional<GameSession> find(int gameID) {
        try {
            List<GameSession> gameSessions = getDBGameSessions();
            for (GameSession g : gameSessions){

                if (g.getGameID() == gameID){
                    return Optional.of(g);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void delete(GameSession gameSession) throws DataMapperException {
        try {
            // DataBaseModel nDb = getDB();
            List<GameSession> arr = getDBGameSessions();
            GameSession toD = null;
            for (GameSession g: arr) {
                if (g.getGameID() == gameSession.getGameID()) {
                    toD = g;

                }
            }
            if (!arr.remove(toD)){
                throw new DataMapperException("GameSession not present");
            }
           // nDb.setGameSessions(arr);
            enterData(arr);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(GameSession gameSession) throws DataMapperException {
        try {
            List<GameSession> arr = getDBGameSessions();
            for (GameSession g : arr) {
                if (g.getGameID() == gameSession.getGameID()) {
                    arr.remove(g);
                    arr.add(gameSession);
                    enterData(arr);
                    return;
                }
            }
        } catch (IOException | ParseException e){
            e.printStackTrace();
        }
    }

    @Override
    public void insert(GameSession gameSession) throws DataMapperException {
        try {
            //? nedan
            if( find(gameSession.getGameID()).isPresent()){
                throw new DataMapperException("GameID taken!");
            }
            List<GameSession> nGameSessions = getDBGameSessions();
            nGameSessions.add(gameSession);
            enterData(nGameSessions);
        } catch (IOException | ParseException e){
            e.printStackTrace();
        }
    }

    private List<GameSession> getDBGameSessions() throws IOException {
        Reader reader = new FileReader(dbPath);

        DataBaseModel dbM = gson.fromJson(reader, DataBaseModel.class);
        reader.close();
        return dbM.getGameSessions();
    }

    private DataBaseModel getDB() throws IOException {
        Reader reader = new FileReader(dbPath);

        DataBaseModel dbM = gson.fromJson(reader, DataBaseModel.class);
        reader.close();
        return dbM;
    }

    private void enterData(List<GameSession> nGameSessions) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        DataBaseModel nDb = getDB();
        nDb.setGameSessions(nGameSessions);
        Writer writer = new FileWriter(dbPath);
        gson.toJson(nDb, writer);
        writer.flush();
        writer.close();
    }
}
