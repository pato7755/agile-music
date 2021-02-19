package com.test.agilemusic.ui.videoplayer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.test.agilemusic.R;
import com.test.agilemusic.communication.CheckInternetConnection;
import com.test.agilemusic.utilities.ErrorDialogInterface;

public class VideoPlayerFragment extends Fragment implements ErrorDialogInterface {

    View root;
    String previewUrl;
    VideoView videoView;
    MediaController mediacontroller;
    CheckInternetConnection checkInternetConnection = new CheckInternetConnection();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_video_player, container, false);

        if (getArguments() != null) {
            previewUrl = getArguments().getString("url");
        }

        initViews();

        return root;
    }

    public void initViews() {

        videoView = root.findViewById(R.id.video_view);

        try {

            if (!checkInternetConnection.isNetworkAvailable(getActivity())) {
                showAlertDialog(getString(R.string.oops), getString(R.string.no_internet_connection), getString(R.string.cancel), getActivity());
            } else {

                mediacontroller = new MediaController(getContext());
                mediacontroller.setAnchorView(videoView);

                videoView.setMediaController(mediacontroller);
                videoView.setVideoURI(Uri.parse(previewUrl));
                videoView.requestFocus();

                videoView.setOnPreparedListener(mp -> mp.setOnVideoSizeChangedListener((mp1, width, height) -> {
                    videoView.setMediaController(mediacontroller);
                    mediacontroller.setAnchorView(videoView);

                }));

                videoView.setOnCompletionListener(mp -> {
                    Toast.makeText(getContext(), "Video over", Toast.LENGTH_SHORT).show();
                    mp.release();

                });

                videoView.setOnErrorListener((mp, what, extra) -> false);

            }
        } catch (Exception ex){

            showAlertDialog(getString(R.string.oops), getString(R.string.something_went_wrong), getString(R.string.cancel), getActivity());

        }
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