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

import com.google.android.exoplayer2.ui.PlayerView;
import com.torres.toni.bakingapp.pojo.Step;
import com.torres.toni.bakingapp.utils.ExoPlayerVideoHandler;
import com.torres.toni.bakingapp.viewmodel.RecipeDetailViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;


public class StepFragment extends Fragment {

    public static final String LOG_TAG = StepFragment.class.getSimpleName();
    RecipeDetailViewModel mViewModel;
    @BindView(R.id.description)
    TextView descriptionTextView;
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
        resetPlayer();
        return rootView;
    }


    private void setUpViewModel() {
        mViewModel = ViewModelProviders.of(getActivity()).get(RecipeDetailViewModel.class);
        final Observer<Step> stepObserver = new Observer<Step>() {
            @Override
            public void onChanged(@Nullable Step step) {
                Log.d(LOG_TAG, "STEP SELECTED CHANGED ->>>>> " + step.getDescription());
                descriptionTextView.setText(step.getDescription());
                if (mViewModel.getStepSelected().getValue().getVideoURL().equals("")) {
                    mExoPlayerHandler.goToBackground();
                    mPlayerView.setVisibility(View.GONE);
                } else {
                    mPlayerView.setVisibility(View.VISIBLE);
                    mExoPlayerHandler.setPlayerPlaying(true);
                    mExoPlayerHandler.goToForeground();
                    Uri uri = Uri.parse(mViewModel.getStepSelected().getValue().getVideoURL());
                    mExoPlayerHandler.prepareExoPlayerForSource(getContext(), uri, mPlayerView);
                    mExoPlayerHandler.seekExoPlayerTo((int) mViewModel.getPlayerCurrentWindowIndex(),
                            mViewModel.getPlayerCurrentPosition());
                }

            }
        };
        mViewModel.getStepSelected().observe(this, stepObserver);
    }

    private void setUpButtons() {
        if (mViewModel.istwoPane()) {
            nextStepButton.setVisibility(View.GONE);
            previousStepButton.setVisibility(View.GONE);
        } else {
            nextStepButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mViewModel.selectNextStep();
                }
            });
            previousStepButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mViewModel.selectPreviousStep();
                }
            });
        }
    }

    private void resetPlayer() {
        mExoPlayerHandler = ExoPlayerVideoHandler.getInstance();
        if (mViewModel.getStepSelected().getValue() != null && mViewModel.getStepSelected().getValue().getVideoURL() != "") {
            Uri uri = Uri.parse(mViewModel.getStepSelected().getValue().getVideoURL());
            mExoPlayerHandler.prepareExoPlayerForSource(getContext(), uri, mPlayerView);
            mExoPlayerHandler.seekExoPlayerTo((int) mViewModel.getPlayerCurrentWindowIndex(),
                    mViewModel.getPlayerCurrentPosition());
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
    public void onStop() {
        super.onStop();
        if (getActivity().isChangingConfigurations()) {
            mViewModel.setPlayerCurrentPosition(mExoPlayerHandler.getPlayer().getCurrentPosition());
            mViewModel.setPlayerCurrentWindowIndex(mExoPlayerHandler.getPlayer().getCurrentWindowIndex());
        }
        mExoPlayerHandler.goToBackground();
    }

    @Override
    public void onStart() {
        super.onStart();
        mExoPlayerHandler.goToForeground();
    }
}
