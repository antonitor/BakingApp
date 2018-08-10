package com.torres.toni.bakingapp.pojo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.torres.toni.bakingapp.data.BakingContract.RecipeEntry;
import com.torres.toni.bakingapp.data.BakingContract.StepEntry;


@Entity(tableName = StepEntry.TABLE_NAME,
        foreignKeys = @ForeignKey(entity = Recipe.class,
                parentColumns = RecipeEntry.COLUMN_ID,
                childColumns = StepEntry.COLUMN_RECIPE_ID),
        primaryKeys = {StepEntry.COLUMN_ID, StepEntry.COLUMN_RECIPE_ID})
public class Step {

    @ColumnInfo(index = true, name = StepEntry.COLUMN_ID)
    @SerializedName(StepEntry.COLUMN_ID)
    @Expose
    private int id;


    @ColumnInfo(name = StepEntry.COLUMN_SHORT_DESCRIPTION)
    @SerializedName(StepEntry.COLUMN_SHORT_DESCRIPTION)
    @Expose
    private String shortDescription;


    @ColumnInfo(name = StepEntry.COLUMN_DESCRIPTION)
    @SerializedName(StepEntry.COLUMN_DESCRIPTION)
    @Expose
    private String description;


    @ColumnInfo(name = StepEntry.COLUMN_VIDEO_URL)
    @SerializedName(StepEntry.COLUMN_VIDEO_URL)
    @Expose
    private String videoURL;


    @ColumnInfo(name = StepEntry.COLUMN_THUMBNAIL_URL)
    @SerializedName(StepEntry.COLUMN_THUMBNAIL_URL)
    @Expose
    private String thumbnailURL;

    @ColumnInfo(index = true, name = StepEntry.COLUMN_RECIPE_ID)
    private long recipeId;

    public Step() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(long recipeId) {
        this.recipeId = recipeId;
    }
}
