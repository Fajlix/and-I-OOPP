package com.example.graymatter.Social;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class PlayerMapper implements PlayerMapperInterface {
    Random rand = new Random();
    Player currentPlayer;
    int currentFriendID;
    GameSession currentGameSession;
    //List<Player> friendBuffer;   ??? kanske
    //databasen

    List<PlayerMapperListener> listeners;

    public PlayerMapper (){
        currentFriendID = 0;
        listeners = new ArrayList<>();
    }

    @Override
    Optional<Player> find(int friendID){

    }

    Player makeFriend (int friendID){
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

    public void connectToServer(){
   //     try(ServerSocket serverSocket = new ServerSocket(9991)) {

        //}
    }

    /**
     * För att logga in på befintligt konto.
     * @param userKey
     * @param newPlayer
     */
    public void logIn (int userKey, int friendID) throws PlayerMapperException {
        Optional<Player> temp = find(friendID);
        if (temp.isPresent()){
            currentPlayer = temp.get();
        } else {
            throw new PlayerMapperException("FriendID does not match user in our database");
        }

        notifyListenersLogin();

    }

    /**
     * För att skapa nytt konto och logga in.
     */
    public void createNewAccountAndLogIn(){



    }


    public int makeFriendID(){
        currentFriendID += 1;
        return currentFriendID;
    }

    /**
     *
     * @param userKey Application unique player ID
     */
    public void logOut(int userKey){
        currentPlayer.changeUserKey(userKey, 0);
        currentPlayer = null;  //näej pissdålig idé
        notifyListenersLogout();
    }


    public List<Player> getUserFriends(){
        List<Player> friends = new ArrayList<>();
        for (int friendID:currentPlayer.getFriendIDs()){
            friends.add(makeFriend(friendID));
        }
        return friends;
    }

    //Concerning listeners

    public void addListener(PlayerMapperListener listener){
        listeners.add(listener);
    }

    public void removeListener(PlayerMapperListener listener){
        listeners.remove(listener);
    }

    private void notifyListenersLogin(){
        for (PlayerMapperListener listener:listeners) {
            listener.notifyLogIn();
        }
    }

    private void notifyListenersLogout(){
        for (PlayerMapperListener listener:listeners){
            listener.notifyLogout();
        }
    }
}
