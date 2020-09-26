package com.example.graymatter.Social;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//i wanted it to be protected, didn't work
public class UserInfo {

    //is this needed? TODO think
    private int userKey;

    private String email;
    private String password;
    private List<Integer> friendUserIDs;


    UserInfo(int userKey, String email, String password, List<Integer> friendUserIDs) throws MissingAccessException {
        this.userKey = userKey;
        this.email = email;
        this.password = password;
        this.friendUserIDs = friendUserIDs;
    }

    protected UserInfo(UserInfo infoToCopy){
        this.userKey = infoToCopy.userKey;
        this.email = infoToCopy.email;
        this.password = infoToCopy.password;
        Collections.copy(this.friendUserIDs, infoToCopy.friendUserIDs);
    }

    List<Integer> getFriendUserIDs(int userKey) throws MissingAccessException {
        safetyCheck(userKey);
        List<Integer> newList = new ArrayList<>();
        Collections.copy(newList, this.friendUserIDs);
        return newList;
    }

    protected String getEmail(int userKey) throws MissingAccessException {
        safetyCheck(userKey);
        return email;
    }

    //shouldn't have a safety check i think?
    protected boolean isPasswordCorrect(String password){
        return this.password.equals(password);
    }

    protected void setPassword(int userKey, String oldPassword, String newPassword) throws MissingAccessException {
        safetyCheck(userKey);
        if(this.password.equals(oldPassword) && passwordSafetyCheck(newPassword)){
            this.password = newPassword;
            return;
        }
        throw new MissingAccessException("Current password is incorrect");
    }

    protected void setEmail(int userKey, String password, String email) throws MissingAccessException {
        safetyCheck(userKey);
        if(this.password.equals(password)){
            this.email = email;
            return;
        }
        throw new MissingAccessException("Password is incorrect");
    }

    protected void addFriend(int userKey, int friendUserID) throws MissingAccessException {
        safetyCheck(userKey);
        friendUserIDs.add(friendUserID);
    }

    protected void removeFriend(int userKey, int friendUserID) throws Exception {
        safetyCheck(userKey);
        // does below work? TODO test
        if(!friendUserIDs.contains(friendUserID)){
            throw new Exception("friendUserID does not match any of player´s friend");
        }
        friendUserIDs.remove(friendUserID);
    }

    //can a method JUST check for exceptions?
    private void safetyCheck(int userKey) throws MissingAccessException {
        if (this.userKey != userKey){
            throw new MissingAccessException("Unmatching userkey");
        }
    }

    //4 forloops might be smarter
    public static boolean passwordSafetyCheck(String password){
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

    public static String passwordNegativeFeedback(){
        //must be updated with changed conditions
        return "Password must be at minimum 8 characters and contain at least an uppercase letter, a lowercase letter, a number, and a special character";
    }


}