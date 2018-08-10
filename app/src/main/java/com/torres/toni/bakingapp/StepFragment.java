package com.torres.toni.bakingapp;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.torres.toni.bakingapp.pojo.Step;
import com.torres.toni.bakingapp.utils.ExoPlayerVideoHandler;
import com.torres.toni.bakingapp.viewmodel.RecipeDetailViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;


public class StepFragment extends Fragment {

    public static final String LOG_TAG = StepFragment.class.getSimpleName();
    RecipeDetailViewModel mRecipeDetailViewModel;
    @BindView(R.id.test)
    TextView test;
    @BindView(R.id.nextStep)
    Button nextStepButton;
    @BindView(R.id.previousStep)
    Button previousStepButton;
    @BindView(R.id.simple_exoplayer_view)
    PlayerView mPlayerView;
    ExoPlayerVideoHandler mExoPlayerHandler;

    public StepFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this, rootView);
        hideNavigationIfLandscape();
        //******************
        mExoPlayerHandler = ExoPlayerVideoHandler.getInstance();
        //**********
        setUpViewModel();
        setUpButtons();
        initializePlayer();
        return rootView;
    }


    private void setUpViewModel() {
        mRecipeDetailViewModel = ViewModelProviders.of(getActivity()).get(RecipeDetailViewModel.class);
        final Observer<Step> stepObserver = new Observer<Step>() {
            @Override
            public void onChanged(@Nullable Step step) {
                Log.d(LOG_TAG, "IS FUCKIN CHANGED");
                test.setText(step.getDescription());
                mRecipeDetailViewModel.setPlayerCurrentPosition(0);
                if (mRecipeDetailViewModel.getStepSelected().getValue().getVideoURL().equals("")) {
                    mExoPlayerHandler.goToBackground();
                    mPlayerView.setVisibility(View.GONE);
                } else {
                    mPlayerView.setVisibility(View.VISIBLE);
                    mExoPlayerHandler.setPlayerPlaying(true);
                    mExoPlayerHandler.goToForeground();
                    Uri uri = Uri.parse(mRecipeDetailViewModel.getStepSelected().getValue().getVideoURL());
                    mExoPlayerHandler.prepareExoPlayerForSource(getContext(), uri, mPlayerView);
                    mExoPlayerHandler.seekExoPlayerTo((int) mRecipeDetailViewModel.getPlayerCurrentWindowIndex(),
                            mRecipeDetailViewModel.getPlayerCurrentPosition());
                }

            }
        };
        mRecipeDetailViewModel.getStepSelected().observe(this, stepObserver);
    }

    private void setUpButtons() {
        if (mRecipeDetailViewModel.istwoPane()) {
            nextStepButton.setVisibility(View.GONE);
            previousStepButton.setVisibility(View.GONE);
        } else {
            nextStepButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mRecipeDetailViewModel.selectNextStep();
                }
            });
            previousStepButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mRecipeDetailViewModel.selectPreviousStep();
                }
            });
        }
    }

    private void initializePlayer() {
        // mExoPlayerHandler = ExoPlayerVideoHandler.getInstance();
        if (mRecipeDetailViewModel.getStepSelected().getValue() != null) {
            Uri uri = Uri.parse(mRecipeDetailViewModel.getStepSelected().getValue().getVideoURL());
            mExoPlayerHandler.prepareExoPlayerForSource(getContext(), uri, mPlayerView);
            mExoPlayerHandler.seekExoPlayerTo((int) mRecipeDetailViewModel.getPlayerCurrentWindowIndex(),
                    mRecipeDetailViewModel.getPlayerCurrentPosition());
        }
    }


    @SuppressLint("InlinedApi")
    private void hideNavigationIfLandscape() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mPlayerView.setSystemUiVisibility(SYSTEM_UI_FLAG_FULLSCREEN | SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        } else {
            ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        }
    }


    @Override
    public void onDestroyView() {
        Log.d(LOG_TAG, "DESTROY VIEW");
        super.onDestroyView();
        mRecipeDetailViewModel.setPlayerCurrentPosition(mExoPlayerHandler.getPlayer().getCurrentPosition());
        mRecipeDetailViewModel.setPlayerCurrentWindowIndex(mExoPlayerHandler.getPlayer().getCurrentWindowIndex());
    }

    @Override
    public void onStop() {
        Log.d(LOG_TAG, "ON STOP");
        super.onStop();
        mExoPlayerHandler.goToBackground();
    }

    @Override
    public void onStart() {
        Log.d(LOG_TAG, "ON START");
        super.onStart();
        mExoPlayerHandler.goToForeground();
    }
}
