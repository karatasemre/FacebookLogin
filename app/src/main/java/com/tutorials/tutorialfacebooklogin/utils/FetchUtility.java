package com.tutorials.tutorialfacebooklogin.utils;

import com.squareup.picasso.Picasso;

/**
 * Created by Emre.Karatas on 5.07.2018.
 */

public class FetchUtility {

    public static void fetchImage(String photoUrl) {
        Picasso.get().load(photoUrl).fetch();
    }
}
