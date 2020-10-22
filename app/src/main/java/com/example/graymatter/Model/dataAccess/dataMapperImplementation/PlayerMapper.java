package com.example.graymatter.Model.dataAccess.dataMapperImplementation;

import android.content.Context;
import android.net.ParseException;

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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


/**
 * DataMapper concretisation for accessing Player database. Public methods cover all allowed public Player database interactions.
 * Package dataAccess contains class PLayerAccess for help with Player actions. External clients do not need to directly use PlayerMapper methods. //TODO maybe i shouldn't write this?
 */
public final class PlayerMapper implements DataMapper<Player> {
    //TODO make get() return Iterable
    //TODO should it check for empty fields?

    Gson gson = new Gson();
    Context context;

    /**
     * PlayerMapper constructor
     * @param dbPath path to .json file
     */
    public PlayerMapper(Context context) {
        this.context = context;
    }

    /**
     * Finds Optional of Player in database based on userID, or if no Player matching userID, empty Optional.
     * @param userID mathing an existing account´s userID.
     * @return Optional of matching player, or empty Optional
     */
    @Override
    public Optional<Player> find(int userID) {
        try {
            List<Player> obj = newRead().getPlayers();
            for (Player p : obj){
                if (p.getUserID() == (userID)){
                    return Optional.of(p);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * Inserts a player into the database. Will not insert Players with a userID already in database.
     * @param player to insert.
     * @throws DataMapperException if player userID matches the userID of a player already in database.
     */
    @Override
    public void insert(Player player) throws DataMapperException {
        try {
            Optional<Player> shouldBeEmpty = find(player.getUserID());
            if (shouldBeEmpty.isPresent()){
                throw new DataMapperException("Player with identical userID is already in database");
            }
            List<Player> players = newRead().getPlayers();
            players.add(player);
            enterData(players);
        } catch (IOException | ParseException e){
            e.printStackTrace();
        }
    }

    /**
     * Updates fields of a player in database. Identifies the player from argument player´s userID.
     * @param player new attributes of player in database with a userID matching argument player´s userID.
     */
    @Override
    public void update(Player player) {
        try {
            List<Player> players = newRead().getPlayers();
            for (Player p : players) {
                if (p.getUserID() == player.getUserID()) {
                    players.remove(p);
                    players.add(player);
                    enterData(players);
                    return;
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes a Player from database completely. Action can not be reversed.
     * @param player to delete. Matching userID selects entry for removal. Other attributes can differ, database entry will still be deleted.
     * @throws DataMapperException if no Player with matching userID can be found.
     */
    @Override
    public void delete(Player player) throws DataMapperException {
        try {
            List<Player> arr = newRead().getPlayers();
            Player pToBeD = null;
            for (Player p: arr) {
                if (p.getUserID() == player.getUserID()) {
                    pToBeD = p;
                }
            }
            if (!arr.remove(pToBeD)){
                throw new DataMapperException("Player not present");
            }
            enterData(arr);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     *
     * @return a list of all players in database.
     */
    @Override
    public List<Player> get() {
        List<Player> players = null;
        try {
            players = newRead().getPlayers();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return players;
    }


    /**
     * Method for database entries, not intended for use from other methods than those defined by interface.
     * @param players updated List of players to wrote to database.
     *//*
    private void enterData(List<Player> players) {

        try {
            DataBaseModel nDb = gson.fromJson(getJsonString(context), DataBaseModel.class);
            nDb.setPlayers(players);

            File file = appStorageDirectory();
            FileWriter writer = new FileWriter(file);
            writer.write(nDb.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }*/

    private File appStorageDirectory() {
        File appFilesDirectory = context.getFilesDir();
        return new File(appFilesDirectory, "test");
    }

    private void enterData(List<Player> players){
        File file = new File(context.getFilesDir(), "testplayers.json");
        FileOutputStream stream = null;
        try {

            DataBaseModel nDb = gson.fromJson(getJsonString(context), DataBaseModel.class);
            nDb.setPlayers(players);
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
        //Reader reader = new FileReader(getJsonString(context));
        DataBaseModel toReturn = gson.fromJson(getJsonString(context), DataBaseModel.class);
       // reader.close();
        return toReturn;
    }

    private String getJsonString (Context context) throws IOException {
        //InputStream inputStream = context.getFilesDir().open("testplayers.json");
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
