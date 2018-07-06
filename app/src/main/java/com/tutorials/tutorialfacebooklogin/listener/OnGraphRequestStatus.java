package com.tutorials.tutorialfacebooklogin.listener;

import com.tutorials.tutorialfacebooklogin.model.AlbumModel;

/**
 * Created by Emre.Karatas on 5.07.2018.
 */

public interface OnGraphRequestStatus {

    void onSuccess(String coverPhotoUrl);
    void onError(String message);
}
