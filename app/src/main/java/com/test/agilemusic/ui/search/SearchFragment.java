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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.agilemusic.R;
import com.test.agilemusic.adapters.SearchArtistAdapter;
import com.test.agilemusic.communication.CheckInternetConnection;
import com.test.agilemusic.models.SearchArtistModel;
import com.test.agilemusic.utilities.EndlessScrollListener;
import com.test.agilemusic.utilities.ErrorDialogInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener, ErrorDialogInterface {

    private CheckInternetConnection checkInternetConnection = new CheckInternetConnection();
    private SearchViewModel searchViewModel;
    LinearLayoutManager linearLayoutManager;
    private TextView noResultsTextView;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    SearchArtistAdapter searchArtistAdapter;
    View root;
    private String searchTerm;
    int offset = 0;
    int limit = 20;
    List<SearchArtistModel> searchArtistModels = new ArrayList<>();


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

        linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        // set dividers in recyclerview
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            recyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), linearLayoutManager.getOrientation()));
        }

        searchArtistAdapter = new SearchArtistAdapter(null, getActivity());
        recyclerView.setAdapter(searchArtistAdapter);

        searchView.setOnQueryTextListener(this);


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
    public boolean onQueryTextSubmit(String query) {

        searchTerm = query;

        noResultsTextView.setVisibility(View.GONE);

        if (!checkInternetConnection.isNetworkAvailable(getActivity())) {
            showAlertDialog(getString(R.string.oops), getString(R.string.no_internet_connection), getString(R.string.cancel), getActivity());
        } else {

            progressBar.setVisibility(View.VISIBLE);

            searchViewModel.getArtistList(searchTerm, offset, limit).observe(getViewLifecycleOwner(), new Observer<List<SearchArtistModel>>() {

                @Override
                public void onChanged(@Nullable List<SearchArtistModel> s) {

                    if (s == null) { // no results
                        progressBar.setVisibility(View.INVISIBLE);
                        recyclerView.setVisibility(View.INVISIBLE);
                        noResultsTextView.setVisibility(View.VISIBLE);

                        showAlertDialog(getString(R.string.oops), getString(R.string.something_went_wrong), getString(R.string.cancel), getActivity());

                    } else if (s.isEmpty()) { // no results
                        progressBar.setVisibility(View.INVISIBLE);
                        recyclerView.setVisibility(View.INVISIBLE);
                        noResultsTextView.setVisibility(View.VISIBLE);

                    } else { // has results
                        recyclerView.setVisibility(View.VISIBLE);
                        noResultsTextView.setVisibility(View.INVISIBLE);
                        searchArtistModels = s;

                        searchArtistAdapter = new SearchArtistAdapter(searchArtistModels, getActivity());
                        recyclerView.setAdapter(searchArtistAdapter);
                        recyclerView.setHasFixedSize(true);
                        progressBar.setVisibility(View.INVISIBLE);

                    }

                }


            });

        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        recyclerView.setVisibility(View.VISIBLE);
        noResultsTextView.setVisibility(View.GONE);

        return false;
    }


    @Override
    public void showAlertDialog(String title, String message, String positiveButtonText, Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButtonText, (dialogInterface, id) -> dialogInterface.dismiss())
                .create()
                .show();

    }


}