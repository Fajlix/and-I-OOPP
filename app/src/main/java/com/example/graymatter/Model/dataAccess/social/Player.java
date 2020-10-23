package com.example.graymatter.Model.dataAccess.social;


import java.util.ArrayList;
import java.util.List;

/**
 * @author Aline
 * Representing a player of the game. Aggregates additional class userInfo with information to be reached exclusively by current user.
 * Does not know of database.
 */
public class Player {

    /**
     * Aggregate with additional information private to the user
     */
    //TODO Okay no this fucks with json
    private UserInfo userInfo; //TODO aldo can just be Optional?
    //TODO just say fuck it, design by contract?
    //TODO maybe UserInfo should be private class in Player?

    /**
     * Needed upon registration:
     */
    private String userName;


    private final int userID;

    //TODO sort methods after: userinfo connections, getters, setter, constructors, friend handler, game session handler (first figure out REST structure)

    private String userImage;
    private List<Integer> playerHistory;

    /**
     * Standard constructor for Player. If any argument relating to UserInfo constructor arguments are null, UserInfo will be set to empty Optional.
     * @param userID used to identify the Player. Needs to be provided a unique ID, possibly from a database handler.
     * @param email of the Player as defined by attributed class UserInfo.
     * @param password for the Player as defined by attributed class UserInfo.
     * @param userName representing the Player at login and possibly public showing.
     * @param userImage system aiding String representing how to find image associated with Player. //TODO okay in domain object?
     * @param playerHistory List of integers representing IDs to game sessions, not attributed or otherwise related from this class.
     * @param friendUserIDs List of integers representing userIDs of other Players, befriended with this instance. This class is not responsible for ensuring that this connection is mutual, accepted or otherwise valid.
     * @throws UserInfoException if password argument does not uphold password standards as defined by UserInfo.passwordSafetyCheck.
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
            this.userInfo = new UserInfo(email, password, friendUserIDs);
        } else {
            this.userInfo = null;
        }
    }

    /**
     * Player constructor for cases where user image, player history, friend relationships are unknown, as in suitable for constructing a new account. Directs to standard seven argument constructor.
     * @param userID used to identify the Player. Needs to be provided a unique ID, possibly from a database handler.
     * @param email of the Player as defined by attributed class UserInfo.
     * @param password for the Player as defined by attributed class UserInfo.
     * @param userName representing the Player at login and possibly public showing.
     * @throws UserInfoException if password argument does not uphold password standards as defined by UserInfo.passwordSafetyCheck.
     */
    public Player(int userID, String email, String password, String userName) throws UserInfoException {
        this(userID, email, password, userName, "", new ArrayList<Integer>(), new ArrayList<Integer>());
    }

    /**
     * Copy constructor. Deep copies fields, minimizing risk of double referencing.
     * @param player to copy.
     */
    public Player(Player player) {
        this.userID = player.userID;
        this.userName = player.userName;
        this.userImage = player.userImage;
        this.playerHistory = new ArrayList<>(player.playerHistory);
        if (player.userInfo != null){
            this.userInfo = new UserInfo(player.userInfo);
        } else {
            this.userInfo = null;
        }
    }

    private UserInfo getUnwrappedUserInfo() throws UserInfoException {
        if(userInfo != null){
            return userInfo;
        }
        throw new UserInfoException("Not allowed to get private information from other than current user");
    }

    /**
     * Method for changing Player instance´s password. Directs to method in UserInfo.
     * @param oldPassword needed to verify the legality of the action.
     * @param newPassword for the Player as defined by attributed class UserInfo.
     * @throws UserInfoException if newPassword argument does not uphold password standards as defined by UserInfo.passwordSafetyCheck.
     */
    public void setPassword(String oldPassword, String newPassword) throws UserInfoException {
        getUnwrappedUserInfo().setPassword(oldPassword, newPassword);
    }

    /**
     * Checks if password is identical to Player´s current password, in attribute userInfo. Directs to method in UserInfo.
     * @param password checked by current password.
     * @return true if password is correct, otherwise false.
     * @throws UserInfoException if Player attribute Optional userInfo is empty, indicating that this is not allowed for this local instance.
     */
    public boolean isPasswordCorrect(String password) throws UserInfoException {
        return getUnwrappedUserInfo().isPasswordCorrect(password);
    }

    /**
     * Checks if argument passwords upholds password standards as defined by UserInfo.passwordSafetyCheck. Directs to method in UserInfo. Exists to avoid outside dependence on class UserInfo.
     * @param password for the Player as defined by attributed class UserInfo.
     */
    public static void passwordSafetyCheck(String password) {
        UserInfo.passwordSafetyCheck(password); //TODO is this really needed? check where it is used
    }

    /**
     * Method for changing Player instance´s email. Directs to method in UserInfo.
     * @param password needed to verify the legality of the action.
     * @param email new email of the Player as defined by attributed class UserInfo.
     * @throws UserInfoException if Player attribute Optional userInfo is empty, indicating that this is not allowed for this local instance.
     */
    public void setEmail(String password, String email) throws UserInfoException {
        getUnwrappedUserInfo().setEmail(password, email);
    }

