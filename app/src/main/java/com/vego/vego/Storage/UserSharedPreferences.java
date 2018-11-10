package com.vego.vego.Storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.vego.vego.model.UserInfo;

import static android.content.Context.MODE_PRIVATE;
import static android.os.Build.USER;
/**
 * Created by DevFatani on 9/8/2016 AD.
 *
 *
 * Simple class to store user information
 */
public class UserSharedPreferences {
    private static final String TAG = "UserSharedPreferences";


    /**
     *  Store user information
     * */
    public static void store(Context context, UserInfo user) {
        SharedPreferences.Editor editor = context.getSharedPreferences("user", MODE_PRIVATE).edit();
        editor.putString("userUID", user.getUid());
        editor.putString("userName", user.getName());
        editor.putString("userEmail", user.getEmail());
        editor.putString("isAdmin", user.getAdmin());
        editor.apply();
        Log.v(TAG, "successful store user!");
    }

    /**
     * Get user information
     * */
    public static UserInfo fetch(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", MODE_PRIVATE);
        String name = sharedPreferences.getString("userName", "");
        String uid = sharedPreferences.getString("userUID", "");
        String email = sharedPreferences.getString("userEmail", "");
        String isAdmin = sharedPreferences.getString("isAdmin", "");
        Log.v(TAG, "successful fetch user!");
        UserInfo  user = new UserInfo();
        user.setIsAdmin(isAdmin);
        user.setUid(uid);
        user.setName(name);
        user.setEmail(email);
        return user;
    }
    public static void storeChosenUser(Context context, String user) {
        SharedPreferences.Editor editor = context.getSharedPreferences("chosen", MODE_PRIVATE).edit();
        editor.putString("chosenUser", user);
        editor.apply();
        Log.v(TAG, "successful store user!");
    }
    public static String fetchChosenUser(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("chosen", MODE_PRIVATE);
        String chosenUser = sharedPreferences.getString("chosenUser", "");
        Log.v(TAG, "successful fetch user!");
        return chosenUser;
    }


//    public static void updatePassword(Context context, String password) {
//        SharedPreferences.Editor editor = context.getSharedPreferences(USER, MODE_PRIVATE).edit();
//        editor.putString(PASSWORD, password);
//        editor.apply();
//        Log.v(TAG, "successful update password");
//    }

//    public static void updateUserInfo(Context context, String username, String email) {
//        SharedPreferences.Editor editor = context.getSharedPreferences(USER, MODE_PRIVATE).edit();
//        editor.putString(NAME, username);
//        editor.putString(EMAIL, email);
//        editor.apply();
//        Log.v(TAG, "successful update username");
//    }

}
