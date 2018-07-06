package com.tutorials.tutorialfacebooklogin.model;

/**
 * Created by Emre.Karatas on 4.07.2018.
 */

public class AlbumModel {
    private String id;
    private String albumName;
    private String coverPhotoID;
    private String coverPhotoURL;


    public AlbumModel(){

    }

    public AlbumModel(String id, String albumName, String coverPhotoID, String coverPhotoURL){
        this.id = id;
        this.albumName = albumName;
        this.coverPhotoID = coverPhotoID;
        this.coverPhotoURL = coverPhotoURL;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getCoverPhotoURL() {
        return coverPhotoURL;
    }

    public void setCoverPhotoURL(String coverPhotoURL) {
        this.coverPhotoURL = coverPhotoURL;
    }

    public String getCoverPhotoID() {
        return coverPhotoID;
    }

    public void setCoverPhotoID(String coverPhotoID) {
        this.coverPhotoID = coverPhotoID;
    }
}
