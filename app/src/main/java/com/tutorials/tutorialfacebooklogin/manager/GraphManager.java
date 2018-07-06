package com.tutorials.tutorialfacebooklogin.manager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.tutorials.tutorialfacebooklogin.listener.OnAlbumListener;
import com.tutorials.tutorialfacebooklogin.listener.OnGraphRequestStatus;
import com.tutorials.tutorialfacebooklogin.listener.OnPhotoListener;
import com.tutorials.tutorialfacebooklogin.model.AlbumModel;
import com.tutorials.tutorialfacebooklogin.model.PhotoModel;
import com.tutorials.tutorialfacebooklogin.utils.FetchUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emre.Karatas on 2.07.2018.
 */

public class GraphManager {
    public static final GraphManager instance = new GraphManager();

    public GraphManager() {

    }

    public void getUserInfo(final Context context, final AccessToken accessToken, final GraphRequest.Callback callback) {

        GraphRequestBatch batch = new GraphRequestBatch(
                GraphRequest.newMeRequest(
                        accessToken,
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject jsonObject,
                                    GraphResponse response) {
                                try {
                                    PreferencesUtility.setFbUserId(context, jsonObject.get("id").toString());
                                    callback.onCompleted(response);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }),
                GraphRequest.newMyFriendsRequest(
                        accessToken,
                        new GraphRequest.GraphJSONArrayCallback() {
                            @Override
                            public void onCompleted(
                                    JSONArray jsonArray,
                                    GraphResponse response) {
                                // Application code for users friends
                            }
                        })
        );
        batch.addCallback(new GraphRequestBatch.Callback() {
            @Override
            public void onBatchCompleted(GraphRequestBatch graphRequests) {
                // Application code for when the batch finishes

            }
        });
        batch.executeAsync();
    }

    public void getAlbums(String userID, final OnAlbumListener callback) {
        final List<AlbumModel> albumModel = new ArrayList<>();
        GraphRequest request = new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + userID + "/albums",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        try {
                            if (response.getError() == null && response.getJSONObject().length() > 0) {
                                JSONArray albumDatas = response.getJSONObject().getJSONArray("data");

                                for (int i = 0; i < albumDatas.length(); i++) {

                                    final String id = ((JSONObject) albumDatas.get(i)).get("id").toString();
                                    final String albumName = ((JSONObject) albumDatas.get(i)).get("name").toString();
                                    final String coverPhoto = ((JSONObject) albumDatas.get(i)).getJSONObject("cover_photo").get("id").toString();

                                    albumModel.add(new AlbumModel(id, albumName, coverPhoto, null));

                                }
                                callback.getAlbumList(albumModel);
                            } else {
                                Log.e("Error: ", response.getError().getErrorMessage().toString());
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, name, cover_photo");

        request.setParameters(parameters);
        request.executeAsync();
    }

    public void getPhotos(String albumID, final OnPhotoListener callback) {

        final List<PhotoModel> photoModels = new ArrayList<>();
        GraphRequest request = new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + albumID + "/photos",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        try {
                            if (response.getJSONObject().length() > 0) {
                                JSONArray photoIDs = response.getJSONObject().getJSONArray("data");

                                for (int i = 0; i < photoIDs.length(); i++) {
                                    photoModels.add(new PhotoModel(photoIDs.getJSONObject(i).get("id").toString()));
                                }

                                callback.getPhotoList(photoModels);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id");

        request.setParameters(parameters);
        request.executeAsync();

    }

    public void getAlbumCoverUrl(String photoID, final OnGraphRequestStatus status) {
        final String[] coverPhotoUrl = {null};
        GraphRequest request = new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + photoID,
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {

                        try {
                            if (response.getJSONObject() != null && response.getJSONObject().length() > 0) {
                                coverPhotoUrl[0] = response.getJSONObject().getJSONArray("images").getJSONObject(3).get("source").toString();

                                FetchUtility.fetchImage(coverPhotoUrl[0]);

                                status.onSuccess(coverPhotoUrl[0]);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();

                            status.onError(e.getMessage().toString());
                        }
                    }
                }
        );

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, images");

        request.setParameters(parameters);
        request.executeAsync();

    }
}
