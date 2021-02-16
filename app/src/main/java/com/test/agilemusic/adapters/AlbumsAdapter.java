package com.test.agilemusic.adapters;

import android.content.Context;
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
import com.test.agilemusic.models.AlbumsModel;
import com.test.agilemusic.utilities.UtilityManager;

import java.util.List;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {

    private List<AlbumsModel> list;
    private Context context;
    private UtilityManager utilityManager = new UtilityManager();


    public AlbumsAdapter(List<AlbumsModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public AlbumsAdapter(List<AlbumsModel> list) {
        this.list = list;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final View view;
        private final TextView albumIdTextView;
        private final TextView albumNameTextView;
        private final TextView yearOfReleaseTextView;
        private final TextView priceTextView;
        private final TextView numberOfTracksTextView;
        private final ImageView albumArtImageView;

        MyViewHolder(View view) {
            super(view);
            this.view = view;
            albumIdTextView = view.findViewById(R.id.album_id_textview);
            albumNameTextView = view.findViewById(R.id.album_name_textview);
            yearOfReleaseTextView = view.findViewById(R.id.year_of_release_textview);
            priceTextView = view.findViewById(R.id.price_textview);
            numberOfTracksTextView = view.findViewById(R.id.number_of_tracks_textview);
            albumArtImageView = view.findViewById(R.id.album_imageview);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            System.out.println("recyclerview clicked");

            String albumId = albumIdTextView.getText().toString();
            String albumName = albumNameTextView.getText().toString();

            Bundle bundle = new Bundle();
            bundle.putString("albumId", albumId);
            bundle.putString("albumName", albumName);
            Navigation.findNavController(view).navigate(R.id.action_navigation_albums_to_navigation_tracks, bundle);



        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        AlbumsModel modelObject = list.get(position);

        System.out.println("modelObject.getAlbumId(): " + modelObject.getAlbumId());
        System.out.println("modelObject.getAlbumName(): " + modelObject.getAlbumName());
        System.out.println("modelObject.getYearOfRelease(): " + modelObject.getYearOfRelease());
        System.out.println("modelObject.getCurrency(): " + modelObject.getCurrency());

        holder.albumIdTextView.setText(modelObject.getAlbumId());
        holder.albumNameTextView.setText(modelObject.getAlbumName());
        holder.yearOfReleaseTextView.setText(utilityManager.getYearFromDate(modelObject.getYearOfRelease()));
        holder.numberOfTracksTextView.setText(modelObject.getNumberOfTracks().concat(" " + context.getString(R.string.tracks)));
        holder.priceTextView.setText(utilityManager.currencyIsoToSymbol(modelObject.getCurrency()).concat(" " + modelObject.getPrice()));

        Glide.with(AgileMusicApplication.getContext())
                .load(modelObject.getImageUrl())
                .placeholder(R.drawable.ic_album_placeholder)
                .into(holder.albumArtImageView);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


}
