package com.test.agilemusic.ui.tracks;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.agilemusic.MainActivity;
import com.test.agilemusic.R;
import com.test.agilemusic.adapters.TracksAdapter;
import com.test.agilemusic.communication.CheckInternetConnection;
import com.test.agilemusic.utilities.ErrorDialogInterface;

import java.util.Objects;

public class TracksFragment extends Fragment implements ErrorDialogInterface {

    private TracksViewModel tracksViewModel;
    private CheckInternetConnection checkInternetConnection = new CheckInternetConnection();
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private TextView noResultsTextView;
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

        if (!checkInternetConnection.isNetworkAvailable(getActivity())) {
            showAlertDialog(getString(R.string.oops), getString(R.string.no_internet_connection), getString(R.string.cancel), getActivity());
        } else {

            progressBar.setVisibility(View.VISIBLE);

            tracksViewModel.getTrackList(albumId).observe(getViewLifecycleOwner(), trackModel -> {

                if (trackModel == null) { // no results

                    System.out.println("trackModel == null");

                    progressBar.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);
                    noResultsTextView.setVisibility(View.VISIBLE);

                    showAlertDialog(getString(R.string.oops), getString(R.string.something_went_wrong), getString(R.string.cancel), getActivity());

                } else if (trackModel.isEmpty()) { // no results

                    System.out.println("trackModel.isEmpty()");

                    progressBar.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);
                    noResultsTextView.setVisibility(View.VISIBLE);

                } else {

                    tracksAdapter = new TracksAdapter(trackModel, getActivity());
                    recyclerView.setAdapter(tracksAdapter);
                    recyclerView.setHasFixedSize(true);

                    progressBar.setVisibility(View.INVISIBLE);

                }

            });

        }

        return root;
    }


    private void initViews() {

        initProgressBar();

        recyclerView = root.findViewById(R.id.track_recyclerview);
        noResultsTextView = root.findViewById(R.id.no_results_textview);

        GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        tracksAdapter = new TracksAdapter(null, getActivity());
        recyclerView.setAdapter(tracksAdapter);

    }

    public void initProgressBar() {

        ConstraintLayout layout = root.findViewById(R.id.container);
        progressBar = new ProgressBar(getActivity(), null, android.R.attr.progressBarStyleLarge);
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(100, 100);
        params.bottomToBottom = ConstraintSet.PARENT_ID;
        params.endToEnd = ConstraintSet.PARENT_ID;
        params.startToStart = ConstraintSet.PARENT_ID;
        params.topToTop = ConstraintSet.PARENT_ID;
        layout.addView(progressBar, params);
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void showAlertDialog(String title, String message, String positiveButtonText, Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButtonText, (dialogInterface, id) -> {
                    dialogInterface.dismiss();
                })
                .create()
                .show();

    }

}