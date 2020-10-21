package com.example.graymatter.Model.dataAccess.social;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Representing private user information. All fields and methods private or protected.
 */
public class UserInfo {


    private String email; //emailSafetyCheck() should be a thing
    private String password;
    private List<Integer> friendUserIDs;

    /**
     * Standard constructor.
     * @param email of the user.
     * @param password as defined by method passwordSafetyCheck.
     * @param friendUserIDs List of integers representing IDs of this users friends. This class is not responsible for ensuring that this connection is mutual, accepted or otherwise valid.
     * @throws UserInfoException is password does not uphold password standards defined by passwordSafetyCheck.
     */
    protected UserInfo(String email, String password, List<Integer> friendUserIDs) throws UserInfoException {
        this.email = email;
        if (!passwordSafetyCheck(password)) throw new UserInfoException(passwordNegativeFeedback());
        this.password = password;
        this.friendUserIDs = friendUserIDs;
    }

    protected UserInfo(UserInfo infoToCopy){
        this.email = infoToCopy.email;
        this.password = infoToCopy.password;
        this.friendUserIDs = new ArrayList<>(infoToCopy.friendUserIDs);
    }

    protected List<Integer> getFriendUserIDs() {
        return new ArrayList<>(this.friendUserIDs);
    }

    protected String getEmail() {
        return email;
    }

    protected boolean isPasswordCorrect(String password){
        return this.password.equals(password);
    }

    protected void setPassword(String oldPassword, String newPassword) throws UserInfoException {
        if(!isPasswordCorrect(oldPassword)){
            throw new UserInfoException("Current password is incorrect");
        }
        if(!passwordSafetyCheck(newPassword)){
            throw new UserInfoException(passwordNegativeFeedback());
        }
        this.password = newPassword;

    }

    protected void setEmail(String password, String email) throws UserInfoException {
        if(isPasswordCorrect(password)){
            this.email = email;
            return;
        }
        throw new UserInfoException("Password is incorrect");
    }

    protected void addFriend(int friendUserID) {
        friendUserIDs.add(friendUserID);
    }

    protected void removeFriend(int friendUserID) throws UserInfoException {
        // does below work? TODO test
        if(!friendUserIDs.contains(friendUserID)){
            throw new UserInfoException("friendUserID does not match any of player´s friend");
        }
        friendUserIDs.remove((Integer) friendUserID);
    }

    //4 forloops might be smarter
    protected static boolean passwordSafetyCheck(String password){
        if (password.length() < 8) return false;
        char[] chArray = password.toCharArray();
        for (char ch: chArray){
            if (Character.isWhitespace(ch)) return false;
        }
        int num = 0;
        int upC = 0;
        int lowC = 0;
        int spec = 0;
        for (char ch: password.toCharArray()){
            if (Character.isDigit(ch)) num = 1;
            else if (Character.isAlphabetic(ch) && Character.isUpperCase(ch)) upC = 1;
            else if (Character.isAlphabetic(ch) && Character.isLowerCase(ch)) lowC = 1;
            else if (!Character.isLetterOrDigit(ch)) spec = 1;
        }
        return num + upC + lowC + spec == 4;
    }

    protected static String passwordNegativeFeedback(){
        //must be updated with changed conditions
        return "Password must be at minimum 8 characters and contain at least an uppercase letter, a lowercase letter, a number, and a special character";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return email.equals(userInfo.email) &&
                password.equals(userInfo.password) &&
                friendUserIDs.equals(userInfo.friendUserIDs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, friendUserIDs);
    }

    public boolean isFriend(int userID) {
        return friendUserIDs.contains(userID);
    }

    public boolean sameEmail(String email) {
        return this.email.equals(email);
    }
}

