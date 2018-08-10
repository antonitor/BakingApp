package com.torres.toni.bakingapp.pojo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.torres.toni.bakingapp.data.BakingContract.RecipeEntry;
import com.torres.toni.bakingapp.data.BakingContract.IngredientEntry;


@Entity(tableName = IngredientEntry.TABLE_NAME,
        foreignKeys = @ForeignKey(entity = Recipe.class,
                parentColumns = RecipeEntry.COLUMN_ID,
                childColumns = IngredientEntry.COLUMN_RECIPE_ID),
        primaryKeys = {IngredientEntry.COLUMN_INGREDIENT, IngredientEntry.COLUMN_RECIPE_ID})
public class Ingredient {

    @ColumnInfo(name = IngredientEntry.COLUMN_QUANTITY)
    @SerializedName(IngredientEntry.COLUMN_QUANTITY)
    @Expose
    private float quantity;

    @ColumnInfo(name = IngredientEntry.COLUMN_MEASURE)
    @SerializedName(IngredientEntry.COLUMN_MEASURE)
    @Expose
    private String measure;

    @ColumnInfo(name = IngredientEntry.COLUMN_INGREDIENT)
    @SerializedName(IngredientEntry.COLUMN_INGREDIENT)
    @Expose
    @NonNull
    private String ingredient;

    @ColumnInfo(index = true, name = IngredientEntry.COLUMN_RECIPE_ID)
    @NonNull
    private int recipeId;

    public Ingredient() {
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }
}
