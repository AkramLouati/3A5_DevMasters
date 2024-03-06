package edu.esprit.utils;

import edu.esprit.entities.EndUser;

public class Session {
    private static EndUser currentUser;
    public static void setCurrentUser(EndUser user){
        currentUser = user;
    }
    public static EndUser getCurrentUser(){
        return currentUser;
    }
}
