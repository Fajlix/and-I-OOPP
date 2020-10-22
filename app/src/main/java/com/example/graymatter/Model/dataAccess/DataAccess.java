package com.example.graymatter.Model.dataAccess;

import android.content.Context;
import android.net.ParseException;

import com.example.graymatter.Model.dataAccess.dataMapper.DataMapper;
import com.example.graymatter.Model.dataAccess.dataMapper.DataMapperException;
import com.example.graymatter.Model.dataAccess.dataMapperImplementation.GameSessionMapper;
import com.example.graymatter.Model.dataAccess.dataMapperImplementation.LocalDataMapper;
import com.example.graymatter.Model.dataAccess.dataMapperImplementation.PlayerMapper;
import com.example.graymatter.Model.dataAccess.social.GameSession;
import com.example.graymatter.Model.dataAccess.social.Player;
import com.example.graymatter.Model.dataAccess.social.UserInfoException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Class provides client facade to GameSession and holds instance of other facade PlayerAccess.
 * Coordinates GameSession fields with corresponding databse entries.
 */
public class DataAccess {

    private LocalDataMapper ldm;

    private DataMapper<GameSession> gsMapper;
    public DataMapper<Player> playerMapper;

    /**
     * can be Optional of null, if null - not logged in
     */
    public Optional<Player> currentPlayer;

    public DataAccess(Context context){
        ldm = new LocalDataMapper(context);
        gsMapper = new GameSessionMapper(context);
        playerMapper = new PlayerMapper(context);
        Optional<Player> optionalPlayer = playerMapper.find(ldm.getCurrentPlayerUserID());
        if (optionalPlayer.isPresent()){
            currentPlayer = optionalPlayer;
            try {
                updatePlayer();
            } catch (UserInfoException e) {
                e.printStackTrace();
            }
        } else {
            currentPlayer = Optional.empty();
        }
    }


    public void storeGameSession(int score, String gameType) throws ParseException {
        LocalDate date = LocalDate.now(); //solve timezone stuff
        GameSession gs = new GameSession(getNewGameID(), score, gameType, date);
        storeGameID(gs.getGameID());
        gsMapper.insert(gs);
    }


    /**
     * For package-internal cleaning purposes.
     * @param gameID unique gameID of the GameSession.
     */
    /*
    protected void removeGameSession(int gameID){
        Optional<GameSession> gs = gsMapper.find(gameID);
        if (!gs.isPresent()){
            throw new DataMapperException("gameID does not match GameSession");
        } else {
            gsMapper.delete(gs.get());
        }
    }
    */

    public int getNewGameID() {
        int topGameID = 0;
        for (GameSession g: gsMapper.get()){ //should throw exception if empty?
            if (g.getGameID() > topGameID){
                topGameID = g.getGameID();
            }
        }
        return topGameID + 1;
    }

    public GameSession getGameSession(int gameID) throws DataMapperException{
        Optional<GameSession> opGameSession = gsMapper.find(gameID);
        if(opGameSession.isPresent()){
            return opGameSession.get();
        }
        throw new DataMapperException("gameId does not match GameSession in database.");
    }
/*
    public static Map<Integer, Integer> getAllScoresIdentifiable(){
        Map<Integer, Integer> scores = new HashMap<>();
        for (GameSession gs: gsMapper.get()){
            scores.put(gs.getGameID(), gs.getScore());
        }
        return scores;
    }
*/
    public List<GameSession> getGameSessionsByType(String gameType) {
        List<GameSession> gs = gsMapper.get();
        List<GameSession> nGs = new ArrayList<>();
        for (GameSession session : gs){
            if (session.getGameType().equals(gameType)){
                nGs.add(session);
            }
        }
        return nGs;
    }

    //below strictly PlayerMapper-related methods



    //TODO cleanup gamesessions (remove gamesessions w dead owner