    /**
     * Returns the email of the Player.
     * @return email of the Player.
     * @throws UserInfoException if Player attribute Optional userInfo is empty, indicating that this is not allowed for this local instance.
     */
    public String getEmail() throws UserInfoException {
        return getUnwrappedUserInfo().getEmail();
    }

    /**
     * Returns the userID of the Player.
     * @return userID of the Player.
     */
    public int getUserID(){
        return userID;
    }

    /**
     * Method for changing Player instance´s userImage String.
     * @param image system aiding String representing how to find image associated with Player.
     */
    public void setUserImage(String image){
        this.userImage = image;
    }

    /**
     * Returns the String representing an aid in finding image associated with the Player.
     * @return String representing the user image.
     */
    public String getUserImage(){
        return userImage;
    }

    /**
     * Equals-method overriding equals() in Object. Checks if o is equal to this instance.
     * @param o any object, not necessarily another Player.
     * @return true if o is of Player type, this and o have matching userIDs and this attribute userInfo matches argument userInfo (since instances with matching IDs can be representations of same Player with different access allowances, meaning userInfo can be empty or not empty).
     */
    @Override
    public boolean equals(Object o){
        if (this == o){
            return true;
        }
        if (o == null
        || getClass() != o.getClass()){
            return false;
        }
        Player oP = (Player) o;
        /*if (!this.userInfo.equals(oP.userInfo)){
            return false; //TODO not sure if it should still return true
        }*/
        return (oP.userID != 0 && userID == oP.userID);
    }

    /**
     * @return a deepcopy of this instance´s playerHistory, a List of integers representing IDs to game sessions, not attributed or otherwise related from this class.
     */
    public List<Integer> getPlayerHistory() {
       // List<Integer> nL = new ArrayList<>();
       // Collections.copy(nL, this.playerHistory);
        return new ArrayList<>(this.playerHistory);
    }

    /**
     * Adds a friend to userInfo´s attributed List of userIDs representing userIDs of other Players, befriended with this instance. This method nor class is not responsible for ensuring that this connection is mutual, accepted or otherwise valid.
     * @param userID of Player this instance wishes to befriend.
     * @throws UserInfoException if Player attribute Optional userInfo is empty, indicating that this is not allowed for this local instance.
     */
    public void addFriend(int userID) throws UserInfoException {
        getUnwrappedUserInfo().addFriend(userID);
    }

    /**
     * Checks if argument userID represents a friend of this instance (is listed as a friend by this instance). This method nor class is not responsible for ensuring that this connection is mutual, accepted or otherwise valid.
     * @param userID of Player to check.
     * @return true if userInfo field friendUserIDs contain argument userID.
     * @throws UserInfoException if Player attribute Optional userInfo is empty, indicating that this is not allowed for this local instance.
     */
    public boolean isFriend(int userID) throws UserInfoException {
        return getUnwrappedUserInfo().isFriend(userID);
    }

    /**
     * Returns the username of this Player. Intended for login and public showing. Directs to method in UserInfo. Login is not handled by Player nor UserInfo.
     * @return the username of this Player.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Returns a List of integers representing Players this Player has befriended. Specific method isFriend checks whether a specific userID is in this list. This method nor class is not responsible for ensuring that this connection is mutual, accepted or otherwise valid.
     * This method is fitting when needing to list friends, to check for specific singular relationships more suitable methods exist. //TODO maybe there shouldn't, here
     * @return a List of integers representing Players this Player has befriended.
     * @throws UserInfoException if Player attribute Optional userInfo is empty, indicating that this is not allowed for this local instance.
     */
    public List<Integer> getFriendUserIDs() throws UserInfoException {
        return getUnwrappedUserInfo().getFriendUserIDs();
    }

    /**
     * Removes a friend from userInfo´s attributed List of userIDs representing userIDs of other Players, befriended with this instance. This method nor class is not responsible for ensuring that this connection is mutual, accepted or otherwise valid.
     * @param userID of Player this instance wishes to unfriend.
     * @throws UserInfoException if Player attribute Optional userInfo is empty, indicating that this is not allowed for this local instance.
     */
    public void removeFriend(int userID) throws UserInfoException{
        getUnwrappedUserInfo().removeFriend(userID);
    }

    //TODO necessary when all is public?
    /**
     * Checks if email is identical to Player´s current email, in attribute userInfo. Directs to method in UserInfo.
     * @param email checked by current email.
     * @return true if email matches, otherwise false.
     * @throws UserInfoException if Player attribute Optional userInfo is empty, indicating that this is not allowed for this local instance.
     */
    public boolean sameEmail(String email) throws UserInfoException {
        return getUnwrappedUserInfo().sameEmail(email);
    }

    /**
     * Add gameID to playerHistory, List representing played game sessions, ot attributed or otherwise related from this class.
     * @param gameID representing a gameSession.
     */
    public void addGameID(int gameID){
        playerHistory.add(gameID);
    }

    /**
     * Removes non-public information about a user. Suitable for when exposing the Player to another Player.
     */
    public void deActUserInfo() {
        userInfo = null;
    }

}
