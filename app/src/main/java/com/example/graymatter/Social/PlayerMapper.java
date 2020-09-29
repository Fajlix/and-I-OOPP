package com.example.graymatter.Social;

import android.media.Image;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.TimeZone;


import org.json.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class PlayerMapper implements PlayerMapperInterface {
    private String serverLocation;
    private final String dbPath = "C:/Users/hanna/Documents/and-I-OOPP/json-server-lib";
    //good for batch writing. bad for safety. idk
    private JSONObject toWrite;

    private Random rand = new Random();
    Player currentPlayer;
    private int currentFriendID;
    GameSession currentGameSession;
    //orimligt???
    //List<Player> playersList;
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
        } catch (ParseException | IOException | JSONException | UserInfoException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Player getPlayer(int friendUserID) {
        //TODO major dumbness. fix pls
        try {
            JSONArray arr = newRead().getJSONArray("players");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject player = arr.getJSONObject(i);
                if (player.getInt("userID") == friendUserID) {
                    return fetchPlayer(player, true);
                }
            }
        } catch (IOException | JSONException | UserInfoException | ParseException e) {
            e.printStackTrace();
        }
        throw new PlayerMapperException("No Player matching userID");
    }

    @Override
    public void delete(Player player) throws PlayerMapperException {
        newDataEntry();
        try {
            JSONArray arr = newRead().getJSONArray("players");
            for (int i = 0; i < arr.length(); i++) {
                if (arr.getJSONObject(i).getInt("userID") == player.getUserID()) {
                    JSONArray nArr = (JSONArray) arr.remove(i);
                    toWrite = newRead().put("players", nArr);
                    enterData();
                }
            }
        } catch (JSONException | ParseException | IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void insert(Player player) throws PlayerMapperException {
        newDataEntry();
        JSONObject nPlayer = new JSONObject();
        try {
            nPlayer.put("userName", player.getUserName());
            nPlayer.put("email", player.getEmail());
            nPlayer.put("password", player.getPassword());
            nPlayer.put("userID", player.getUserID());
            nPlayer.put("userImage", player.getUserImage().toString());
            nPlayer.put("playerHistory", getPlayerUserFriendIDsAsJSONArray(player));
            nPlayer.put("friendUserIDs", getPlayerHistoryAsJSONArray(player));
            toWrite = newRead().accumulate("players", nPlayer);
            enterData();
        } catch (JSONException | ParseException | IOException | UserInfoException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(Player player) throws PlayerMapperException {
        newDataEntry();
        try {
            JSONArray arr = newRead().getJSONArray("players");
            for (int i = 0; i < arr.length(); i++) {
                if (arr.getJSONObject(i).getInt("userID") == player.getUserID()) {
                    JSONObject nPlayer = arr.getJSONObject(i);
                    nPlayer.put("userName", player.getUserName());
                    nPlayer.put("email", player.getEmail());
                    nPlayer.put("password", player.getPassword());
                    nPlayer.put("userID", player.getUserID());
                    nPlayer.put("userImage", player.getUserImage().toString());
                    nPlayer.put("playerHistory", getPlayerUserFriendIDsAsJSONArray(player));
                    nPlayer.put("friendUserIDs", getPlayerHistoryAsJSONArray(player));
                    toWrite = newRead().accumulate("players", nPlayer);
                    enterData();
                }
            }
        } catch (JSONException | ParseException | IOException | UserInfoException e){
            e.printStackTrace();
        }
    }

    private JSONArray getPlayerHistoryAsJSONArray(Player player) throws JSONException, IOException, ParseException {
        JSONArray array = new JSONArray();
        for (GameSession gs: player.getPlayerHistory()){
            JSONObject nGs = new JSONObject();
            nGs.put("gameID", "" + gs.getDate().getYear() + "-" + gs.getDate().getMonthValue() + "-" + gs.getDate().getDayOfMonth() + "S" + newGameSessionNumber());
            nGs.put("score", gs.getScore());
            nGs.put("gameType", gs.getGameType());
        //    nGs.put("time", gs.getTime());
            array.put(nGs);
        }
        return array;
    }

    private JSONArray getPlayerUserFriendIDsAsJSONArray(Player player) {
        JSONArray array = new JSONArray();
        for (int i: player.getFriendUserIDs()){
            array.put(i);
        }
        return array;
    }

    /**
     * För att logga in på befintligt konto.
     *
     *
     * @param userName
     * @param password
     */
    public void logIn(String userName, String password) throws PlayerMapperException {
        Player player;

        player = findPlayerFromStringField("userName", userName, false);

        player.isPasswordCorrect(password);
       // notifyListenersLogin();
        currentPlayer = player;
    }

    public void changeEmail(String password, String email) throws UserInfoException {
        currentPlayer.setEmail(password, email);
        update(currentPlayer);
    }

    public void changePassword(String oldPassword, String newPassword) throws UserInfoException {
        currentPlayer.setPassword(oldPassword, newPassword);
        update(currentPlayer);
    }

    public void changeUserImage(Image image){
        currentPlayer.setUserImage(image);
        update(currentPlayer);
    }





    /**
     * För att skapa nytt konto och logga in.
     */
    public void createNewAccountAndLogIn(String email, String password, String userName) throws UserInfoException {
        //check account creation conditions before creating account.
        Player.passwordSafetyCheck(password);
        if (isUserName(userName)) throw new PlayerMapperException("Username already taken!");
        //ask if they want email login help?
        if (isUserName(email)) throw new PlayerMapperException("Email is already being used!");
        currentFriendID++;
        Player player = Player.makePlayer(currentFriendID, email, password, userName);
        insert(player);
    }

    private void newDataEntry(){
        toWrite = new JSONObject();
    }

    private void enterData() throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(dbPath);
        pw.write(toWrite.toString());
        pw.flush();
        pw.close();
        toWrite = null;
    }

    private JSONObject newRead() throws IOException, ParseException {
        Object obj = new JSONParser().parse(new FileReader(dbPath));
        return (JSONObject) obj;
    }


    //this and also findgame needs to iterateish
    private Player findPlayerFromStringField(String type, String value, boolean restricted) {
        //fult? alltså hela jävla metoden
        if (!(type.equals("userName") || type.equals("email"))){
            throw new PlayerMapperException("String type is not 'userName', 'userID' nor 'email'");
        }
        try {
            JSONArray arr = newRead().getJSONArray("players");
            for (int i = 0; i < arr.length(); i++){
                JSONObject player = arr.getJSONObject(i);
                if (player.getString(type).equals(value)){
                    return fetchPlayer(player, restricted);
                }
            }
        } catch (ParseException | IOException | JSONException | UserInfoException e) {
            e.printStackTrace();
        }
        throw new PlayerMapperException("A matching Player could not be found");
    }



    private Player fetchPlayer(JSONObject player, boolean restricted) throws JSONException, UserInfoException {
        int userKey;
        int userID;
        String email = null;
        String password = null;
        String userName;
        Image userImage;
        List<Integer> friendUserIDs = new ArrayList<>();
        List<GameSession> playerHistory;
        if(!restricted){
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
        return Player.makePlayer(userID, email, password, userName, userImage, playerHistory, friendUserIDs);
    }

    private List<Integer> fetchFriendUserIDs (JSONObject player) throws JSONException {
        List<Integer> friendUserIDs = new ArrayList<>();
        JSONArray friendUserIDsPRE = player.getJSONArray("friendUserIDs");
        for (int o = 0; o < friendUserIDsPRE.length(); o++){
            friendUserIDs.add((Integer)friendUserIDsPRE.get(o));
        }
        return friendUserIDs;
    }

    private List<GameSession> fetchPlayerHistory (JSONObject player) throws JSONException {
        List<GameSession> playerHistory = new ArrayList<>();
        JSONArray playerHistoryPRE = player.getJSONArray("playerHistory");
        for (int o = 0; o < playerHistoryPRE.length(); o++){
            String idToFormat = playerHistoryPRE.getJSONObject(o).getString("gameID");
            int gameID = Integer.parseInt(idToFormat.substring(0, idToFormat.indexOf("S")));
            int score = playerHistoryPRE.getJSONObject(o).getInt("score");
            String gameType = playerHistoryPRE.getJSONObject(o).getString("gameType");
            LocalDate date = LocalDate.parse(idToFormat.substring(idToFormat.indexOf("S") + 1));
            //String time = playerHistoryPRE.getJSONObject(o).getString("time");
            playerHistory.add(new GameSession(gameID, score, gameType, date));
        }
        return playerHistory;
    }

    /*
    public GameSession getGameSession(int gameID){
        JSONObject db = newRead();
        newRead(
    }
*/

    //completely seperate getters? yh

//?

    //TODO
    private boolean isUserName(String userName) {
        return true;
    }

    public boolean isEmail(String email) {
        return true;
    }

    public void storeGameSession(int score, String gameType) throws ParseException, JSONException, IOException {
        LocalDate date = LocalDate.now(TimeZone.getTimeZone(currentPlayer.getTimeZone()).toZoneId());
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        try {
            JSONArray arr = newRead().getJSONArray("players");
            JSONObject player;
            for (int i = 0; i < arr.length(); i++) {
                player = arr.getJSONObject(i);
                if (player.getInt("userID") == currentPlayer.getUserID()) {
                    break;
                }
            }
        } catch (JSONException | IOException | ParseException e){
            e.printStackTrace();
        }

        GameSession gameSession = new GameSession(newGameSessionNumber(), score, gameType, date);
        currentPlayer.addGameSession(gameSession);
        //store in database
        update(currentPlayer);
    }

    private int newGameSessionNumber() throws IOException, ParseException, JSONException {
        return newRead().getInt("lastGameSessionID") + 1;
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

    public void addFriend(int userID) throws UserInfoException {
        if(isFriend(userID)){
            throw new PlayerMapperException("Already a friend!");
        }
        Optional<Player> friend = find(userID);
        if (!friend.isPresent()){
            throw new PlayerMapperException("No user with given userID");
        }
        currentPlayer.addFriend(userID);
        update(currentPlayer);
    }

    public void removeFriend(int userID) {
        if(!isFriend(userID)){
            throw new PlayerMapperException("Already not a friend!");
        }
        try {
            currentPlayer.removeFriend(userID);

        } catch (UserInfoException e){
            e.printStackTrace();
        }
        update(currentPlayer);
    }

    public boolean isFriend(int userID){
        return currentPlayer.isFriend(userID);

    }

    /**
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
**/
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
