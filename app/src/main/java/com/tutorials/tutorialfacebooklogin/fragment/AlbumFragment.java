package com.tutorials.tutorialfacebooklogin.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tutorials.tutorialfacebooklogin.MainActivity;
import com.tutorials.tutorialfacebooklogin.listener.OnPhotoListener;
import com.tutorials.tutorialfacebooklogin.R;
import com.tutorials.tutorialfacebooklogin.adapter.AlbumViewAdapter;
import com.tutorials.tutorialfacebooklogin.manager.GraphManager;
import com.tutorials.tutorialfacebooklogin.manager.PreferencesUtility;
import com.tutorials.tutorialfacebooklogin.model.AlbumModel;
import com.tutorials.tutorialfacebooklogin.model.PhotoModel;

import java.util.List;

/**
 * Created by Emre.Karatas on 4.07.2018.
 */

public class AlbumFragment extends Fragment implements AlbumViewAdapter.ItemClickListener {

    AlbumViewAdapter albumAdapter;
    RecyclerView albumList;
    List<AlbumModel> albumModels;


    public static AlbumFragment newInstance() {
        AlbumFragment albumFragment = new AlbumFragment();

        return albumFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_album, container, false);

        albumModels = PreferencesUtility.getAlbumList(getContext());

        albumList = v.findViewById(R.id.album_list);
        int numberOfColumns = 2;
        albumList.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
        albumAdapter = new AlbumViewAdapter(getContext(), albumModels);
        albumAdapter.setClickListener(this);
        albumList.setAdapter(albumAdapter);

        return v;
    }

    @Override
    public void onItemClick(View view, int position) {
        String albumID = albumAdapter.getAlbumId(position);

        GraphManager.instance.getPhotos(albumID, new OnPhotoListener() {
            @Override
            public void getPhotoList(List<PhotoModel> photoModels) {

                MainActivity.instance.openFragment(PhotosFragment.newInstance(photoModels), true);

            }
        });
    }
}
