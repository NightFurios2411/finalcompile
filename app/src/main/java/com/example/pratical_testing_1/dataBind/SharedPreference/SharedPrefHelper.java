package com.example.pratical_testing_1.dataBind.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefHelper {
    private static final String PREF_NAME = "UserProfile";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_ID = "id";
    private static final String KEY_PROFILE_PIC = "profile_pic";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPrefHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Save user data
    public void saveUserProfile(String username, String email, String id, String profile_pic) {
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_ID, id);
        editor.putString(KEY_PROFILE_PIC, profile_pic);
        editor.apply(); // Save changes asynchronously
    }

    public void setUserName(String username) {
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }
    public String getUserName() {
        return sharedPreferences.getString(KEY_USERNAME, "");
    }


    public void setEmail(String email) {
        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }
    public String getEmail() {
        return sharedPreferences.getString(KEY_EMAIL, "");
    }


    public void setKeyId(String id) {
        editor.putString(KEY_ID, id);
        editor.apply();
    }
    public String getKeyId() {
        return sharedPreferences.getString(KEY_ID, "");
    }


    public void setProfilePic(String profile_pic) {
        editor.putString(KEY_PROFILE_PIC, profile_pic);
        editor.apply();
    }
    public String getProfilePic() {
        return sharedPreferences.getString(KEY_PROFILE_PIC, "");
    }


    // Clear user data (e.g., on logout)
    public void clearProfileData() {
        editor.clear();
        editor.apply();
    }
}

