package com.example.asus.appmovie.Preference;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class AppPreference {
    private String KEY_DAILY = "daily";
    private String KEY_RELEASE = "release_movie";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public AppPreference(Context context){
        String PREF_NAME = "UserPref";
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void setDaily(boolean status){
        editor.putBoolean(KEY_DAILY, status);
        editor.apply();
    }

    public boolean isDaily(){
        return preferences.getBoolean(KEY_DAILY, false);
    }

    public void setRelease(boolean status){
        editor.putBoolean(KEY_RELEASE, status);
        editor.apply();
    }

    public boolean isRelease(){
        return preferences.getBoolean(KEY_RELEASE, false);
    }

}
