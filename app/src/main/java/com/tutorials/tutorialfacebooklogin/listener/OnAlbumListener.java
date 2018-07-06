package com.tutorials.tutorialfacebooklogin.listener;

import com.tutorials.tutorialfacebooklogin.model.AlbumModel;

import java.util.List;

/**
 * Created by Emre.Karatas on 4.07.2018.
 */

public interface OnAlbumListener {
    void getAlbumList(List<AlbumModel> albumModels);
}
