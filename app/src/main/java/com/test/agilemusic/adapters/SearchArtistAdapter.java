package com.test.agilemusic.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.test.agilemusic.R;
import com.test.agilemusic.models.SearchArtistModel;

import java.util.List;

public class SearchArtistAdapter extends RecyclerView.Adapter<SearchArtistAdapter.MyViewHolder> {

    private List<SearchArtistModel> list;
    private Context context;


    public SearchArtistAdapter(List<SearchArtistModel> list, Context context) {
        this.list = list;
        this.context = context;

    }

    public SearchArtistAdapter(List<SearchArtistModel> list) {
        this.list = list;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final View view;
        private final TextView artistIdTextView;
        private final TextView artistNameTextView;
        private final TextView genreTextView;

        MyViewHolder(View view) {
            super(view);
            this.view = view;
            artistIdTextView = view.findViewById(R.id.artist_id_textview);
            artistNameTextView = view.findViewById(R.id.artist_name_textview);
            genreTextView = view.findViewById(R.id.genre_textview);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            System.out.println("recyclerview clicked");

            String id = artistIdTextView.getText().toString();
            String name = artistNameTextView.getText().toString();

            Bundle bundle = new Bundle();
            bundle.putString("id", id);
            bundle.putString("name", name);
            Navigation.findNavController(view).navigate(R.id.action_navigation_search_to_navigation_albums, bundle);

        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_search_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        SearchArtistModel modelObject = list.get(position);

        holder.artistIdTextView.setText(modelObject.getArtistId());
        holder.artistNameTextView.setText(modelObject.getArtistName());
        holder.genreTextView.setText(modelObject.getGenre());
    }


    public void add(SearchArtistModel mc) {
        list.add(mc);

    }

    public void addToList(List<SearchArtistModel> newList){

        this.list.addAll(newList);


    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


}
