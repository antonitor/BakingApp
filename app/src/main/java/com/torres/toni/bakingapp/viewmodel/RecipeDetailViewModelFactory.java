package com.torres.toni.bakingapp.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import com.torres.toni.bakingapp.data.database.BakingDatabase;

public class RecipeDetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private BakingDatabase database;
    private int id;

    public RecipeDetailViewModelFactory(BakingDatabase database, int id) {
        this.database = database;
        this.id = id;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new RecipeDetailViewModel(database, id);
    }
}
