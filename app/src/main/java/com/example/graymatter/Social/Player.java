package com.example.graymatter.Social;

import java.awt.Image;
import java.util.List;

public class Player {


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

    private Image userImage;
    private List<GameSession> playerHistory;
    private List<Integer> friendIDs;
    //databaser??

    private Player(){

    }

    private Player(int userKey, int friendID, String email, String password, String userName){

        this.userKey = userKey;
    }

    private Player(int friendID, String userName, String imagePath, List<GameSession> playerHistory){
        //assign public fields, mark others "0"
    }

    public static Player makePlayer(int userKey, int friendID, String email, String password, String userName){

        this.userKey = userKey;
    }

    public static Player makePublicPlayer(int friendID, String userName, String imagePath, List<GameSession> playerHistory){
        return new Player(friendID, userName, imagePath, playerHistory);
    }


    public static Player getPlayer(int friendID, int userKey) implements Throwable{
        if(this.userKey = userKey){
            throw new MissingAccessException("Userkey does not match");
        }
        return Player(userKey, friendID);
    }

    public List<Integer> getFriendIDs(){

    }

    public void setPassword(String oldPassword, String newPassword, int userKey) {
        if(this.userKey==userKey && this.password==oldPassword) this.password = newPassword;
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


    public void changeUserKey(int oldUserKey, int newUserKey){

    }



    private class MissingAccessException extends Exception {
        public MissingAccessException(String exceptionLabel){
            super(exceptionLabel);
        }
    }

    public List<GameSession> getPlayerHistory() {
        return playerHistory;
    }
}
