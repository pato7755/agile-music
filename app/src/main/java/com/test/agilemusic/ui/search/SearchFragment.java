package com.test.agilemusic.ui.search;

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
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.agilemusic.R;
import com.test.agilemusic.adapters.SearchArtistAdapter;
import com.test.agilemusic.communication.CheckInternetConnection;
import com.test.agilemusic.utilities.ErrorDialogInterface;

import java.util.Objects;

public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener, ErrorDialogInterface {

    private CheckInternetConnection checkInternetConnection = new CheckInternetConnection();
    private SearchViewModel searchViewModel;
    private TextView noResultsTextView;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    SearchArtistAdapter searchArtistAdapter;
    View root;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_search, container, false);

        initViews();

        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);

        return root;
    }


    private void initViews() {

        initProgressBar();

        SearchView searchView = root.findViewById(R.id.searchview);
        recyclerView = root.findViewById(R.id.recyclerview);
        noResultsTextView = root.findViewById(R.id.no_results_textview);

//        progressBar = root.findViewById(R.id.progress_bar);

//        progressBar.setVisibility(View.VISIBLE);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            recyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), linearLayoutManager.getOrientation()));
        }
        searchArtistAdapter = new SearchArtistAdapter(null, getActivity());
        recyclerView.setAdapter(searchArtistAdapter);

        searchView.setOnQueryTextListener(this);

        System.out.println("progressbar: " + progressBar.getVisibility());


    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        System.out.println("onQueryTextSubmit");

        recyclerView.setVisibility(View.VISIBLE);
        noResultsTextView.setVisibility(View.GONE);

        if (!checkInternetConnection.isNetworkAvailable(getActivity())) {
            showAlertDialog(getString(R.string.oops), getString(R.string.no_internet_connection), getString(R.string.cancel), getActivity());
        } else {

            showOrHideProgressBar(1);
            System.out.println("progressbar: " + progressBar.getVisibility());

            searchViewModel.getArtistList(query).observe(getViewLifecycleOwner(), searchArtistModels -> {

                if (searchArtistModels == null) { // no results
                    System.out.println("searchArtistModels is null");
                    recyclerView.setVisibility(View.INVISIBLE);
                    noResultsTextView.setVisibility(View.VISIBLE);

                } else if (searchArtistModels.isEmpty()) { // no results
                    System.out.println("searchArtistModels.isEmpty()");
                    recyclerView.setVisibility(View.INVISIBLE);
                    noResultsTextView.setVisibility(View.VISIBLE);

                } else { // has results
                    searchArtistAdapter = new SearchArtistAdapter(searchArtistModels, getActivity());
                    recyclerView.setAdapter(searchArtistAdapter);
                    recyclerView.setHasFixedSize(true);

                }

            });
            showOrHideProgressBar(0);

        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        System.out.println("onQueryTextChange");
        recyclerView.setVisibility(View.VISIBLE);
        noResultsTextView.setVisibility(View.GONE);

        return false;
    }

    public void showOrHideProgressBar(int flag) {

        if (flag == 0) {
            progressBar.setVisibility(View.INVISIBLE);
        } else if (flag == 1) {
            progressBar.setVisibility(View.VISIBLE);
        }

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
}