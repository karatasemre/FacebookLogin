package com.tutorials.tutorialfacebooklogin.model;

/**
 * Created by Emre.Karatas on 4.07.2018.
 */

public class PhotoModel {
    private String id;
    private String url;

    public PhotoModel(){

    }

    public PhotoModel(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
