package com.example.d;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Util;

public class AudioViewHolder extends RecyclerView.ViewHolder {
    private Context mContext;
    private PlayerView mExoPlayerView;
    private TextView mTitleTextView;
    private SimpleExoPlayer mExoPlayer;

    public AudioViewHolder(@NonNull View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        mExoPlayerView = itemView.findViewById(R.id.exoplayer);
        mTitleTextView = itemView.findViewById(R.id.audioTitle);
    }

    public void setAudio(AudioItem audioItem) {
        mTitleTextView.setText(audioItem.getTitle());

        if (mExoPlayer == null) {
            // Create a new ExoPlayer instance
            TrackSelector trackSelector = new DefaultTrackSelector(mContext);
            mExoPlayer = new SimpleExoPlayer.Builder(mContext).setTrackSelector(trackSelector).build();
            mExoPlayerView.setPlayer(mExoPlayer);
        } else {
            mExoPlayer.stop();
        }

        Uri audioUri = Uri.parse(audioItem.getUrl());
        MediaSource mediaSource = buildMediaSource(audioUri);

        mExoPlayer.setMediaSource(mediaSource);
        mExoPlayer.prepare();
        mExoPlayer.setPlayWhenReady(false);
    }

    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(mContext,
                Util.getUserAgent(mContext, "YourApplicationName"));
        return new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(uri));
    }
}
