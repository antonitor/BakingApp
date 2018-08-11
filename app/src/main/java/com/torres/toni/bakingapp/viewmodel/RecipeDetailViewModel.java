package com.torres.toni.bakingapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.torres.toni.bakingapp.data.database.BakingDatabase;
import com.torres.toni.bakingapp.pojo.Ingredient;
import com.torres.toni.bakingapp.pojo.Recipe;
import com.torres.toni.bakingapp.pojo.Step;

import java.util.List;

public class RecipeDetailViewModel extends ViewModel {

    private static final String LOG_TAG = RecipeDetailViewModel.class.getSimpleName();
    private int recipeId;
    private LiveData<Recipe> recipe;
    private LiveData<List<Ingredient>> ingredientList;
    private LiveData<List<Step>> stepList;
    private MutableLiveData<Step> stepSelected;
    private boolean twoPane;
    private int selectedStepId;
    private long playerCurrentPosition;
    private long playerCurrentWindowIndex;

    public RecipeDetailViewModel(BakingDatabase database, int id) {
        stepSelected = new MutableLiveData<>();
        this.recipeId = id;
        if (this.recipe == null) {
            this.recipe = database.getDao().getRecipe(id);
        }
        if (this.ingredientList == null) {
            this.ingredientList = database.getDao().getIngredients(id);
        }
        if (this.stepList == null) {
            this.stepList = database.getDao().getLiveSteps(id);
        }
    }

    public LiveData<Recipe> getRecipe() {
        return this.recipe;
    }

    public LiveData<List<Ingredient>> getIngredientList() {
        return ingredientList;
    }

    public LiveData<List<Step>> getStepList() {
        return stepList;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public LiveData<Step> getStepSelected() {
        return stepSelected;
    }

    public void setStepSelected(int stepId) {
        for (Step step : stepList.getValue()) {
            if (step.getId() == stepId) {
                selectedStepId = stepId;
                stepSelected.setValue(step);
                setPlayerCurrentPosition(0);
                setPlayerCurrentWindowIndex(0);
                return;
            }
        }
    }

    public void selectNextStep() {
        if (selectedStepId < stepList.getValue().size() -1 ) {
            setStepSelected(selectedStepId +1);
        }
    }

    public void selectPreviousStep() {
        if (selectedStepId > 0) {
            setStepSelected(selectedStepId -1);
        }
    }

    public boolean istwoPane() {
        return twoPane;
    }

    public void setwoPane(boolean twoPane) {
        this.twoPane = twoPane;
    }

    public long getPlayerCurrentPosition() {
        return playerCurrentPosition;
    }

    public void setPlayerCurrentPosition(long playerCurrentPosition) {
        this.playerCurrentPosition = playerCurrentPosition;
    }

    public long getPlayerCurrentWindowIndex() {
        return playerCurrentWindowIndex;
    }

    public void setPlayerCurrentWindowIndex(long playerCurrentWindowIndex) {
        this.playerCurrentWindowIndex = playerCurrentWindowIndex;
    }
}
