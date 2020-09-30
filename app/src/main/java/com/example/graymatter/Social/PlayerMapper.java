package com.example.graymatter.Social;

import android.media.Image;
import android.net.ParseException;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.TimeZone;





public class PlayerMapper implements PlayerMapperInterface {
    private String serverLocation;
    private final String dbPath = "C:/Users/hanna/Documents/and-I-OOPP/app/src/main/java/com/example/graymatter/Social/testPlayers.json";
    //good for batch writing. bad for safety. idk
    private dbModel toWrite;

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
            dbModel obj =  newRead();

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
        throw new PlayerMapperException("No Player matching userID");
    }

    @Override
    public void delete(Player player) throws PlayerMapperException {
        newDataEntry();
        try {
            dbModel nDb = newRead();
            List<Player> arr = nDb.getPlayers();
            for (Player p: arr) {
                if (p.getUserID() == player.getUserID()) {
                    if (!arr.remove(p)){
                        throw new PlayerMapperException("Player not present");
                    }
                    nDb.setPlayers(arr);
                    toWrite = nDb;
                    enterData();
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void insert(Player player) throws PlayerMapperException {
        newDataEntry();
        try {

            dbModel dbModel = newRead();
            dbModel.getPlayers().add(player);
            toWrite = dbModel;
            enterData();
        } catch (IOException | ParseException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(Player player) throws PlayerMapperException {
        newDataEntry();
        try {
            dbModel nDb = newRead();
            List<Player> arr = nDb.getPlayers();
            for (Player p : arr) {
                if (p.getUserID() == player.getUserID()) {
                    nDb.getPlayers().add(p);
                    toWrite = nDb;
                    enterData();
                }
            }
        } catch (IOException | ParseException e){
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
        //might be highly unnecessary
        Optional<Player> player = Optional.empty();
        try {
            dbModel nDb = newRead();
            for (Player p : nDb.getPlayers()){
                if (p.getUserName().equals(userName)){
                    player = Optional.of(p);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!player.isPresent()){
            throw new PlayerMapperException("Wrong username!");
        }
       // player = findPlayerFromStringField("userName", userName, false);

        player.get().isPasswordCorrect(password);
       // notifyListenersLogin();
        currentPlayer = player.get();
    }

    public void changeEmail(String password, String email) throws UserInfoException {
        currentPlayer.setEmail(password, email);
        update(currentPlayer);
    }

    public void changePassword(String oldPassword, String newPassword) throws UserInfoException {
        currentPlayer.setPassword(oldPassword, newPassword);
        update(currentPlayer);
    }

    public void changeUserImage(String image){
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
        if (isEmail(email)) throw new PlayerMapperException("Email is already being used!");
        currentFriendID++;
        Player player = Player.makePlayer(currentFriendID, email, password, userName);
        currentPlayer = player;
        insert(player);
    }

    private void newDataEntry(){
        toWrite = new dbModel();
    }

    private void enterData() throws FileNotFoundException {
        Gson gson = new Gson();
        try {
            gson.toJson(toWrite, new FileWriter(dbPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*PrintWriter pw = new PrintWriter(dbPath);
        pw.write(toWrite.toString());
        pw.flush();
        pw.close();*/
        toWrite = null;
    }


    private dbModel newRead() throws IOException {
        Gson gson = new Gson();
        Reader reader = new FileReader("C:/Users/hanna/Documents/and-I-OOPP/app/src/main/java/com/example/graymatter/Social/testPlayers.json");
        return gson.fromJson(reader, dbModel.class);
    }


/*
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
        } catch ( IOException | JSONException | UserInfoException e) {
            e.printStackTrace();
        }
        throw new PlayerMapperException("A matching Player could not be found");
    }
*/

/*
    private Player fetchPlayer(JSONObject player, boolean restricted) throws JSONException, UserInfoException {
        int userKey;
        int userID;
        String email = null;
        String password = null;
        String userName;
        String userImage;
        List<Integer> friendUserIDs = new ArrayList<>();
        List<GameSession> playerHistory;
        if(!restricted){
            email = player.getString("email");
            password = player.getString("password");
            friendUserIDs = fetchFriendUserIDs(player);
        }

        userImage = player.getString("userImage");
        userID = player.getInt("userID");
        userName = player.getString("userName");
        playerHistory = fetchPlayerHistory(player);
        //close read?
        if (restricted) return Player.makePlayer(userID, userName, userImage, playerHistory);
        return Player.makePlayer(userID, email, password, userName, userImage, playerHistory, friendUserIDs);
    }
*/
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
        try {
            dbModel nDb = newRead();
            for (Player p: nDb.getPlayers()) {
                if (p.getUserName().equals(userName)) {
                    return true;
                }
            }
        } catch ( IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isEmail(String email) {
        try {
            dbModel nDb = newRead();
            for (Player p: nDb.getPlayers()) {
                if(p.sameEmail(email)){
                    return true;
                }
            }
        } catch ( IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void storeGameSession(int score, String gameType) throws ParseException, JSONException, IOException {
        LocalDate date = LocalDate.now(TimeZone.getTimeZone(currentPlayer.getTimeZone()).toZoneId());
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        dbModel nDb;
        try {
            nDb = newRead();

            for (Player p : nDb.getPlayers()) {
                if (p.getUserID() == currentPlayer.getUserID()) {
                    break;
                }
            }
            GameSession gameSession = new GameSession(newGameSessionNumber(), score, gameType, date);
            currentPlayer.addGameSession(gameSession);
            nDb.incLastGameSessionNumber();
            //store in database
            newDataEntry();
            toWrite = nDb;
            enterData();
            update(currentPlayer);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private int newGameSessionNumber() throws IOException, ParseException, JSONException {
        dbModel nDb = newRead();
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

    public boolean isLoggedIn() {
        return (currentPlayer != null);
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
