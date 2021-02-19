package com.test.agilemusic.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.test.agilemusic.R;
import com.test.agilemusic.application.AgileMusicApplication;
import com.test.agilemusic.models.VideosModel;

import java.util.List;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.MyViewHolder> {

    private List<VideosModel> list;
    private Context context;


    public VideosAdapter(List<VideosModel> list, Context context) {
        this.list = list;
        this.context = context;

    }

    public VideosAdapter(List<VideosModel> list) {
        this.list = list;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final View view;
        private final TextView trackNameTextView;
        private final TextView previewUrlTextView;
        private final ImageView thumbnailImageView;

        MyViewHolder(View view) {
            super(view);
            this.view = view;
            trackNameTextView = view.findViewById(R.id.track_name_textview);
            previewUrlTextView = view.findViewById(R.id.preview_url_textview);
            thumbnailImageView = view.findViewById(R.id.thumbnail_imageview);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            String url = previewUrlTextView.getText().toString();

            Bundle bundle = new Bundle();
            bundle.putString("url", url);
            Navigation.findNavController(view).navigate(R.id.action_navigation_videos_to_navigation_video_player, bundle);

        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        VideosModel modelObject = list.get(position);

        holder.trackNameTextView.setTypeface(Typeface.createFromAsset(context.getAssets(), "Lato-Regular.ttf"));

        holder.trackNameTextView.setText(modelObject.getTrackName());
        holder.previewUrlTextView.setText(modelObject.getPreviewUrl());

        System.out.println("VideoURl: " + modelObject.getPreviewUrl());

        Glide.with(AgileMusicApplication.getContext())
                .load(modelObject.getThumbnalUrl())
                .placeholder(R.drawable.ic_album_placeholder)
                .into(holder.thumbnailImageView);
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


}
