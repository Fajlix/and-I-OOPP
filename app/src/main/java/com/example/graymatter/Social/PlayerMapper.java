package com.example.graymatter.Social;

import android.media.Image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;


import org.json.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class PlayerMapper implements PlayerMapperInterface {
    String serverLocation;
    private final String dbPath = "C:/Users/hanna/Documents/and-I-OOPP/json-server-lib";
    //good for batch writing. bad for safety. idk
    JSONObject toWrite;

    Random rand = new Random();
    Player currentPlayer;
    int currentFriendID;
    GameSession currentGameSession;
    //orimligt???
    List<Player> playersList;
    //List<Player> friendBuffer;   ??? kanske
    //databasen
    //kanske bara läsa / skriva till fil med bufferedReader?

    //batch reading??

    List<PlayerMapperListener> listeners;

    public PlayerMapper() {
        serverLocation = "http://localhost:3000";
        //in db?
        currentFriendID = 0;
        listeners = new ArrayList<>();
    }

    @Override
    public Optional<Player> find(int userID) {
        try {
            JSONObject obj = newRead();
            JSONArray arr = obj.getJSONArray("players");
            for (int i = 0; i < arr.length(); i++){
                JSONObject player = arr.getJSONObject(i);
                if (player.getInt("userID") == (userID)){
                    return Optional.of(fetchPlayer(player, false));
                }
            }
        } catch (ParseException | IOException | JSONException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    Player makeFriend(int friendID) {
        return null;
    }

    @Override
    public void delete(Player player) throws PlayerMapperException {

    }

    @Override
    public void insert(Player player) throws PlayerMapperException {

    }

    @Override
    public void update(Player player) throws PlayerMapperException {

    }

    /**
     * För att logga in på befintligt konto.
     *
     * @param userKey
     * @param friendID
     */
    public void logIn(int userKey, int friendID, String password) throws PlayerMapperException {
        Optional<Player> temp = find(friendID);
        Player player;
        if (temp.isPresent()) {
            player = temp.get();
        } else {
            throw new PlayerMapperException("FriendID does not match user in our database");
        }
        player.isPasswordCorrect(password);
        notifyListenersLogin();
        currentPlayer = player;

    }

    /**
     * För att skapa nytt konto och logga in.
     */
    public void createNewAccountAndLogIn(int userKey, String email, String password, String userName) throws MissingAccessException {
        //check account creation conditions before creating account.
        Player.passwordSafetyCheck(password);
        if (isName(userName)) throw new PlayerMapperException("Username already taken!");
        //ask if they want email login help?
        if (isName(email)) throw new PlayerMapperException("Email is already being used!");
        currentFriendID++;
        Player player = Player.makePlayer(userKey, currentFriendID, email, password, userName);
        JSONObject jsonPlayer = new JSONObject();
        try {
            jsonPlayer.put("userKey", userKey);
            jsonPlayer.put("userID", player.getUserID());
            jsonPlayer.put("email", email);
            jsonPlayer.put("password", password);
            jsonPlayer.put("userKey", userKey);
            jsonPlayer.put("userKey", userKey);
            jsonPlayer.put("userKey", userKey);
            jsonPlayer.put("userKey", userKey);
            JSONObject toMod = newRead();
            JSONArray arr = toMod.getJSONArray("players").put(jsonPlayer);
            /**
            m = new LinkedHashMap(2);
            m.put("type", "fax");
            m.put("number", "212 555-1234");

            // adding map to list
            ja.add(m);

            // putting phoneNumbers to JSONObject
            jo.put("phoneNumbers", ja);
            toMod.
            toWrite =
            enterData();
             **/
        } catch (JSONException | ParseException | IOException e) {
            e.printStackTrace();
        }
        JSONObject newPlayer = { "userKey": userKey, "age":30, "car":null };




    }

    public void newDataEntry(){
        toWrite = new JSONObject();
    }

    public void enterData() throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(dbPath);
        pw.write(toWrite.toString());
        pw.flush();
        pw.close();
        toWrite = null;
    }

    public JSONObject newRead() throws IOException, ParseException {
        Object obj = new JSONParser().parse(new FileReader(dbPath));
        return (JSONObject) obj;
    }


    //this and also findgame needs to iterateish
    private Player findPlayerFromStringField(String type, String value, boolean restricted) {
        //fult? alltså hela jävla metoden
        if (!(type.equals("userName") || type.equals("email"))){
            throw new IllegalArgumentException("String type is not 'userName', 'userID' nor 'email'");
        }
        try {
            JSONArray arr = newRead().getJSONArray("players");
            for (int i = 0; i < arr.length(); i++){
                JSONObject player = arr.getJSONObject(i);
                if (player.getString(type).equals(value)){
                    return fetchPlayer(player, restricted);
                }
            }
        } catch (ParseException | IOException | JSONException e) {
            e.printStackTrace();
        }
        throw new InvalidParameterException("A matching Player could not be found");
    }

    private Player fetchPlayer(JSONObject player, boolean restricted) throws JSONException {
        int userKey;
        int userID;
        String email;
        String password;
        String userName;
        Image userImage;
        List<Integer> friendUserIDs = new ArrayList<>();
        List<GameSession> playerHistory;
        if(!restricted){
            userKey = player.getInt("userKey");
            email = player.getString("email");
            password = player.getString("password");
            friendUserIDs = fetchFriendUserIDs(player);
        }
        //TODO get Image
        userImage = null;
        userID = player.getInt("userID");
        userName = player.getString("userName");
        playerHistory = fetchPlayerHistory(player);
        //close read?
        if (restricted) return Player.makePlayer(userID, userName, userImage, playerHistory);
        return Player.makePlayer(userKey, userID, email, password, userName, userImage, playerHistory, friendUserIDs);
    }

    public List<Integer> fetchFriendUserIDs (JSONObject player) throws JSONException {
        List<Integer> friendUserIDs = new ArrayList<>();
        JSONArray friendUserIDsPRE = player.getJSONArray("friendUserIDs");
        for (int o = 0; o < friendUserIDsPRE.length(); o++){
            friendUserIDs.add((Integer)friendUserIDsPRE.get(o));
        }
        return friendUserIDs;
    }

    public List<GameSession> fetchPlayerHistory (JSONObject player) throws JSONException {
        List<GameSession> playerHistory = new ArrayList<>();
        JSONArray playerHistoryPRE = player.getJSONArray("playerHistory");
        for (int o = 0; o < playerHistoryPRE.length(); o++){
            int gameID = playerHistoryPRE.getJSONObject(o).getInt("gameID");
            int score = playerHistoryPRE.getJSONObject(o).getInt("score");
            String gameType = playerHistoryPRE.getJSONObject(o).getString("gameType");
            String time = playerHistoryPRE.getJSONObject(o).getString("time");
            playerHistory.add(new GameSession(gameID, score, gameType, time));
        }
        return playerHistory;
    }

    //completely seperate getters? yh

    public Player findByEmail(String email) {

    }

    private boolean isName(String userName) {

    }

    public boolean isEmail(String email) {

    }

    public void storeGameSession(GameSession gameSession){
        currentPlayer.addGameSession(gameSession);
        //store in database
    }

    public int makeFriendID() {
        currentFriendID += 1;
        return currentFriendID;
    }

    /**
     * @param userKey Application unique player ID
     */
    public void logOut(int userKey) {
        currentPlayer.changeUserKey(userKey, 0);
        currentPlayer = null;  //näej pissdålig idé
        notifyListenersLogout();
    }


    public List<Player> getUserFriends() {
        List<Player> friends = new ArrayList<>();
        for (int friendID : currentPlayer.getFriendUserIDs()) {
            friends.add(makeFriend(friendID));
        }
        return friends;
    }


    /**
     *
     */
    private List<Player> getPlayers() throws JSONException {
        JSONArray playerArray = (JSONArray) jsonObject.get("players");
        List<Player> players = new ArrayList<>();
        for (Object o : playerArray) {
            JSONObject obj = (JSONObject) o;
            int friendID = (int) obj.get("friendID");
            String userImage = (String) obj.get("userImage");
            String userName = (String) obj.get("userName");
            List<GameSession> playerHistory = new ArrayList<>();
            for (Object i : (JSONArray) obj.get(playerHistory)) {
                JSONObject item = (JSONObject) i;
                int gameID = (int) item.get("gameID");
                int score = (int) item.get("score");
                String gameType = (String) item.get("gameType");
                String time = (String) item.get("time");
                GameSession gameSession = new GameSession(gameID, score, gameType, time);
                playerHistory.add(gameSession);
            }
            Player player = Player.makePlayer(friendID, userName, userImage, playerHistory);
            players.add(player);
        }
        return players;
    }

    private void updatePlayersList() {
        this.playersList = getPlayers();
    }

    //Concerning listeners

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
}

    //// TEST
/*
class PersonServer implements Runnable{

    protected int portNumber;
    protected ServerSocket serverSocket = null;
    protected boolean isStopped = false;
    protected Thread runningThread = null;

    public PersonServer(int portNumber) throws IOException {
        this.portNumber = portNumber;
    }

    @Override
    public void run() {


        synchronized (this) {
            this.runningThread = Thread.currentThread();
        }

        openServerSocket();

        while (!isStopped()) {
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                if (isStopped()) {
                    System.out.println("Server Stopped.");
                    return;
                }
                throw new RuntimeException(
                        "Error accepting client connection", e);
            }
            BufferedReader in = null;
            BufferedWriter out = null;
            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            } catch (IOException e) {
                e.printStackTrace();
            }
            String s = "";
            while (true) {
                try {
                    if (!((s = in.readLine()) != null)) break;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                if (s.isEmpty()) {
                    break;
                }
            }

            //tänk kompis
            new Thread(
                    new WorkerRunnable(
                            clientSocket, "Multithreaded Server")
            ).start();
        }
        System.out.println("Server Stopped.");

    }
    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop(){
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port 8080", e);
        }
    }


}

// in client:
//connect w
    Socket s = new Socket("localhost",3000);

*/
