package com.test.agilemusic.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.test.agilemusic.R;
import com.test.agilemusic.models.SearchArtistModel;

import java.util.List;

public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener{

    private SearchViewModel searchViewModel;
    private SearchView searchView;
    private RecyclerView recyclerView;
    TextView textView;
    View root;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);

        root = inflater.inflate(R.layout.fragment_search, container, false);

        initViews();


        return root;
    }


    private void initViews(){

        searchView = root.findViewById(R.id.searchview);
        recyclerView = root.findViewById(R.id.recyclerview);
        textView = root.findViewById(R.id.text_dashboard);

        searchView.setOnQueryTextListener(this);


    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        System.out.println("onQueryTextSubmit");
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        System.out.println("onQueryTextChange");

        if (newText.length() >= 2){
            //call api
            searchViewModel.getArtistList(newText).observe(getViewLifecycleOwner(), new Observer<List<SearchArtistModel>>() {
                @Override
                public void onChanged(List<SearchArtistModel> searchArtistModels) {

                }

            });
        }

        return false;
    }
}