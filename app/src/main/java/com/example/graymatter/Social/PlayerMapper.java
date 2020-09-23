package com.example.graymatter.Social;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;


import org.json.*;
import org.json.simple.parser.JSONParser;


public class PlayerMapper implements PlayerMapperInterface {
    String serverLocation;
    JSONObject jsonObject;

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
        currentFriendID = 0;
        listeners = new ArrayList<>();
    }

    @Override
    public Optional<Player> find(int friendID) {

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
     * Necessary to run before trying to access the Player database.
     */
    public void connectToServer() {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("testPlayer.json"));
            jsonObject = (JSONObject) obj;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * För att logga in på befintligt konto.
     *
     * @param userKey
     * @param friendID
     */
    public void logIn(int userKey, int friendID) throws PlayerMapperException {
        Optional<Player> temp = find(friendID);
        if (temp.isPresent()) {
            currentPlayer = temp.get();
        } else {
            throw new PlayerMapperException("FriendID does not match user in our database");
        }

        notifyListenersLogin();

    }

    /**
     * För att skapa nytt konto och logga in.
     */
    public void createNewAccountAndLogIn() {


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
            Player player = Player.makePublicPlayer(friendID, userName, userImage, playerHistory);
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