    //Concerns currentPlayer and the existance of the user
    /**
     * To log into existing account.
     * @param userName The user´s username.
     * @param password The user´s password. Does not have to fulfill current password requirements.
     */
    public void logIn(String userName, String password) throws DataMapperException, UserInfoException {
        //might be highly unnecessary
        Optional<Player> player;
        player = findByUserName(userName);
        if (!player.isPresent()){
            throw new DataMapperException("Wrong username!");
        }
        if (!player.get().isPasswordCorrect(password)){
            throw new DataMapperException("Wrong password!"); //should possibly be userinfoexception
        }
        currentPlayer = player;
        ldm.setCurrentPlayerUserID(player.get().getUserID());


    }

    /**
     * To create a new account and log in. No public method to only create the user as only creation is not desired.
     * @param email A legal email.
     * @param password A password with requirements as defined by social.userInfo.passwordNegativeFeedback().
     * @param userName A not yet taken username.
     */
    public void createNewAccountAndLogIn(String email, String password, String userName) throws UserInfoException {
        if(isLoggedIn()){
            throw new DataMapperException("A player is already logged in!");
        }
        //check account creation conditions before creating account.
        Player.passwordSafetyCheck(password);
        if (isUserName(userName)){
            throw new DataMapperException("Username already taken!");
        }
        //ask if they want email login help?
        if (isEmail(email)){
            throw new DataMapperException("Email is already being used!");
        }
        Player player = new Player(getNewUserID(), email, password, userName);
        playerMapper.insert(player);
        logIn(userName, password);
    }

    /**
     * Log out the player.
     */
    public void logOut() {
        currentPlayer = Optional.empty();  //näej pissdålig idé
        ldm.setCurrentPlayerUserID(0);
    }

    public void deleteAccount(String password) throws UserInfoException {
        Player player = getUnwrappedPlayer();
        player.isPasswordCorrect(password);
        playerMapper.delete(player);
        logOut();
    }

    //Checks database for different parameters

