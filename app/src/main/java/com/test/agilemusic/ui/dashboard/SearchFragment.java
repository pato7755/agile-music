package com.test.agilemusic.ui.dashboard;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.agilemusic.R;
import com.test.agilemusic.adapters.SearchArtistAdapter;
import com.test.agilemusic.models.SearchArtistModel;

import java.util.List;
import java.util.Objects;

public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener {

    private SearchViewModel searchViewModel;
    private SearchView searchView;
    private RecyclerView recyclerView;
    SearchArtistAdapter searchArtistAdapter;
    TextView textView;
    View root;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_search, container, false);


        initViews();

        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);

//        searchArtistAdapter = new SearchArtistAdapter();

        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


    private void initViews() {

        searchView = root.findViewById(R.id.searchview);
        recyclerView = root.findViewById(R.id.recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            recyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), linearLayoutManager.getOrientation()));
        }
        searchArtistAdapter = new SearchArtistAdapter(null, getActivity());
        recyclerView.setAdapter(searchArtistAdapter);

        searchView.setOnQueryTextListener(this);


    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        System.out.println("onQueryTextSubmit");
        searchViewModel.getArtistList(query).observe(getViewLifecycleOwner(), new Observer<List<SearchArtistModel>>() {
            @Override
            public void onChanged(List<SearchArtistModel> searchArtistModels) {

                searchArtistAdapter = new SearchArtistAdapter(searchArtistModels, getActivity());

                recyclerView.setAdapter(searchArtistAdapter);
                recyclerView.setHasFixedSize(true);
//                    searchArtistAdapter.notifyDataSetChanged();

            }

        });
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        System.out.println("onQueryTextChange");

//        if (newText.length() >= 2) {
//            //call api
//            searchViewModel.getArtistList(newText).observe(getViewLifecycleOwner(), new Observer<List<SearchArtistModel>>() {
//                @Override
//                public void onChanged(List<SearchArtistModel> searchArtistModels) {
//
//                    searchArtistAdapter = new SearchArtistAdapter(searchArtistModels, getActivity());
//                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
//                    recyclerView.setLayoutManager(linearLayoutManager);
//                    recyclerView.setAdapter(searchArtistAdapter);
////                    searchArtistAdapter.notifyDataSetChanged();
//
//                }
//
//            });
//        }

        return false;
    }
}