package com.test.agilemusic.ui.videos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.test.agilemusic.communication.UrlClass;
import com.test.agilemusic.models.SearchArtistModel;
import com.test.agilemusic.models.VideosModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VideosViewModel extends ViewModel {

    private MutableLiveData<List<VideosModel>> videosList = null;

    public LiveData<List<VideosModel>> getVideosByArtistName(String searchTerm, int offset, int limit) {
        if (videosList == null) {
            this.videosList = new MutableLiveData<>();
        }
        searchVideo(searchTerm, offset, limit);
        return videosList;
    }


    public void searchVideo(String term, int offset, int limit) {

        System.out.println(UrlClass.baseUrl + UrlClass.SEARCH);

        try {

            AndroidNetworking.get(UrlClass.baseUrl + UrlClass.SEARCH)
                    .addHeaders("Content-Type", "application/json")
                    .addQueryParameter(UrlClass.TERM, term)
                    .addQueryParameter(UrlClass.ENTITY, UrlClass.MUSIC_VIDEO)
                    .addQueryParameter(UrlClass.OFFSET, String.valueOf(offset))
                    .addQueryParameter(UrlClass.LIMIT, String.valueOf(limit))
                    .setTag("search")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {

                            System.out.println("response: " + response);

                            try {

                                int resultCount = response.getInt("resultCount");
                                JSONArray resultsArray = response.getJSONArray("results");

                                List<VideosModel> list = new ArrayList<>();

                                if (resultCount != 0) {

                                    for (int a = 0; a < resultsArray.length(); a++) {

                                        JSONObject resultObject = resultsArray.getJSONObject(a);

                                        String trackName = resultObject.has("trackName") ? resultObject.getString("trackName") : "";
                                        String previewUrl = resultObject.has("previewUrl") ? resultObject.getString("previewUrl") : "";
                                        String artworkUrl = resultObject.has("artworkUrl100") ? resultObject.getString("artworkUrl100") : "";

                                        list.add(new VideosModel(trackName, artworkUrl, previewUrl));

                                    }

                                }
                                videosList.postValue(list);

                            } catch (Exception ex) {
                                System.out.println("exception: " + ex.getMessage());
                                videosList.postValue(null);
                            }

                        }

                        @Override
                        public void onError(ANError error) {
                            // handle error

                            videosList.postValue(null);
                        }
                    });

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            videosList.postValue(null);
        }

    }


}