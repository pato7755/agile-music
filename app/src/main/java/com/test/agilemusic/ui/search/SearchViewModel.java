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

    private MutableLiveData<List<SearchArtistModel>> artistList;

    public LiveData<List<SearchArtistModel>> getArtistList(String searchTerm) {
        System.out.println("getArtistList");
        if (artistList == null) {
            this.artistList = new MutableLiveData<>();
        }
        searchArtistByName(searchTerm);
        return artistList;
    }


    public void searchArtistByName(String term) {

        System.out.println(UrlClass.baseUrl + UrlClass.SEARCH);

        try {

            AndroidNetworking.get(UrlClass.baseUrl + UrlClass.SEARCH)
                    .addHeaders("Content-Type", "application/json")
                    .addQueryParameter(UrlClass.TERM, term)
                    .addQueryParameter(UrlClass.MEDIA, UrlClass.MUSIC_MEDIA_TYPE)
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

                                List<SearchArtistModel> list = null;
                                list = new ArrayList<>();

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
                                    System.out.println("list.toString(): " + list.toString());

                                }


//                                artistList.postValue(list);
                                artistList.postValue(list);

                            } catch (Exception ex) {
                                System.out.println("exception: " + ex.getMessage());
                            }

                        }

                        @Override
                        public void onError(ANError error) {
                            // handle error

//                            progressBar.setVisibility(View.GONE);
//                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                            System.out.println("error: " + error);
                            System.out.println(error.getErrorCode());
                            System.out.println(error.getErrorBody());
                            System.out.println(error.getErrorDetail());

                            if (error.getErrorCode() != 0) {
                                // received error from server
                                System.out.println("error.getErrorCode() != 0)");
                            } else {
                                System.out.println("error.getErrorCode() == 0)");
                            }
                        }
                    });

        } catch (Exception ex) {
            System.out.println("exception");
            System.out.println(ex.getMessage());
        }

    }


}