package com.torres.toni.bakingapp.data;

import com.torres.toni.bakingapp.pojo.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipesWebService {

    @GET("android-baking-app-json/")
    Call<List<Recipe>> fetchRecipes();
}
