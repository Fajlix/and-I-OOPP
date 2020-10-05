package com.example.graymatter.Social;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representing a player of the game. Aggregates additional class userInfo with information to be reached exclusively by current user.
 * Does not know of database.
 */
public class Player {

    /**
     * Aggregate with additional information private to the user. Reached with correct userKey.
     */
    private UserInfo userInfo;
    /**
     * Needed upon registration:
     */
    private String userName;


    private int userID;

    //TODO sort methods after: userinfo connections, getters, setter, constructors, friend handler, game session handler (first figure out REST structure)

    //tänk på bilderna!!!
    private String userImage;
   // private Image userImage;
    private List<Integer> playerHistory;

    private Player(){

    }
    //TODO does Player need to deepcopying stuff? Considering the database?

    /**constructor for Player with private userInfo
     */
    private Player(int userID, String email, String password, String userName, String userImage, List<Integer> playerHistory, List<Integer> friendUserIDs) throws UserInfoException {
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
        //dumb?
        if(email != null && password != null && friendUserIDs != null){
            this.userInfo = new UserInfo(email, password, friendUserIDs);
        }
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

    // TODO some kind of factory? :/
    //anyways, 1 constructor if many makemethods
    //used for users
    protected static Player makePlayer(int userID, String email, String password, String userName, String userImage, List<Integer> playerHistory, List<Integer> friendUserIDs) throws UserInfoException {
        return new Player(userID, email, password, userName, userImage, playerHistory, friendUserIDs);
    }

    //used in the creation of new users
    protected static Player makePlayer(int userID, String email, String password, String userName) throws UserInfoException {
        return new Player(userID, email, password, userName, null, new ArrayList<Integer>(), new ArrayList<Integer>());
    }

    //used for non-user players
    protected static Player makePlayer(int userID, String userName, String userImage, List<Integer> playerHistory){
        try {
            return new Player(userID, null, null, userName, userImage, playerHistory, null);
        } catch (UserInfoException e) {
            e.printStackTrace();
        }
        //TODO no
        return null;
    }

    protected void setPassword(String oldPassword, String newPassword) throws UserInfoException {
        userInfo.setPassword(oldPassword, newPassword);
    }

    protected void setEmail(String password, String email) throws UserInfoException {
        userInfo.setEmail(password, email);
    }

    protected boolean isPasswordCorrect(String password){
        return userInfo.isPasswordCorrect(password);
    }

    protected String getEmail() throws UserInfoException {
        return userInfo.getEmail();
    }

    //should be private
    protected static void passwordSafetyCheck(String password) {
        UserInfo.passwordSafetyCheck(password);
    }

    public int getUserID(){
        return userID;
    }



    public String getUserImage(){
        return userImage;
    }

    protected void setUserImage(String image){
        this.userImage = image;
    }

    @Override
    public boolean equals(Object o){
        if (this == o){
            return true;
        }
        if (o == null){
            return false;
        }
        if (getClass() != o.getClass()){
            return false;
        }
        Player p = (Player) o;
        return userID == p.userID;
    }

    public List<Integer> getPlayerHistory() {
        List<Integer> nL = new ArrayList<>();
        Collections.copy(nL, this.playerHistory);
        return nL;
    }

    public void addFriend(int userID) {
        userInfo.addFriend(userID);
    }

    public boolean isFriend(int userID) {
        return userInfo.isFriend(userID);
    }

    public String getUserName() {
        return userName;
    }

    /**
     * sorta ugly
     *
     * @return friendUserIDs
     */
    public List<Integer> getFriendUserIDs() {
        return userInfo.getFriendUserIDs();
    }

    public void removeFriend(int userID) throws UserInfoException{
        userInfo.removeFriend(userID);
    }

    public boolean sameEmail(String email) {
        return userInfo.sameEmail(email);
    }
}
