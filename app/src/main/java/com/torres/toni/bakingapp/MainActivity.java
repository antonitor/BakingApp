package com.torres.toni.bakingapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.torres.toni.bakingapp.data.BakingRepository;
import com.torres.toni.bakingapp.data.RecipesWebService;
import com.torres.toni.bakingapp.data.database.BakingDatabase;
import com.torres.toni.bakingapp.data.database.DatabaseHelper;
import com.torres.toni.bakingapp.pojo.Recipe;
import com.torres.toni.bakingapp.pojo.WebServiceResponse;
import com.torres.toni.bakingapp.viewmodel.RecipesListViewModelFactory;
import com.torres.toni.bakingapp.viewmodel.RecipesListViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity implements RecipeListAdapter.RecipeListClickHandler{

    private RecipeListAdapter mRecipeListAdapter;
    @BindView(R.id.recyclerview_baking) RecyclerView mRecyclerView;
    @BindView(R.id.pb_loading_indicator) ProgressBar mLoadingIndicator;
    private BakingDatabase mDb;
    private Context mContext = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //RECYCLER VIEW
        GridLayoutManager layoutManager = new GridLayoutManager(this, calculateNoOfColumns());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecipeListAdapter = new RecipeListAdapter(null, this, this);
        mRecyclerView.setAdapter(mRecipeListAdapter);

        //DATABASE
        mDb = BakingDatabase.getInstance(this);

        setUpViewModel();
    }

    private void setUpViewModel() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://go.udacity.com/")
                .addConverterFactory(GsonConverterFactory.create());
        final RecipesWebService recipesWebService = builder.build().create(RecipesWebService.class);
        BakingRepository repository = BakingRepository.getInstance(recipesWebService, new DatabaseHelper(mDb));
        RecipesListViewModel viewModel = ViewModelProviders.of(this, new RecipesListViewModelFactory(repository)).get(RecipesListViewModel.class);
        final Observer<List<Recipe>> recipeListObserver = new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                mRecipeListAdapter.setRecipes(recipes);
            }
        };
        final Observer<WebServiceResponse.Status> serviceResponseObserver = new Observer<WebServiceResponse.Status>() {
            @Override
            public void onChanged(@Nullable WebServiceResponse.Status status) {
                switch (status) {
                    case LOADING:
                        mLoadingIndicator.setVisibility(View.VISIBLE);
                        break;
                    case SUCCESS:
                        mLoadingIndicator.setVisibility(View.INVISIBLE);
                        Toast.makeText(mContext, "SUCCESS LOADING DATA FROM NETWORK", Toast.LENGTH_SHORT).show();
                        break;
                    case FAILURE:
                        mLoadingIndicator.setVisibility(View.INVISIBLE);
                        Toast.makeText(mContext, "IMPOSIBLE LOADING DATA FROM NETWORK", Toast.LENGTH_SHORT).show();
                }
            }
        };
        viewModel.getRecipeList().observe(this, recipeListObserver);
        viewModel.getWebServiceResponse().getStatus().observe(this, serviceResponseObserver);
    }

    private int calculateNoOfColumns() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 270);
        return noOfColumns;
    }

    @Override
    public void onRecipeItemClicked(int recipeId) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(getString(R.string.extra_recipe_id), recipeId);
        startActivity(intent);
    }
}
