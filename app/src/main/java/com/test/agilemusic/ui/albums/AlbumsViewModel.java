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
                            // do anything with response

                            System.out.println("response: " + response);

                            try {

                                int resultCount = response.getInt("resultCount");
                                JSONArray resultsArray = response.getJSONArray("results");

                                List<AlbumsModel> list = null;
                                list = new ArrayList<>();

                                for (int a = 0; a < resultsArray.length(); a++) {

                                    JSONObject resultObject = resultsArray.getJSONObject(a);

                                    if (resultObject.getString("wrapperType").equals("collection")) {

                                        String albumId = resultObject.getString("collectionId");
                                        String albumName = resultObject.getString("collectionName");
                                        String releaseDate = resultObject.getString("releaseDate");
                                        String trackCount = String.valueOf(resultObject.getInt("trackCount"));
                                        String price = resultObject.getString("collectionPrice");
                                        String currency = resultObject.getString("currency");
                                        String artworkUrl = resultObject.getString("artworkUrl100");

                                        list.add(new AlbumsModel(albumId, albumName, releaseDate, price, trackCount, currency, artworkUrl));
                                        System.out.println("list.toString(): " + list.toString());

                                    }
                                }

//                                artistList.postValue(list);
                                albumList.setValue(list);

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