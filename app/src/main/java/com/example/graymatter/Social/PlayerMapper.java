package com.example.graymatter.Social;

import android.net.ParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;


public final class PlayerMapper implements DataMapper<Player> {
    private final String dbPath;
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

    public PlayerMapper(String dbPath) {
        this.dbPath = dbPath; // "src/main/assets/testPlayers.json"
        //in db?
        currentFriendID = 0;
            try {
                if (newRead().getCurrentPlayer() != 0) {
                    Optional<Player> player = find(newRead().getCurrentPlayer());
                    if (player.isPresent()) currentPlayer = player.get();
                }
            } catch (IOException e) {
                e.printStackTrace();
        }
        //listeners = new ArrayList<>();
    }

    @Override
    public Optional<Player> find(int userID) {
        try {
            DataBaseModel obj =  newRead();
            for (Player p : obj.players){

                if (p.getUserID() == (userID)){
                    return Optional.of(p);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Player getPlayer(int friendUserID) {
        //TODO major dumbness. fix pls
        try {
            List<Player> arr = newRead().getPlayers();
            for (Player p: arr) {

                if (p.getUserID() == friendUserID) {
                    return p;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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
        List<Player> players = null;
        try {
            players = newRead().getPlayers();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return players;
    }

    private void enterData() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Writer writer = new FileWriter(dbPath);
        gson.toJson(toWrite, writer);
        writer.flush();
        writer.close();
    }


    private DataBaseModel newRead() throws IOException {
        Reader reader = new FileReader(dbPath);
        DataBaseModel toReturn = gson.fromJson(reader, DataBaseModel.class);
        reader.close();
        return toReturn;
    }




    private int newGameSessionNumber() throws IOException, ParseException, JSONException {
        DataBaseModel nDb = newRead();
        return nDb.getLastGameSessionNumber();
    }

    public String getEmail() throws UserInfoException {
        return currentPlayer.getEmail();
    }

    private int makeFriendID() {
        currentFriendID += 1;
        return currentFriendID;
    }

    /**
     * Log out the player. Purely handles database and currentPlayer parameter
     */
    public void logOut() {
      //  currentPlayer.changeUserKey(userKey, 0);
        currentPlayer = null;  //näej pissdålig idé
        currentGameSession = null;
        notifyListenersLogout();
    }


    private List<Player> getUserFriends() {
        List<Player> friends = new ArrayList<>();
        for (int friendID : currentPlayer.getFriendUserIDs()) {
            friends.add(getPlayer(friendID));
        }
        return friends;
    }
    //TODO friend list should update at app startup
    public void addFriend(int userID) throws UserInfoException {
        if(isFriend(userID)){
            throw new DataMapperException("Already a friend!");
        }
        Optional<Player> friend = find(userID);
        if (!friend.isPresent()){
            throw new DataMapperException("No user with given userID");
        }
        currentPlayer.addFriend(userID);
        friend.get().addFriend(currentPlayer.getUserID());
        update(currentPlayer);
        update(friend.get());
    }

    public void removeFriend(int userID) {
        if(!isFriend(userID)){
            throw new DataMapperException("Already not a friend!");
        }
        try {
            currentPlayer.removeFriend(userID);
        } catch (UserInfoException e){
            e.printStackTrace();
        }
        update(currentPlayer);
        Optional<Player> friend = find(userID);
        if (!friend.isPresent()){
            throw new DataMapperException("No user with given userID");
        }
        try {
            friend.get().removeFriend(currentPlayer.getUserID());
        } catch (UserInfoException e) {
            e.printStackTrace();
        }
        update(friend.get());
    }

    public boolean isFriend(int userID){
        return currentPlayer.isFriend(userID);

    }

    //Concerning listeners
    //prob not needed

    public void addListener(PlayerMapperListener listener) {
        listeners.add(listener);
    }

    public void removeListener(PlayerMapperListener listener) {
        listeners.remove(listener);
    }

    private void notifyListenersLogin() {
        for (PlayerMapperListener listener : listeners) {
            listener.notifyLogIn();
        }
    }

    private void notifyListenersLogout() {
        for (PlayerMapperListener listener : listeners) {
            listener.notifyLogout();
        }
    }

    public boolean isLoggedIn() {
        return (currentPlayer != null);
    }
}
