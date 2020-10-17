package com.example.graymatter.Social;

import android.net.ParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Optional;
import java.util.Random;


public final class PlayerMapper implements DataMapper<Player> {
    //good for batch writing. bad for safety. idk
    private DataBaseModel toWrite;
    Gson gson = new Gson();
 /*   FileWriter writer;
    {
        try {
            writer = new FileWriter(dbPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }**/

    private Random rand = new Random();
    private String jsonStr;


    public Player currentPlayer;
    private int currentFriendID;
    public GameSession currentGameSession;
    //orimligt???
    //List<Player> playersList;
    //List<Player> friendBuffer;   ??? kanske
    //databasen
    //kanske bara läsa / skriva till fil med bufferedReader?

    //batch reading??

    List<PlayerMapperListener> listeners;

    public PlayerMapper(String jsonStr) {
        this.jsonStr = jsonStr;
        currentFriendID = 0;
        if (LocalDataMapper.getCurrentPlayerUserID() != 0) {
            Optional<Player> player = find(LocalDataMapper.getCurrentPlayerUserID());
            if (player.isPresent()) currentPlayer = player.get();
        }
        //listeners = new ArrayList<>();
    }

    public Optional<Player> find(int userID) {
            List<Player> obj = newRead().getPlayers();
            for (Player p : obj){
                if (p.getUserID() == (userID)){
                    return Optional.of(p);
                }
            }
        return Optional.empty();
    }

    public Player getPlayer(int friendUserID) {
        //TODO major dumbness. fix pls
            List<Player> arr = newRead().getPlayers();
            for (Player p: arr) {

                if (p.getUserID() == friendUserID) {
                    return p;
                }
            }
        throw new DataMapperException("No Player matching userID");
    }

    @Override
    public void delete(Player player) throws DataMapperException {
        try {
            DataBaseModel nDb = newRead();
            List<Player> arr = nDb.getPlayers();
            Player pToBe = null;
            for (Player p: arr) {
                if (p.getUserID() == player.getUserID()) {
                    pToBe = p;

                }
            }
            if (!arr.remove(pToBe)){
                throw new DataMapperException("Player not present");
            }
            nDb.setPlayers(arr);
            toWrite = nDb;
            enterData();
        } catch (IOException e){
            e.printStackTrace();
        }
    }



    @Override
    public void insert(Player player) throws DataMapperException {
        try {

            DataBaseModel dbModel = newRead();
            dbModel.getPlayers().add(player);
            toWrite = dbModel;
            enterData();
        } catch (IOException | ParseException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(Player player) throws DataMapperException {
        try {
            DataBaseModel nDb = newRead();
            List<Player> arr = nDb.getPlayers();
            for (Player p : arr) {
                if (p.getUserID() == player.getUserID()) {
                    arr.remove(p);
                    arr.add(player);
                    nDb.setPlayers(arr);
                    toWrite = nDb;
                    enterData();
                    return;
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Player> get() {
        return newRead().getPlayers();
    }

    private void enterData() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Writer writer = new FileWriter(jsonStr);
        gson.toJson(toWrite, writer);
        writer.flush();
        writer.close();
    }


    private DataBaseModel newRead(){
        return gson.fromJson(jsonStr, DataBaseModel.class);
    }

}
