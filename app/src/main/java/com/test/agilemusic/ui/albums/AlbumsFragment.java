package com.test.agilemusic.ui.albums;

import android.content.Context;
import android.os.Build;
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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.agilemusic.MainActivity;
import com.test.agilemusic.R;
import com.test.agilemusic.adapters.AlbumsAdapter;
import com.test.agilemusic.communication.CheckInternetConnection;
import com.test.agilemusic.utilities.ErrorDialogInterface;

import java.util.Objects;

public class AlbumsFragment extends Fragment implements ErrorDialogInterface {

    private AlbumsViewModel albumsViewModel;
    private CheckInternetConnection checkInternetConnection = new CheckInternetConnection();
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private TextView noResultsTextView;
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

        if (!checkInternetConnection.isNetworkAvailable(getActivity())) {
            showAlertDialog(getString(R.string.oops), getString(R.string.no_internet_connection), getString(R.string.cancel), getActivity());
        } else {

            progressBar.setVisibility(View.VISIBLE);

            albumsViewModel.getAlbumList(artistId).observe(getViewLifecycleOwner(), albumsModels -> {

                if (albumsModels == null) { // no results

                    System.out.println("albumsModels == null");

                    progressBar.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);
                    noResultsTextView.setVisibility(View.VISIBLE);

                    showAlertDialog(getString(R.string.oops), getString(R.string.something_went_wrong), getString(R.string.cancel), getActivity());

                } else if (albumsModels.isEmpty()) { // no results

                    System.out.println("albumsModels.isEmpty()");

                    progressBar.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);
                    noResultsTextView.setVisibility(View.VISIBLE);

                } else {

                    albumsAdapter = new AlbumsAdapter(albumsModels, getActivity());
                    recyclerView.setAdapter(albumsAdapter);
                    recyclerView.setHasFixedSize(true);

                    progressBar.setVisibility(View.INVISIBLE);
                }

            });

        }

        return root;
    }


    private void initViews() {

        initProgressBar();

        recyclerView = root.findViewById(R.id.album_recyclerview);
        noResultsTextView = root.findViewById(R.id.no_results_textview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            recyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), linearLayoutManager.getOrientation()));
        }

        albumsAdapter = new AlbumsAdapter(null, getActivity());
        recyclerView.setAdapter(albumsAdapter);

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