    public Optional<Player> findByUserName(String userName) {
        for (Player p :playerMapper.get()){
            if(p.getUserName().equals(userName)){
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    public Optional<Player> findByEmail(String email) {
        for (Player p :playerMapper.get()){
            try {
                if(p.getEmail().equals(email)){
                    return Optional.of(p);
                }
            } catch (UserInfoException e) {
                e.printStackTrace();
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
    public boolean isEmail(String email) throws UserInfoException {
        for (Player p:playerMapper.get()) {
            //dedicated method sameEmail because of privacy concerns in Player
            if(p.sameEmail(email)){
                return true;
            }
        }
        return false;
    }

    public boolean isFriend(int userID) throws UserInfoException {
        Player player = getUnwrappedPlayer();
        return player.isFriend(userID);
    }

    /**
     *
     * @return true if anyone is logged in to the app.
     */
    public boolean isLoggedIn() {
//        Optional<Player> optionalPlayer = playerMapper.find(ldm.getCurrentPlayerUserID());
//        if (optionalPlayer.isPresent()){
//            currentPlayer = optionalPlayer;
//            try {
//                updatePlayer();
//            } catch (UserInfoException e) {
//                e.printStackTrace();
//            }
//        } else {
//            currentPlayer = Optional.empty();
//        }
        return (currentPlayer.isPresent());
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
        Player player = getUnwrappedPlayer();
        return player.getEmail();
    }

    //Changes Player parameters

    /**
     * Only way to change email. Changes email for logged in user.
     * @param password requires correct password for safety concerns.
     * @param email must be a legal email.
     * @throws UserInfoException if password is incorrect: "Current password is incorrect", if new email is illegal: *fix*
     */
    public void changeEmail(String password, String email) throws UserInfoException {
        Player player = getUnwrappedPlayer();
        player.setEmail(password, email);
        playerMapper.update(player);
    }

    /**
     * Only way to change password. Changes password for logged in user.
     * @param oldPassword requires correct password for safety concerns. Must not fulfill quality checks.
     * @param newPassword new password, must fulfill quality checks as defined by social.userInfo.passwordNegativeFeedback().
     * @throws UserInfoException if old passowrd is incorrect: "Current password is incorrect", if new password is unfit: *fix*
     */
    public void changePassword(String oldPassword, String newPassword) throws UserInfoException {
        Player player = getUnwrappedPlayer();
        player.setPassword(oldPassword, newPassword);
        playerMapper.update(player);
    }

    /**
     * Only way to change userimage. Changes userimage for logged in user.
     * @param image new userimage.
     */
    public void changeUserImage(String image){
        Player player = getUnwrappedPlayer();
        player.setUserImage(image);
        playerMapper.update(player);
    }

    //Related to friends

    /**
     *
     * @param userID
     * @throws UserInfoException
     */
    public void addFriend(int userID) throws UserInfoException {
        if(isFriend(userID)){
            throw new DataMapperException("Already a friend!");
        }
        Optional<Player> friend = playerMapper.find(userID);
        if (!friend.isPresent()){
            throw new DataMapperException("No user with given userID");
        }
        Player player = getUnwrappedPlayer();
        player.addFriend(userID);
        friend.get().addFriend(player.getUserID());
        playerMapper.update(player);
        playerMapper.update(friend.get());
    }

    public void removeFriend(int userID) throws DataMapperException{
        //removes friend from user´s friend list
        Player player = getUnwrappedPlayer();
        try {
            player.removeFriend(userID);
        } catch (UserInfoException e){
            e.printStackTrace();
        }
        playerMapper.update(player);
        //removes user from friend´s friend list
        Optional<Player> friend = playerMapper.find(userID);
        if (!friend.isPresent()){
            throw new DataMapperException("No user with given userID");
        }
        try {
            friend.get().removeFriend(player.getUserID());
        } catch (UserInfoException e) { //can't end up here
            e.printStackTrace();
        }
        playerMapper.update(friend.get());
    }

    public List<Player> getFriends() throws UserInfoException {
        Player player = getUnwrappedPlayer();
        List<Player> friends = new ArrayList<>();
        for (int friendID : player.getFriendUserIDs()) {
            Optional<Player> friend = playerMapper.find(friendID);
            if(!friend.isPresent()){
                updatePlayer();
                return getFriends();
            } else {
                friend.get().deActUserInfo();
                friends.add(friend.get());
            }
        }
        return friends;
    }

    /**
     * removes friendUserIDs of deleted accounts
     */
    private void updatePlayer() throws UserInfoException {
        Player player = getUnwrappedPlayer();
        for (int i = 0; i < player.getFriendUserIDs().size(); i++) {
            int no = player.getFriendUserIDs().get(i);
            Optional<Player> friend = playerMapper.find(no);
            if (!friend.isPresent()){
                try {
                    player.removeFriend(no);
                } catch (UserInfoException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //related to GameSessions (though only by ID)

    protected void storeGameID(int gameID) {
        Player player = getUnwrappedPlayer();
        player.addGameID(gameID);
        playerMapper.update(player);
    }

    /**
     *
     * @param gameID to find owner of
     * @return int userID of user who owns input gamesession ID
     */
    public int getGameIDOwner(int gameID) {
        for (Player player :playerMapper.get()){
            if (player.getPlayerHistory().contains(gameID)){
                return player.getUserID();
            }
        }
        return 0;
    }

    public int getPBSize() {
        return playerMapper.get().size();
    }

    private Player getUnwrappedPlayer(){
        if (!currentPlayer.isPresent()){
            throw new DataMapperException("Player not logged in!");
        }
        return currentPlayer.get();
    }

    public Player getCurrentPlayer(){
        if(currentPlayer.isPresent()){
            return new Player(currentPlayer.get());
        }
        throw new DataMapperException("Player not logged in!");
    }




}
