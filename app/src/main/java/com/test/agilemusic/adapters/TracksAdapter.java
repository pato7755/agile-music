package com.test.agilemusic.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.test.agilemusic.R;
import com.test.agilemusic.models.TrackModel;
import com.test.agilemusic.utilities.UtilityManager;

import java.io.IOException;
import java.util.List;

public class TracksAdapter extends RecyclerView.Adapter<TracksAdapter.MyViewHolder> {

    private List<TrackModel> list;
    private Context context;
    private UtilityManager utilityManager = new UtilityManager();
    private MediaPlayer mediaPlayer = new MediaPlayer() ;
    private boolean isMediaPlaying = false;




    public TracksAdapter(List<TrackModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public TracksAdapter(List<TrackModel> list) {
        this.list = list;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final View view;
        private final TextView trackNumberTextView;
        private final TextView trackNameTextView;
        private final TextView trackIdTextView;
        private final TextView previewUrlTextView;
        private final TextView isStreamableTextView;
        private final ImageButton previewButton;
        private final ImageButton stopButton;
        private final ImageButton likeButton;

        MyViewHolder(View view) {
            super(view);
            this.view = view;
            trackNumberTextView = view.findViewById(R.id.track_number_textview);
            trackNameTextView = view.findViewById(R.id.track_name_textview);
            trackIdTextView = view.findViewById(R.id.track_id_textview);
            previewUrlTextView = view.findViewById(R.id.preview_url_textview);
            isStreamableTextView = view.findViewById(R.id.is_streamable_textview);
            previewButton = view.findViewById(R.id.preview_button);
            stopButton = view.findViewById(R.id.stop_button);
            likeButton = view.findViewById(R.id.like_button);

        }


    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        TrackModel modelObject = list.get(position);

        holder.trackNameTextView.setTypeface(Typeface.createFromAsset(context.getAssets(), "Lato-Regular.ttf"));
        holder.trackNumberTextView.setTypeface(Typeface.createFromAsset(context.getAssets(), "Lato-Regular.ttf"));

        holder.trackNumberTextView.setText(modelObject.getTrackNumber());
        holder.trackNameTextView.setText(modelObject.getTrackName());
        holder.trackIdTextView.setText(modelObject.getTrackId());
        holder.previewUrlTextView.setText(modelObject.getPreviewUrl());
        holder.isStreamableTextView.setText(String.valueOf(modelObject.isStreamable()));

        holder.stopButton.setEnabled(false);


        holder.previewButton.setOnClickListener(v -> {

            if (!isMediaPlaying) {

                // media is not playing

                System.out.println("previewButton pressed");

                if (mediaPlayer == null)
                mediaPlayer = new MediaPlayer();

                String previewUrl = holder.previewUrlTextView.getText().toString();

                try {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        System.out.println("lollipop");
                        mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                        .setUsage(AudioAttributes.USAGE_MEDIA)
                                        .build()
                        );
                    } else {
                        System.out.println("lower than lollipop");
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    }


                    mediaPlayer.setDataSource(previewUrl);

                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer player) {
                            // Called when the MediaPlayer is ready to play
                            System.out.println("onPrepared");
                            mediaPlayer.start();
                            isMediaPlaying = true;

                            holder.previewButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_stop));
                        }

                    });

                    System.out.println("mediaPlayer.prepareAsync();");
                    mediaPlayer.prepareAsync();

                } catch (IOException ex) {
                    ex.getMessage();
                } catch (IllegalArgumentException ex) {
                    ex.getMessage();
                } catch (IllegalStateException ex) {
                    ex.getMessage();
                } catch (SecurityException ex) {
                    ex.getMessage();
                }

            } else {
                // media is playing

                System.out.println("isPlaying");

                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.release();

                mediaPlayer = null;
                
                holder.previewButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_play));

                isMediaPlaying = false;

            }

        });



        /*holder.stopButton.setOnClickListener(v -> {

            System.out.println("stopButton pressed");

            try {

                mediaPlayer.release();
                Toast.makeText(context, "Stopped", Toast.LENGTH_SHORT).show();

                holder.previewButton.setVisibility(View.VISIBLE);
                holder.previewButton.setEnabled(true);

                holder.stopButton.setVisibility(View.INVISIBLE);
                holder.stopButton.setEnabled(false);

            } catch (IllegalArgumentException ex) {
                ex.getMessage();
            } catch (IllegalStateException ex) {
                ex.getMessage();
            } catch (SecurityException ex) {
                ex.getMessage();
            }

        });
*/
//        mediaPlayer.setOnCompletionListener(mediaPlayer -> {

        if (mediaPlayer == null)
            mediaPlayer = new MediaPlayer();

        mediaPlayer.setOnCompletionListener(
                new MediaPlayer.OnCompletionListener(){
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        System.out.println("onCompletion");
                        isMediaPlaying = false;

                        holder.previewButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_play));

                        mediaPlayer.reset();
                        mediaPlayer.release();

//                        mediaPlayer = null;


//                        holder.previewButton.setImageResource(R.drawable.ic_play);
                    }



        });

        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {

                // The MediaPlayer has moved to the Error state, must be reset!
                // Then return true if the error has been handled
                System.out.println("mediaplayer onError");
                mediaPlayer.reset();
                mediaPlayer.release();

                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


}
