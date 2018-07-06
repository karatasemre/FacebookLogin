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

import com.tutorials.tutorialfacebooklogin.R;
import com.tutorials.tutorialfacebooklogin.adapter.PhotoViewAdapter;
import com.tutorials.tutorialfacebooklogin.model.PhotoModel;

import java.io.Serializable;
import java.util.List;

import static com.tutorials.tutorialfacebooklogin.utils.ConstStringKey.PHOTO_MODELS;

/**
 * Created by Emre.Karatas on 4.07.2018.
 */

public class PhotosFragment extends Fragment implements PhotoViewAdapter.ItemClickListener {

    PhotoViewAdapter photoAdapter;
    RecyclerView photoList;
    List<PhotoModel> photoModelList;


    public static PhotosFragment newInstance(List<PhotoModel> photoModels) {
        PhotosFragment photosFragment = new PhotosFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(PHOTO_MODELS, (Serializable) photoModels);
        photosFragment.setArguments(bundle);

        return photosFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photos, container, false);

        photoList = v.findViewById(R.id.photo_list);

        photoModelList =  (List<PhotoModel>) getArguments().getSerializable(PHOTO_MODELS);

        int numberOfColumns = 2;
        photoList.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
        photoAdapter = new PhotoViewAdapter(getContext(), photoModelList);
        photoAdapter.setClickListener(this);
        photoList.setAdapter(photoAdapter);

        return v;
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.i("TAG", photoAdapter.getAlbumId(position));
    }
}
