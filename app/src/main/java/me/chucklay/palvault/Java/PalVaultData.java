package me.chucklay.palvault.Java;

import android.app.Application;

/**
 * Created by Charlie on 12/22/2015.
 *
 * Stores data about the application's state.
 */
public class PalVaultData extends Application {

    private static boolean authenticatedUser = false;
    private static String username = null;

    public static String getUsername(){
        return username;
    }
    public static boolean isAuthenticatedUser(){
        return isAuthenticatedUser();
    }

    public static void setUsername(String newUsername){
        username = newUsername;
    }
    public static void setAuthenticatedUser(boolean authenticated){
        authenticatedUser = authenticated;
    }
}
