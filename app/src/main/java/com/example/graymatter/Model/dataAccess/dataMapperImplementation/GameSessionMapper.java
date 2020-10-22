package com.example.graymatter.Model.dataAccess.dataMapperImplementation;

import android.content.Context;
import android.net.ParseException;

import com.example.graymatter.Model.Game.Game;
import com.example.graymatter.Model.dataAccess.dataMapper.DataMapper;
import com.example.graymatter.Model.dataAccess.dataMapper.DataMapperException;
import com.example.graymatter.Model.dataAccess.social.GameSession;
import com.example.graymatter.Model.dataAccess.social.Player;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.ContentHandler;
import java.util.List;
import java.util.Optional;

public class GameSessionMapper implements DataMapper<GameSession> {
    //parametriserad mapper?? går det?
    String dbPath;
    Gson gson = new Gson();
    Context context;


    public GameSessionMapper(Context context){
        this.dbPath = dbPath;
        this.context = context;
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
            enterData(arr);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(GameSession gameSession) {
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
        } catch (IOException e){
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

    @Override
    public List<GameSession> get() {
        List<GameSession> gameSessions = null;
        try {
            gameSessions = getDBGameSessions();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gameSessions;
    }

    private List<GameSession> getDBGameSessions() throws IOException {


        DataBaseModel dbM = gson.fromJson(getJsonString(context), DataBaseModel.class);
        return dbM.getGameSessions();
    }

    //maybe this should throw the exception
    private void enterData(List<GameSession> gameSessions){
        File file = new File(context.getFilesDir(), "testplayers.json");
        FileOutputStream stream = null;
        try {

            DataBaseModel nDb = gson.fromJson(getJsonString(context), DataBaseModel.class);
            nDb.setGameSessions(gameSessions);
            System.out.println();

            String jsonInString = gson.toJson(nDb);

            stream = new FileOutputStream(file);
            stream.write(jsonInString.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * Method for reading the database, not intended for use from other methods than those defined by interface.
     * @return DataBaseModel containing all database fields.
     * @throws IOException
     */
    private DataBaseModel newRead() throws IOException {
        DataBaseModel toReturn = gson.fromJson(getJsonString(context), DataBaseModel.class);
        return toReturn;
    }

    private String getJsonString (Context context) throws IOException {
        File file = new File(context.getFilesDir(), "testplayers.json");
        FileInputStream stream = new FileInputStream(file);

        Reader red = new InputStreamReader(stream);
        BufferedReader buf = new BufferedReader(red);

        String line = buf.readLine();
        StringBuilder sb = new StringBuilder();
        while(line != null){
            sb.append(line).append("\n");
            line = buf.readLine();
        }
        System.out.println(sb.toString());
        return sb.toString();

    }
}
