package com.tutorials.tutorialfacebooklogin.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tutorials.tutorialfacebooklogin.R;
import com.tutorials.tutorialfacebooklogin.listener.OnGraphRequestStatus;
import com.tutorials.tutorialfacebooklogin.manager.GraphManager;
import com.tutorials.tutorialfacebooklogin.model.PhotoModel;
import com.tutorials.tutorialfacebooklogin.utils.ColorUtility;
import com.tutorials.tutorialfacebooklogin.utils.SquareRelativeLayout;

import java.util.List;
import java.util.Random;

/**
 * Created by Emre.Karatas on 4.07.2018.
 */

public class PhotoViewAdapter extends RecyclerView.Adapter<PhotoViewAdapter.ViewHolder> {

    public List<PhotoModel> photoList;
    public LayoutInflater mInflater;
    public ItemClickListener mClickListener;

    public PhotoViewAdapter(Context context, List<PhotoModel> photoList) {
        this.mInflater = LayoutInflater.from(context);
        this.photoList = photoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_photo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setColor(Color.parseColor("#" + ColorUtility.getColor[new Random().nextInt(254)]));

        holder.itemLayout.setBackground(shape);

        GraphManager.instance.getAlbumCoverUrl(photoList.get(position).getId(), new OnGraphRequestStatus() {
            @Override
            public void onSuccess(String coverPhotoUrl) {
                Picasso.get().load(coverPhotoUrl)
                        .error(R.drawable.loading)
                        .into(holder.fbPhoto);
            }

            @Override
            public void onError(String message) {
                Log.e("Error Cover Image", message.toString());

            }
        });


    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView fbPhoto;
        SquareRelativeLayout itemLayout;

        ViewHolder(View itemView) {
            super(itemView);
            fbPhoto = itemView.findViewById(R.id.fb_photo);
            itemLayout = itemView.findViewById(R.id.item_photo_layout);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null)
                mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public String getAlbumId(int id) {
        return photoList.get(id).getId();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
