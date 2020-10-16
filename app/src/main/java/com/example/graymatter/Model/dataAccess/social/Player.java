package com.example.graymatter.Model.dataAccess.social;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Representing a player of the game. Aggregates additional class userInfo with information to be reached exclusively by current user.
 * Does not know of database.
 */
public class Player {

    /**
     * Aggregate with additional information private to the user. Reached with correct userKey.
     */
    //TODO Okay no this fucks with json
    private Optional<UserInfo> userInfo; //TODO aldo can just be Optional?
    /**
     * Needed upon registration:
     */
    private String userName;


    private final int userID;

    //TODO sort methods after: userinfo connections, getters, setter, constructors, friend handler, game session handler (first figure out REST structure)


    private String userImage;
    private List<Integer> playerHistory;

    /**constructor for Player with private userInfo
     */
    public Player(int userID, String email, String password, String userName, String userImage, List<Integer> playerHistory, List<Integer> friendUserIDs) throws UserInfoException {
        this.userID = userID;
        this.userName = userName;
        if(userImage == null){
            this.userImage = "";
        } else {
            this.userImage = userImage;
        }
        this.playerHistory = playerHistory;
        //if fields for UserInfo are null, the UserInfo Optional should be empty
        if(email != null && password != null && friendUserIDs != null){
            this.userInfo = Optional.of(new UserInfo(email, password, friendUserIDs));
        } else {
            this.userInfo = Optional.empty();
        }
    }

    public Player(int newUserID, String email, String password, String userName) throws UserInfoException {
        this(newUserID, email, password, userName, null, null, null);
    }

    public Player(Player player) {
        this.userID = player.userID;
        this.userName = player.userName;
        this.userImage = player.userImage;
        this.playerHistory = new ArrayList<>(player.playerHistory);
        if (player.userInfo.isPresent()){
            this.userInfo = Optional.of(new UserInfo(player.userInfo.get()));
        } else {
            this.userInfo = Optional.empty();
        }
    }

    private UserInfo getUnwrappedUserInfo() throws UserInfoException {
        if(userInfo.isPresent()){
            return userInfo.get();
        }
        throw new UserInfoException("Not allowed to get private information from other than current user");
    }

    public void setPassword(String oldPassword, String newPassword) throws UserInfoException {
        getUnwrappedUserInfo().setPassword(oldPassword, newPassword);
    }

    public boolean isPasswordCorrect(String password) throws UserInfoException {
        return getUnwrappedUserInfo().isPasswordCorrect(password);
    }

    public static void passwordSafetyCheck(String password) {
        UserInfo.passwordSafetyCheck(password);
    }

    public void setEmail(String password, String email) throws UserInfoException {
        getUnwrappedUserInfo().setEmail(password, email);
    }

    public String getEmail() throws UserInfoException {
        return getUnwrappedUserInfo().getEmail();
    }

    public int getUserID(){
        return userID;
    }

    public void setUserImage(String image){
        this.userImage = image;
    }

    public String getUserImage(){
        return userImage;
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
       // List<Integer> nL = new ArrayList<>();
       // Collections.copy(nL, this.playerHistory);
        return new ArrayList<>(this.playerHistory);
    }

    public void addFriend(int userID) throws UserInfoException {
        getUnwrappedUserInfo().addFriend(userID);
    }

    public boolean isFriend(int userID) throws UserInfoException {
        return getUnwrappedUserInfo().isFriend(userID);
    }

    public String getUserName() {
        return userName;
    }

    /**
     * sorta ugly
     *
     * @return friendUserIDs
     */
    public List<Integer> getFriendUserIDs() throws UserInfoException {
        return getUnwrappedUserInfo().getFriendUserIDs();
    }

    public void removeFriend(int userID) throws UserInfoException{
        getUnwrappedUserInfo().removeFriend(userID);
    }

    public boolean sameEmail(String email) throws UserInfoException {
        return getUnwrappedUserInfo().sameEmail(email);
    }

    /**
     * For storing new games.
     */
    public void addGameID(int gameID){
        playerHistory.add(gameID);
    }

    /**
     * maybe this is a fucking awful idea
     */
    public void deActUserInfo() {
        userInfo = Optional.empty();
    }
}
