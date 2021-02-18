package com.test.agilemusic.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.test.agilemusic.communication.UrlClass;
import com.test.agilemusic.models.SearchArtistModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchViewModel extends ViewModel {

    private MutableLiveData<List<SearchArtistModel>> artistList = null;

    public LiveData<List<SearchArtistModel>> getArtistList(String searchTerm) {
        System.out.println("getArtistList");
        if (artistList == null) {
            this.artistList = new MutableLiveData<>();
        }
        searchArtistByName(searchTerm);
        System.out.println("artistList: " + artistList.toString());
        return artistList;
    }


    public void searchArtistByName(String term) {

        System.out.println(UrlClass.baseUrl + UrlClass.SEARCH);

        try {

            AndroidNetworking.get(UrlClass.baseUrl + UrlClass.SEARCH)
                    .addHeaders("Content-Type", "application/json")
                    .addQueryParameter(UrlClass.TERM, term)
                    .addQueryParameter(UrlClass.ENTITY, UrlClass.ALL_ARTIST_ENTITY)
                    .addQueryParameter(UrlClass.LIMIT, "20")
                    .setTag("search")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // do anything with response

                            System.out.println("response: " + response);

                            try {

                                int resultCount = response.getInt("resultCount");
                                JSONArray resultsArray = response.getJSONArray("results");

                                List<SearchArtistModel> list = new ArrayList<>();

                                if (resultCount != 0) {

                                    for (int a = 0; a < resultsArray.length(); a++) {

                                        JSONObject resultObject = resultsArray.getJSONObject(a);

                                        String artistName = resultObject.getString("artistName");

                                        String genre = "";
                                        if (resultObject.has("primaryGenreName"))
                                            genre = resultObject.getString("primaryGenreName");
                                        else
                                            genre = "No particular genre";

                                        String artistId = resultObject.getString("artistId");

                                        list.add(new SearchArtistModel(artistId, artistName, genre));
//                                        System.out.println("list.toString(): " + list.toString());

                                    }

                                }
                                artistList.postValue(list);

                            } catch (Exception ex) {
                                System.out.println("exception: " + ex.getMessage());
                                artistList.postValue(null);
                            }

                        }

                        @Override
                        public void onError(ANError error) {
                            // handle error

                            artistList.postValue(null);
                        }
                    });

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            artistList.postValue(null);
        }

    }


}