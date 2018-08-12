package com.torres.toni.bakingapp.utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

public class ExoPlayerVideoHandler {

    private static ExoPlayerVideoHandler instance;
    private static final String LOG_TAG = ExoPlayerVideoHandler.class.getSimpleName();

    public static ExoPlayerVideoHandler getInstance() {
        if (instance == null) {
            instance = new ExoPlayerVideoHandler();
        }
        return instance;
    }

    private SimpleExoPlayer player;
    private Uri uri;
    private boolean isPlayerPlaying = true;

    private ExoPlayerVideoHandler() {
    }

    public void prepareExoPlayerForSource(Context context, Uri uri, PlayerView exoPlayerView) {
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(context),
                    new DefaultTrackSelector(),
                    new DefaultLoadControl());
            player.setPlayWhenReady(true);
        }
        if (!uri.equals(this.uri)) {
            this.uri = uri;
            MediaSource source = buildMediaSource(uri);
            player.prepare(source, true, false);
        }
        exoPlayerView.setPlayer(player);
    }

    public void seekExoPlayerTo(int currentWindowIndex, long currentPosition) {
        player.seekTo(currentWindowIndex, currentPosition);
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                createMediaSource(uri);
    }

    public SimpleExoPlayer getPlayer() {
        return player;
    }

    public void releaseVideoPlayer() {
        if (player != null) {
            player.release();
        }
        player = null;
    }

    public void goToBackground() {
        if (player != null) {
            isPlayerPlaying = player.getPlayWhenReady();
            player.setPlayWhenReady(false);
        }
    }

    public void goToForeground() {
        if (player != null) {
            player.setPlayWhenReady(isPlayerPlaying);
        }
    }

    public void setPlayerPlaying(boolean playerPlaying) {
        isPlayerPlaying = playerPlaying;
    }
}

