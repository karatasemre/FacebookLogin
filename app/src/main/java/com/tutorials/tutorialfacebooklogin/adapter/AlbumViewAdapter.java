package com.tutorials.tutorialfacebooklogin.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tutorials.tutorialfacebooklogin.R;
import com.tutorials.tutorialfacebooklogin.listener.OnGraphRequestStatus;
import com.tutorials.tutorialfacebooklogin.manager.GraphManager;
import com.tutorials.tutorialfacebooklogin.model.AlbumModel;
import com.tutorials.tutorialfacebooklogin.utils.ColorUtility;

import java.util.List;
import java.util.Random;

/**
 * Created by Emre.Karatas on 4.07.2018.
 */

public class AlbumViewAdapter extends RecyclerView.Adapter<AlbumViewAdapter.ViewHolder> {

    public List<AlbumModel> albumModels;
    public LayoutInflater mInflater;
    public ItemClickListener mClickListener;

    public AlbumViewAdapter(Context context, List<AlbumModel> albumModels) {
        this.mInflater = LayoutInflater.from(context);
        this.albumModels = albumModels;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_album, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.albumName.setText(albumModels.get(position).getAlbumName());

        final GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setColor(Color.parseColor("#" + ColorUtility.getColor[new Random().nextInt(254)]));

        GraphManager.instance.getAlbumCoverUrl(albumModels.get(position).getCoverPhotoID(), new OnGraphRequestStatus() {
            @Override
            public void onSuccess(String coverPhotoUrl) {
                Picasso.get().load(coverPhotoUrl)
                        .error(shape)
                        .into(holder.albumCoverPhoto);
            }

            @Override
            public void onError(String message) {
                Log.e("Error Cover Image", message.toString());

            }
        });


        holder.frameItemLayout.setBackground(shape);
    }

    @Override
    public int getItemCount() {
        return albumModels.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView albumName;
        FrameLayout frameItemLayout;
        ImageView albumCoverPhoto;

        ViewHolder(View itemView) {
            super(itemView);
            albumName = itemView.findViewById(R.id.album_name);
            frameItemLayout = itemView.findViewById(R.id.frame_item_layout);
            albumCoverPhoto = itemView.findViewById(R.id.album_cover_photo);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null)
                mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public String getAlbumId(int id) {
        return albumModels.get(id).getId();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
