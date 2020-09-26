package com.example.graymatter.Social;


import android.media.Image;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representing a player of the game. Aggregates additional class userInfo with information to be reached exclusively by current user.
 * Does not know of database.
 */
public class Player {

    //tveksam lösning
    private static String defaultPath = "";
    // possibly use userInfo to limit PlayerInfo and to ease privacy issues
    /**
     * Aggregate with additional information private to the user. Reached with correct userKey.
     */
    private UserInfo userInfo;
    /**
     * Needed upon registration:
     */
    private String userName;


    private int userID;
    /**
     *
     */

    //TODO sort methods after: userinfo connections, getters, setter, constructors, friend handler, game session handler (first figure out REST structure)



    //tänk på bilderna!!!
  //  private String imagePath;
    private Image userImage;
    private List<GameSession> playerHistory;

    private Player(){

    }
    //TODO does Player need to deepcopying stuff? Considering the database?

    /**constructor for Player with private userInfo
     * @param userKey
     * @param userID
     * @param email
     * @param password
     * @param userName
     * @param userImage
     * @param playerHistory
     * @param friendUserIDs
     * @throws MissingAccessException
     */
    private Player(int userKey, int userID, String email, String password, String userName, Image userImage, List<GameSession> playerHistory, List<Integer> friendUserIDs) throws MissingAccessException {
        this(userID, userName, userImage, playerHistory);
        this.userInfo = new UserInfo(userKey, email, password, friendUserIDs);
    }

    private Player(int userID, String userName, Image userImage, List<GameSession> playerHistory) {
        //assign public fields, mark others "0"
        this.userID = userID;
        this.userName = userName;
        this.userImage = userImage;
        /*
        try {
            userImage = ImageIO.read(new File("strawberry.jpg"));
        } catch (IOException e) {
            userImage = ImageIO.read(new File(defaultPath));
        }*/

        this.playerHistory = playerHistory;

    }

    //living dangerously
    private Player(Player player){
        this.userInfo = new UserInfo(player.userInfo);
        this.userID = player.userID;
        this.userName = player.userName;
        this.userImage = player.userImage;
        //TODO hmmm
        Collections.copy(this.playerHistory, player.playerHistory);
    }

    // halfmode bc deepy copy needs to be taken in considiration but first: how is this used? privacy issues.
/*
    public Player (Player player) throws MissingAccessException {
        UserInfo userInfo = new UserInfo()
        //mycket fishy med Image nedan, ingen copy
        this(player.userKey, player.userID, player.userName, player.userImage, player.playerHistory);
    }
*/

    // TODO some kind of factory? :/
    //anyways, 1 constructor if many makemethods
    public static Player makePlayer(int userKey, int userID, String email, String password, String userName, Image userImage, List<GameSession> playerHistory, List<Integer> friendUserIDs) throws MissingAccessException {
        return new Player(userKey, userID, email, password, userName, userImage, playerHistory, friendUserIDs);
    }

    public static Player makePlayer(int userKey, int userID, String email, String password, String userName) throws MissingAccessException {
        return new Player(userKey, userID, email, password, userName, userImage, playerHistory, friendUserIDs);
    }

    public static Player makePlayer(int userID, String userName, Image userImage, List<GameSession> playerHistory){
        return new Player(userID, userName, userImage, playerHistory);
    }


    //terrible idea
    /**
     * Returns the player, with userInfo field having UserInfo value if userKey matches, otherwise userInfo field null.
     * @param userKey Dictates the anture of userInfo-field. If not intended to return UserInfo, userKey should be "".
     * @return this Player
     * @throws MissingAccessException if
     */
    /*
    public Player getPlayer(String userKey) throws MissingAccessException {
        if(userKey.equals("")){
            Player player = (this);
            player.userInfo = null;
            return player;
        }
        if(this.userKey == parseUserID(userKey)){
            throw new MissingAccessException("Userkey does not match");
        }
        return this;
    }
    */

    public List<Integer> getFriendUserIDs(int userKey) throws MissingAccessException {
        return userInfo.getFriendUserIDs(userKey);
    }


    public void setPassword(int userKey, String oldPassword, String newPassword) throws MissingAccessException {
        userInfo.setPassword(userKey, oldPassword, newPassword);
    }

    public void setEmail(int userKey, String password, String email) throws MissingAccessException {
        userInfo.setEmail(userKey, password, email);
    }

    public boolean isPasswordCorrect(String password){
        return userInfo.isPasswordCorrect(password);
    }

    public String getEmail(int userKey) throws MissingAccessException {
        return userInfo.getEmail(userKey);
    }

    public static void passwordSafetyCheck(String password) {
        UserInfo.passwordSafetyCheck(password);
    }

    public int getUserID(){
        return userID;
    }


    //TODO Parserthingies

    //Here or in PlayerMapper?
    //also could do more possibly
    /**
     * Parses userIDs to drop beginning zeroes. Parses String to int. Draws absolute from value since used Integer.parseInt() allows *+* and *-*, does not mean that using method with *+* or *-* signs is recommended.
     * @param idToParse String value containing
     * @return int representing a valid userID.
     */
    public static int parseUserIDorKey(String idToParse){
        int i = 0;
        while(!Character.isDigit(idToParse.charAt(i))){
            idToParse = idToParse.substring(1);
        }
        int userID;

        for(char ch: idToParse.toCharArray()){
            if(Character.isAlphabetic(ch)){
                throw new NumberFormatException("Invalid userIDformat");
            }
        }
        return Math.abs(Integer.parseInt(idToParse));
    }

    public Image getUserImage(){
        return userImage;
    }

    public void setUserImage(Image image){
        this.userImage = image;
    }

    //TODO getters, setters or equal for userName, userID, userKey, playerHistory, equals, tostring

    //needs equals






    public List<GameSession> getPlayerHistory() {
        return playerHistory;
    }


    //TODO
    public void changeUserKey(int userKey, int i) {
    }
}
