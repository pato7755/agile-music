package com.test.agilemusic.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.test.agilemusic.R;
import com.test.agilemusic.models.TrackModel;
import com.test.agilemusic.utilities.UtilityManager;

import java.util.List;

public class TracksAdapter extends RecyclerView.Adapter<TracksAdapter.MyViewHolder> {

    private List<TrackModel> list;
    private Context context;
    private UtilityManager utilityManager = new UtilityManager();


    public TracksAdapter(List<TrackModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public TracksAdapter(List<TrackModel> list) {
        this.list = list;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final View view;
        private final TextView trackNumberTextView;
        private final TextView trackNameTextView;
        private final TextView trackIdTextView;
        private final TextView previewUrlTextView;
        private final TextView isStreamableTextView;

        MyViewHolder(View view) {
            super(view);
            this.view = view;
            trackNumberTextView = view.findViewById(R.id.track_number_textview);
            trackNameTextView = view.findViewById(R.id.track_name_textview);
            trackIdTextView = view.findViewById(R.id.track_id_textview);
            previewUrlTextView = view.findViewById(R.id.preview_url_textview);
            isStreamableTextView = view.findViewById(R.id.is_streamable_textview);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            System.out.println("recyclerview clicked");


        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        TrackModel modelObject = list.get(position);

        holder.trackNameTextView.setTypeface(Typeface.createFromAsset(context.getAssets(), "Lato-Regular.ttf"));
        holder.trackNumberTextView.setTypeface(Typeface.createFromAsset(context.getAssets(), "Lato-Regular.ttf"));

        holder.trackNumberTextView.setText(modelObject.getTrackNumber());
        holder.trackNameTextView.setText(modelObject.getTrackName());
        holder.trackIdTextView.setText(modelObject.getTrackId());
        holder.previewUrlTextView.setText(modelObject.getPreviewUrl());
        holder.isStreamableTextView.setText(String.valueOf(modelObject.isStreamable()));

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


}
