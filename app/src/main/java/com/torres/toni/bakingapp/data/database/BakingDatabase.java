package com.torres.toni.bakingapp.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.torres.toni.bakingapp.pojo.Ingredient;
import com.torres.toni.bakingapp.pojo.Recipe;
import com.torres.toni.bakingapp.pojo.Step;

import static com.torres.toni.bakingapp.data.BakingContract.DATABASE_NAME;

@Database(entities = {Recipe.class, Step.class, Ingredient.class}, version = 3, exportSchema = false)
public abstract class BakingDatabase extends RoomDatabase {

    private static final String LOG_TAG = BakingDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static BakingDatabase sInstance;

    public static BakingDatabase getInstance(Context context){
        if (sInstance == null){
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance.");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        BakingDatabase.class, DATABASE_NAME)
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance.");
        return sInstance;
    }

    public abstract RecipeDao getDao();

}
