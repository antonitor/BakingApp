package com.torres.toni.bakingapp.pojo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.torres.toni.bakingapp.data.BakingContract.RecipeEntry;

import java.util.List;

@Entity(tableName = RecipeEntry.TABLE_NAME)
public class Recipe {

    @PrimaryKey
    @ColumnInfo(index = true, name = RecipeEntry.COLUMN_ID)
    @SerializedName(RecipeEntry.COLUMN_ID)
    @Expose
    private int id;

    @ColumnInfo(name = RecipeEntry.COLUMN_NAME)
    @SerializedName(RecipeEntry.COLUMN_NAME)
    @Expose
    private String name;

    @Ignore
    @SerializedName(RecipeEntry.JSON_INGREDIENTS)
    @Expose
    private List<Ingredient> ingredients = null;

    @Ignore
    @SerializedName(RecipeEntry.JSON_STEPS)
    @Expose
    private List<Step> steps = null;

    @ColumnInfo(name = RecipeEntry.COLUMN_SERVINGS)
    @SerializedName(RecipeEntry.COLUMN_SERVINGS)
    @Expose
    private int servings;


    @ColumnInfo(name = RecipeEntry.COLUMN_IMAGE)
    @SerializedName(RecipeEntry.COLUMN_IMAGE)
    @Expose
    private String image;


    public Recipe() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
