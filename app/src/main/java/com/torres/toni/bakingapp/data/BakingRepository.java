package com.torres.toni.bakingapp.data;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.torres.toni.bakingapp.data.database.DatabaseHelper;
import com.torres.toni.bakingapp.pojo.Recipe;
import com.torres.toni.bakingapp.pojo.WebServiceResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BakingRepository {

    private static final String LOG_TAG = BakingRepository.class.getSimpleName();

    private final RecipesWebService mWebService;
    private final DatabaseHelper mDbHelper;
    private final WebServiceResponse mWebServiceResponse;
    public static BakingRepository instance;

    private BakingRepository(RecipesWebService webService, DatabaseHelper dbHelper) {
        mWebService = webService;
        mDbHelper = dbHelper;
        mWebServiceResponse = new WebServiceResponse();
    }

    public static BakingRepository getInstance(RecipesWebService webService, DatabaseHelper dbHelper) {
        if (instance == null) {
            instance = new BakingRepository(webService, dbHelper);
        }
        instance.refreshRecipeList();
        return instance;
    }

    private void refreshRecipeList() {
        mWebServiceResponse.setStatus(WebServiceResponse.Status.LOADING);
        mWebService.fetchRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (response.isSuccessful()) {
                    Log.d(LOG_TAG, "Data Refreshed From Network");
                    mWebServiceResponse.setStatus(WebServiceResponse.Status.SUCCESS);
                    mDbHelper.insertRecipes(response.body());
                } else {
                    Log.d(LOG_TAG, "Data couldn't be refreshed from network");
                    mWebServiceResponse.setStatus(WebServiceResponse.Status.FAILURE);
                }
            }
            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                mWebServiceResponse.setStatus(WebServiceResponse.Status.FAILURE);
                Log.d(LOG_TAG, t.getMessage());
            }
        });
    }

    public LiveData<List<Recipe>> getRecipes() {
        refreshRecipeList();
        return mDbHelper.getDao().getRecipes();
    }

    public WebServiceResponse getWebServiceResponse() {
        return mWebServiceResponse;
    }
}
