package com.test.agilemusic.ui.albums;

import android.os.Build;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.test.agilemusic.communication.UrlClass;
import com.test.agilemusic.models.AlbumsModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AlbumsViewModel extends ViewModel {

    private MutableLiveData<List<AlbumsModel>> albumList;

    public LiveData<List<AlbumsModel>> getAlbumList(String id) {

        if (albumList == null) {
            this.albumList = new MutableLiveData<>();
        }
        getAlbums(id);
        return albumList;
    }


    public void getAlbums(String id) {

        System.out.println(UrlClass.baseUrl + UrlClass.LOOKUP);

        try {

            AndroidNetworking.get(UrlClass.baseUrl + UrlClass.LOOKUP)
                    .addHeaders("Content-Type", "application/json")
                    .addQueryParameter(UrlClass.ID, id)
                    .addQueryParameter(UrlClass.ENTITY, UrlClass.ALBUM)
                    .addQueryParameter(UrlClass.LIMIT, "20")
                    .setTag("get-album")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {

                            System.out.println("response: " + response);

                            try {

                                int resultCount = response.getInt("resultCount");
                                JSONArray resultsArray = response.getJSONArray("results");

                                List<AlbumsModel> list = new ArrayList<>();

                                if (resultCount > 1) {

                                    for (int a = 0; a < resultsArray.length(); a++) {

                                        JSONObject resultObject = resultsArray.getJSONObject(a);

                                        if (resultObject.getString("wrapperType").equals("collection")) {

                                            String albumId = resultObject.has("collectionId") ? resultObject.getString("collectionId") : "";
                                            String albumName = resultObject.has("collectionName") ? resultObject.getString("collectionName") : "";
                                            String releaseDate = resultObject.has("releaseDate") ? resultObject.getString("releaseDate") : "";
                                            String trackCount = resultObject.has("trackCount") ? resultObject.getString("trackCount") : "";
                                            String price = resultObject.has("collectionPrice") ? resultObject.getString("collectionPrice") : "0.00";
                                            String currency = resultObject.has("currency") ? resultObject.getString("currency") : "USD";
                                            String artworkUrl = resultObject.has("artworkUrl100") ? resultObject.getString("artworkUrl100") : "";

                                            list.add(new AlbumsModel(albumId, albumName, releaseDate, price, trackCount, currency, artworkUrl));

                                        }
                                    }
                                }

                                albumList.postValue(list);

                            } catch (Exception ex) {
                                System.out.println("exception: " + ex.getMessage());
                                albumList.postValue(null);
                            }

                        }

                        @Override
                        public void onError(ANError error) {
                            // handle error

                            albumList.postValue(null);

                        }
                    });

        } catch (Exception ex) {
            albumList.postValue(null);
            System.out.println(ex.getMessage());
        }

    }

}