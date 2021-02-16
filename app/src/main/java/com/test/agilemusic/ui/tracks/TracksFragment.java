package com.test.agilemusic.ui.tracks;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.test.agilemusic.MainActivity;
import com.test.agilemusic.R;
import com.test.agilemusic.adapters.AlbumsAdapter;
import com.test.agilemusic.adapters.TracksAdapter;
import com.test.agilemusic.communication.CheckInternetConnection;
import com.test.agilemusic.ui.albums.AlbumsViewModel;

import java.util.Objects;

public class TracksFragment extends Fragment {

    private TracksViewModel tracksViewModel;
    private CheckInternetConnection checkInternetConnection = new CheckInternetConnection();
    //    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private TextView albumNameTextView;
    TracksAdapter tracksAdapter;
    View root;
    String albumId = "";
    String albumName = "";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        tracksViewModel = ViewModelProviders.of(this).get(TracksViewModel.class);

        root = inflater.inflate(R.layout.fragment_tracks, container, false);

        if (getArguments() != null) {
            albumId = getArguments().getString("albumId");
            albumName = getArguments().getString("albumName");
        }

        ((MainActivity) Objects.requireNonNull(getActivity())).setActionBarTitle(albumName);

        initViews();

        System.out.println("albumId: " + albumId);

        tracksViewModel.getTrackList(albumId).observe(getViewLifecycleOwner(), trackModel -> {

            tracksAdapter = new TracksAdapter(trackModel, getActivity());
            recyclerView.setAdapter(tracksAdapter);
            recyclerView.setHasFixedSize(true);

        });
        return root;
    }



    private void initViews() {

        recyclerView = root.findViewById(R.id.track_recyclerview);
//        albumNameTextView = root.findViewById(R.id.album_textview);
//        progressBar = root.findViewById(R.id.progress_bar);

//        progressBar.setVisibility(View.INVISIBLE);

//        StaggeredGridLayoutManager linearLayoutManager = new StaggeredGridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL, false);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            recyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), linearLayoutManager.getOrientation()));
//        }

        tracksAdapter = new TracksAdapter(null, getActivity());
        recyclerView.setAdapter(tracksAdapter);

    }

}