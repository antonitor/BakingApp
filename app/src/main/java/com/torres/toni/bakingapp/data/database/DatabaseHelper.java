package com.torres.toni.bakingapp.data.database;

import com.torres.toni.bakingapp.pojo.Ingredient;
import com.torres.toni.bakingapp.pojo.Recipe;
import com.torres.toni.bakingapp.pojo.Step;

import java.util.List;

public class DatabaseHelper {

    private RecipeDao dao;

    public DatabaseHelper(BakingDatabase database) {
        dao = database.getDao();
    }

    public void insertRecipes(List<Recipe> recipes) {
        dao.insertRecipes(recipes);
        for (Recipe recipe : recipes) {

            List<Ingredient> ingredients = recipe.getIngredients();
            for (Ingredient ingredient : ingredients) {
                ingredient.setRecipeId(recipe.getId());
            }
            dao.insertIngredients(ingredients);

            List<Step> steps = recipe.getSteps();
            for (Step step : steps) {
                step.setRecipeId(recipe.getId());
            }
            dao.insertSteps(steps);
        }
    }

    public RecipeDao getDao() {
        return dao;
    }

}
