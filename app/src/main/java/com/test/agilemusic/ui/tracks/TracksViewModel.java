package com.test.agilemusic.ui.tracks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.test.agilemusic.communication.UrlClass;
import com.test.agilemusic.models.AlbumsModel;
import com.test.agilemusic.models.TrackModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TracksViewModel extends ViewModel {

    private MutableLiveData<List<TrackModel>> trackList;

    public LiveData<List<TrackModel>> getTrackList(String albumId) {

        if (trackList == null) {
            this.trackList = new MutableLiveData<>();
        }
        getTracks(albumId);
        return trackList;
    }


    public void getTracks(String albumId) {

        System.out.println(UrlClass.baseUrl + UrlClass.LOOKUP);

        try {

            AndroidNetworking.get(UrlClass.baseUrl + UrlClass.LOOKUP)
                    .addHeaders("Content-Type", "application/json")
                    .addQueryParameter(UrlClass.ID, albumId)
                    .addQueryParameter(UrlClass.ENTITY, UrlClass.SONG)
                    .setTag("get-tracks")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {

                            System.out.println("response: " + response);

                            try {

                                int resultCount = response.getInt("resultCount");
                                JSONArray resultsArray = response.getJSONArray("results");

                                List<TrackModel> list = new ArrayList<>();

                                if (resultCount > 1) {

                                    for (int a = 0; a < resultsArray.length(); a++) {

                                        JSONObject resultObject = resultsArray.getJSONObject(a);

                                        if (resultObject.getString("wrapperType").equals("track")) {

                                            String trackId = resultObject.getString("trackId");
                                            String trackName = resultObject.getString("trackName");
                                            String trackNumber = resultObject.getString("trackNumber");
                                            String previewUrl = resultObject.getString("previewUrl");
                                            boolean isStreamable = resultObject.getBoolean("isStreamable");

                                            list.add(new TrackModel(trackId, trackName, trackNumber, isStreamable, previewUrl));

                                        }
                                    }

                                }

                                trackList.setValue(list);

                            } catch (Exception ex) {
                                System.out.println("exception: " + ex.getMessage());
                                trackList.postValue(null);
                            }

                        }

                        @Override
                        public void onError(ANError error) {
                            // handle error

                            trackList.postValue(null);

                        }
                    });

        } catch (Exception ex) {
            trackList.postValue(null);
            System.out.println(ex.getMessage());
        }

    }

}