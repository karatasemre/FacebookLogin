package com.tutorials.tutorialfacebooklogin.listener;

import com.tutorials.tutorialfacebooklogin.model.AlbumModel;
import com.tutorials.tutorialfacebooklogin.model.PhotoModel;

import java.util.List;

/**
 * Created by Emre.Karatas on 4.07.2018.
 */

public interface OnPhotoListener {
    void getPhotoList(List<PhotoModel> photoModels);
}
