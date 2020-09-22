package com.example.graymatter.Social;


import android.media.Image;
import android.provider.ContactsContract;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Player {

    //tveksam lösning
    private static String defaultPath = "";
    //  private UserInfo userInfo;
    /**
     * Needed upon registration:
     */
    private String userName;
    private String email;
    private String password;

    private int friendID;
    /**
     *
     */
    private int userKey;


    //tänk på bilderna!!!
    private String imagePath;
    private Image userImage;
    private List<GameSession> playerHistory;
    private List<Integer> friendIDs;
    //databaser??

    private Player(){

    }

    private Player(int userKey, int friendID, String email, String password, String userName, String imagePath, Image userImage, List<GameSession> playerHistory, List<Integer> friendIDs) throws MissingAccessException {
        this(friendID, userName, imagePath, userImage, playerHistory, friendIDs);
        this.userKey = userKey;
        this.email = email;
        this.password = password;
    }

    private Player(int friendID, String userName, String imagePath, Image userImage, List<GameSession> playerHistory, List<Integer> friendIDs) throws MissingAccessException {
        //assign public fields, mark others "0"
        this.friendID = friendID;
        this.userName = userName;
        /*
        this.userImage = null;
        try {
            userImage = ImageIO.read(new File("strawberry.jpg"));
        } catch (IOException e) {
            userImage = ImageIO.read(new File(defaultPath));
        }
         **/
        this.playerHistory = playerHistory;
        this.friendIDs = friendIDs;
    }

    public Player (Player player) throws MissingAccessException {
        this(player.userKey, player.friendID, player.email, player.password, player.userName, player.imagePath, player.userImage, player.playerHistory, player.friendIDs);
    }

    public static Player makePlayer(int userKey, int friendID, String email, String password, String userName, String imagePath, Image userImage, List<GameSession> playerHistory, List<Integer> friendIDs) throws MissingAccessException {
        return new Player(userKey, friendID, email, password, userName, imagePath, userImage, playerHistory, friendIDs);

    }

    public static Player makePublicPlayer(int friendID, String userName, String imagePath, Image userImage, List<GameSession> playerHistory, List<Integer> friendIDs) throws MissingAccessException {
        return new Player(friendID, userName, imagePath, userImage, playerHistory, friendIDs);
    }


    public Player getPlayer(int friendID, int userKey) throws MissingAccessException {
        if(this.userKey == userKey){
            throw new MissingAccessException("Userkey does not match");
        }
        return new Player(this);
    }

    public List<Integer> getFriendIDs(){
        return friendIDs;
    }

    public void setPassword(String oldPassword, String newPassword, int userKey) {
        if(this.userKey==userKey && this.password.equals(oldPassword)) this.password = newPassword;
    }

    public void setEmail(String email, int userKey) {
        if(this.userKey==userKey) this.email = email;
    }

    public String getEmail(String email, int userKey) throws MissingAccessException {
        if(this.userKey==userKey) return this.email;
        throw new MissingAccessException();
    }

    public int getFriendID(){
        return friendID;
    }


    public Image getUserImage(){
        return userImage;
    }

    //needs equals

    public void changeUserKey(int oldUserKey, int newUserKey){

    }



    private static class MissingAccessException extends Exception {
        public MissingAccessException(String exceptionLabel){
            super(exceptionLabel);
        }

        public MissingAccessException(){
            super();
        }
    }

    public List<GameSession> getPlayerHistory() {
        return playerHistory;
    }
}
