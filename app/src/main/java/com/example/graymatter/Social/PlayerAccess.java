package com.example.graymatter.Social;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class provides client access to Player. Coordinates Player parameters with database.
 */
public class PlayerAccess {

    //can be static?
    public PlayerMapper playerMapper;
    public Player currentPlayer;


    public PlayerAccess(String jsonStr){
        playerMapper = new PlayerMapper(jsonStr);
        Optional<Player> optionalPlayer = playerMapper.find(15);
        if (optionalPlayer.isPresent()){
            currentPlayer = optionalPlayer.get();
        } else {
            currentPlayer = null; //TODO mmm
        }
        updatePlayer();
    }


    //Concerns currentPlayer and the existance of the user
    /**
     * To log into existing account.
     * @param userName The user´s username.
     * @param password The user´s password. Does not have to fulfill current password requirements.
     */
    public void logIn(String userName, String password) throws DataMapperException {
        //might be highly unnecessary
        Optional<Player> player = Optional.empty();
        DataBaseModel nDb = null;
        player = findByUserName(userName);
        if (!player.isPresent()){
            throw new DataMapperException("Wrong username!");
        }
        if (!player.get().isPasswordCorrect(password)){
            throw new DataMapperException("Wrong password!"); //should possibly be userinfoexception
        }
        currentPlayer = player.get();
    }

    /**
     * To create a new account and log in. No public method to only create the user as only creation is not desired.
     * @param email A legal email.
     * @param password A password with requirements as defined by social.userInfo.passwordNegativeFeedback().
     * @param userName A not yet taken username.
     */
    public void createNewAccountAndLogIn(String email, String password, String userName) throws UserInfoException {
        //check account creation conditions before creating account.
        Player.passwordSafetyCheck(password);
        if (isUserName(userName)) throw new DataMapperException("Username already taken!");
        //ask if they want email login help?
        if (isEmail(email)) throw new DataMapperException("Email is already being used!");


        Player player = Player.makePlayer(getNewUserID(), email, password, userName);
        playerMapper.insert(player);
        logIn(userName, password);
    }

    /**
     * Log out the player.
     */
    public void logOut() {
        currentPlayer = null;  //näej pissdålig idé
    }

    public void deleteAccount(String password){
        currentPlayer.isPasswordCorrect(password);
        playerMapper.delete(currentPlayer);
        currentPlayer = null;
        //TODO write currentPlayer locally somehow
    }

    //Checks database for different parameters

    private Optional<Player> findByUserName(String userName) {
        for (Player p :playerMapper.get()){
            if(p.getUserName().equals(userName)){
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    private Optional<Player> findByEmail(String email) {
        for (Player p :playerMapper.get()){
            if(p.getUserName().equals(email)){
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    /**
     * Checks whether a username is taken or not.
     * @param userName to check.
     * @return true if username is taken.
     */
    public boolean isUserName(String userName) {
        for (Player p: playerMapper.get()) {
            if (p.getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether an email is in use or not. If it is, user should be helped to access the existing account (email verification or similar).
     * @param email to check
     * @return true if email is already in use.
     */
    public boolean isEmail(String email) {
        for (Player p:playerMapper.get()) {
            //dedicated method sameEmail because of privacy concerns in Player
            if(p.sameEmail(email)){
                return true;
            }
        }
        return false;
    }

    public boolean isFriend(int userID){
        return currentPlayer.isFriend(userID);
    }

    /**
     *
     * @return true if anyone is logged in to the app.
     */
    public boolean isLoggedIn() {
        return (currentPlayer != null);
    }

    private int getNewUserID() {
        int topUserID = 0;
        for (Player p: playerMapper.get()){ //should throw exception if empty?
            if (p.getUserID() > topUserID){
                topUserID = p.getUserID();
            }
        }
        return topUserID + 1;
    }

    //user-specific getters
    public String getEmail() throws UserInfoException {
        return currentPlayer.getEmail();
    }

    //Changes Player parameters

    /**
     * Only way to change email. Changes email for logged in user.
     * @param password requires correct password for safety concerns.
     * @param email must be a legal email.
     * @throws UserInfoException if password is incorrect: "Current password is incorrect", if new email is illegal: *fix*
     */
    public void changeEmail(String password, String email) throws UserInfoException {
        currentPlayer.setEmail(password, email);
        playerMapper.update(currentPlayer);
    }

    /**
     * Only way to change password. Changes password for logged in user.
     * @param oldPassword requires correct password for safety concerns. Must not fulfill quality checks.
     * @param newPassword new password, must fulfill quality checks as defined by social.userInfo.passwordNegativeFeedback().
     * @throws UserInfoException if old passowrd is incorrect: "Current password is incorrect", if new password is unfit: *fix*
     */
    public void changePassword(String oldPassword, String newPassword) throws UserInfoException {
        currentPlayer.setPassword(oldPassword, newPassword);
        playerMapper.update(currentPlayer);
    }

    /**
     * Only way to change userimage. Changes userimage for logged in user.
     * @param image new userimage.
     */
    public void changeUserImage(String image){
        currentPlayer.setUserImage(image);
        playerMapper.update(currentPlayer);
    }

    //Related to friends

    public void addFriend(int userID) throws UserInfoException {
        if(isFriend(userID)){
            throw new DataMapperException("Already a friend!");
        }
        Optional<Player> friend = playerMapper.find(userID);
        if (!friend.isPresent()){
            throw new DataMapperException("No user with given userID");
        }
        currentPlayer.addFriend(userID);
        friend.get().addFriend(currentPlayer.getUserID());
        playerMapper.update(currentPlayer);
        playerMapper.update(friend.get());
    }

    public void removeFriend(int userID) {
        //check if the relationship even exists. Throw exception since isFriend == true indicated frontend error.
        if(!isFriend(userID)){
            throw new DataMapperException("Already not a friend!");
        }
        //removes friend from user´s friend list
        try {
            currentPlayer.removeFriend(userID);
        } catch (UserInfoException e){
            e.printStackTrace();
        }
        playerMapper.update(currentPlayer);
        //removes user from friend´s friend list
        Optional<Player> friend = playerMapper.find(userID);
        if (!friend.isPresent()){
            throw new DataMapperException("No user with given userID");
        }
        try {
            friend.get().removeFriend(currentPlayer.getUserID());
        } catch (UserInfoException e) {
            e.printStackTrace();
        }
        playerMapper.update(friend.get());
    }

    public List<Player> getFriends(){
        List<Player> friends = new ArrayList<>();
        for (int friendID : currentPlayer.getFriendUserIDs()) {
            Optional<Player> friend = playerMapper.find(friendID);
            if(!friend.isPresent()){
                //should you throw other class´ exception?
                updatePlayer();
            } else {
                friends.add(friend.get());
            }
        }
        return friends;
    }

    private void updatePlayer(){
        //TODO friend list should update at app startup
    }

    //related to GameSessions (though only by ID)

    protected void storeGameID(int gameID) {
        currentPlayer.addGameID(gameID);
        playerMapper.update(currentPlayer);
    }
}
