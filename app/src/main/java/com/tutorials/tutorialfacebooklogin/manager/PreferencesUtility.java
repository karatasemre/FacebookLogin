package com.tutorials.tutorialfacebooklogin.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tutorials.tutorialfacebooklogin.model.AlbumModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emre.Karatas on 4.07.2018.
 */

public class PreferencesUtility {

    private static final String PREF_NAME = "facebook";
    private static final String FB_USER_ID = "FB_USER_ID";
    private static final String ALBUM_LIST = "ALBUM_LIST";

    private Context context;
    private SharedPreferences sharedPreferences;

    public PreferencesUtility(Context context) {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setFbUserId(Context context, String userId) {
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(FB_USER_ID, userId);
        edit.apply();
    }

    public static String getFbUserId(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sp.getString(FB_USER_ID, null);
    }

    public static void setAlbumList(Context context, List<AlbumModel> albumModels) {
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();

        Gson gson = new Gson();
        String json = gson.toJson(albumModels);

        edit.putString(ALBUM_LIST, json);
        edit.commit();
    }

    public static List<AlbumModel> getAlbumList(Context context) {
        List<AlbumModel> albumModel = new ArrayList<>();
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString(ALBUM_LIST, "");

        Type type = new TypeToken<List<AlbumModel>>() {
        }.getType();
        albumModel = gson.fromJson(json, type);

        return albumModel;
    }
}
