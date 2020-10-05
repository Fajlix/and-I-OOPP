package com.example.graymatter.Social;

import java.io.IOException;
import java.util.Optional;

/**
 * Class provides client access to Player. Coordinates Player parameters with database.
 */
public class PlayerAccess {

    //can be static?
    PlayerMapper playerMapper;
    Player currentPlayer;


    public PlayerAccess(String dbPath){
        playerMapper = new PlayerMapper(dbPath);
        //TODO get current player locally somehow
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
        try {
            for (Player p: playerMapper.get()) {
                if (p.getUserName().equals(userName)) {
                    return true;
                }
            }
        } catch ( IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Checks whether an email is in use or not. If it is, user should be helped to access the existing account (email verification or similar).
     * @param email to check
     * @return true if email is already in use.
     */
    public boolean isEmail(String email) {
        try {
            for (Player p:playerMapper.get()) {
                //dedicated method sameEmail because of privacy concerns in Player
                if(p.sameEmail(email)){
                    return true;
                }
            }
        } catch ( IOException e) {
            e.printStackTrace();
        }
        return false;
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
}
