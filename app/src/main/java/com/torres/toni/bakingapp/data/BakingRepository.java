package com.torres.toni.bakingapp.data;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.torres.toni.bakingapp.data.database.DatabaseHelper;
import com.torres.toni.bakingapp.data.database.RecipeDao;
import com.torres.toni.bakingapp.pojo.Ingredient;
import com.torres.toni.bakingapp.pojo.Recipe;
import com.torres.toni.bakingapp.pojo.Step;

import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BakingRepository {

    private static final String LOG_TAG = BakingRepository.class.getSimpleName();

    private final RecipesWebService mWebService;
    private final DatabaseHelper mDbHelper;
    private final Executor mExecutor;

    public BakingRepository(RecipesWebService webService, DatabaseHelper dbHelper, Executor executor) {
        mWebService = webService;
        mDbHelper = dbHelper;
        mExecutor = executor;
    }

    public LiveData<List<Recipe>> getRecipes() {
        refreshRecipeList();
        return mDbHelper.getDao().getRecipes();
    }

    public LiveData<List<Ingredient>> getIngredients(int id){
        return mDbHelper.getDao().getIngredients(id);
    }

    public LiveData<Recipe> getRecipe(int id) {
        return mDbHelper.getDao().getRecipe(id);
    }

    private void refreshRecipeList() {

        mWebService.fetchRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                Log.d(LOG_TAG, "Data Refreshed From Network");
                List<Recipe> recipes = response.body();
                mDbHelper.insertRecipes(recipes);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d(LOG_TAG, t.getMessage());
            }
        });

        /*
        mExecutor.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        mWebService.fetchRecipes().enqueue(new Callback<List<Recipe>>() {
                            @Override
                            public void onResponse(Call<List<Recipe>> call, final Response<List<Recipe>> response) {
                                Log.d(LOG_TAG, "Data Refreshed From Network");
                                mExecutor.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        List<Recipe> recipes = response.body();
                                        mDbHelper.insertRecipes(recipes);
                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                                Log.d(LOG_TAG, t.getMessage());
                            }
                        });
                    }
                }
        );
        */
    }

}
