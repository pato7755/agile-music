package com.test.agilemusic.ui.albums;

import android.os.Build;
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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.agilemusic.MainActivity;
import com.test.agilemusic.R;
import com.test.agilemusic.adapters.AlbumsAdapter;
import com.test.agilemusic.adapters.SearchArtistAdapter;
import com.test.agilemusic.communication.CheckInternetConnection;
import com.test.agilemusic.ui.search.SearchViewModel;

import java.util.Objects;

public class AlbumsFragment extends Fragment {

    private AlbumsViewModel albumsViewModel;
    private CheckInternetConnection checkInternetConnection = new CheckInternetConnection();
    //    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private TextView albumNameTextView;
    AlbumsAdapter albumsAdapter;
    View root;
    String artistName = "";
    String artistId = "";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        albumsViewModel = ViewModelProviders.of(this).get(AlbumsViewModel.class);

        root = inflater.inflate(R.layout.fragment_albums, container, false);

        if (getArguments() != null) {
            artistId = getArguments().getString("id");
            artistName = getArguments().getString("name");
        }

        Objects.requireNonNull(getActivity()).setTitle(artistName);
        ((MainActivity) Objects.requireNonNull(getActivity())).setActionBarTitle(artistName);

        initViews();

        albumsViewModel.getAlbumList(artistId).observe(getViewLifecycleOwner(), albumsModels -> {

            albumsAdapter = new AlbumsAdapter(albumsModels, getActivity());
            recyclerView.setAdapter(albumsAdapter);
            recyclerView.setHasFixedSize(true);

        });
        return root;
    }



    private void initViews() {

        recyclerView = root.findViewById(R.id.album_recyclerview);
        albumNameTextView = root.findViewById(R.id.album_textview);
//        progressBar = root.findViewById(R.id.progress_bar);

//        progressBar.setVisibility(View.INVISIBLE);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            recyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), linearLayoutManager.getOrientation()));
        }

        albumsAdapter = new AlbumsAdapter(null, getActivity());
        recyclerView.setAdapter(albumsAdapter);



    }

}