package com.torres.toni.bakingapp;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.torres.toni.bakingapp.data.database.BakingDatabase;
import com.torres.toni.bakingapp.viewmodel.RecipeDetailViewModel;
import com.torres.toni.bakingapp.viewmodel.RecipeDetailViewModelFactory;

public class RecipeDetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = RecipeDetailActivity.class.getSimpleName();
    private boolean mTwoPane;
    RecipeDetailViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().show();
        setContentView(R.layout.activity_recipe_detail);
        onLandscapeHideIU();
        Intent intent = getIntent();
        if (findViewById(R.id.step_detail_two_pane) != null) {
            mTwoPane = true;
            setUpViewModel(intent.getIntExtra(getString(R.string.extra_recipe_id), 0));
            if (savedInstanceState == null) {
                RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.step_list_two_pane, recipeDetailFragment)
                        .commit();
            }
        } else {
            mTwoPane = false;
            setUpViewModel(intent.getIntExtra(getString(R.string.extra_recipe_id), 0));
            if (savedInstanceState == null) {
                RecipeDetailFragment detailFragment = new RecipeDetailFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.fragment_container, detailFragment)
                        .commit();
            }
        }

    }

    private void setUpViewModel(int id) {
        BakingDatabase database = BakingDatabase.getInstance(this);
        mViewModel = ViewModelProviders.of(this, new RecipeDetailViewModelFactory(database, id)).get(RecipeDetailViewModel.class);
        mViewModel.setwoPane(mTwoPane);
    }


    public void onLandscapeHideIU() {
        View decorView = getWindow().getDecorView();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            float dp = Resources.getSystem().getDisplayMetrics().heightPixels / Resources.getSystem().getDisplayMetrics().density;
            if (dp < 600) {
                getSupportActionBar().hide();
                decorView.setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN);
            }
        } else {
            float dp = Resources.getSystem().getDisplayMetrics().widthPixels / Resources.getSystem().getDisplayMetrics().density;
            if (dp < 600) {
                getSupportActionBar().show();
            }
        }
    }


    @Override
    public void onBackPressed() {
        FragmentManager mgr = getSupportFragmentManager();
        if (mgr.getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            mgr.popBackStackImmediate();
        }
    }
}
