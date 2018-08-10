package com.torres.toni.bakingapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import com.torres.toni.bakingapp.data.BakingRepository;
import com.torres.toni.bakingapp.pojo.Recipe;

import java.util.List;
public class RecipesListViewModel extends ViewModel {

    private static final String LOG_TAG = RecipesListViewModel.class.getSimpleName();
    private LiveData<List<Recipe>> recipeList;

    public RecipesListViewModel(BakingRepository bakingRepository) {
        if (this.recipeList != null) {
            return;
        }
        this.recipeList = bakingRepository.getRecipes();
    }

    public LiveData<List<Recipe>> getRecipeList() {
        return this.recipeList;
    }
}
