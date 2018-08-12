package com.torres.toni.bakingapp.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.torres.toni.bakingapp.data.BakingContract;
import com.torres.toni.bakingapp.pojo.Ingredient;
import com.torres.toni.bakingapp.pojo.Recipe;
import com.torres.toni.bakingapp.pojo.Step;

import java.util.List;

@Dao
public abstract class RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertRecipes(List<Recipe> recipes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertIngredients(List<Ingredient> ingredients);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertSteps(List<Step> steps);

    @Query("SELECT * FROM " + BakingContract.RecipeEntry.TABLE_NAME)
    public abstract LiveData<List<Recipe>> getRecipes();

    @Query("SELECT * FROM " + BakingContract.RecipeEntry.TABLE_NAME + " WHERE " + BakingContract.RecipeEntry.COLUMN_ID + " = :id")
    public abstract Recipe getRecipe(int id);

    @Query("SELECT * FROM " + BakingContract.IngredientEntry.TABLE_NAME + " WHERE " + BakingContract.IngredientEntry.COLUMN_RECIPE_ID + " = :id")
    public abstract List<Ingredient> getIngredients(int id);

    @Query("SELECT * FROM " + BakingContract.StepEntry.TABLE_NAME + " WHERE " + BakingContract.StepEntry.COLUMN_RECIPE_ID + " = :id")
    public abstract LiveData<List<Step>> getLiveSteps(int id);

    @Query("SELECT * FROM " + BakingContract.StepEntry.TABLE_NAME + " WHERE " + BakingContract.StepEntry.COLUMN_RECIPE_ID + " = :id")
    public abstract List<Step> getSteps(int id);

}