package com.torres.toni.bakingapp.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.torres.toni.bakingapp.data.BakingRepository;

public class RecipesListViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private BakingRepository bakingRepository;

    public RecipesListViewModelFactory(BakingRepository bakingRepository) {
        this.bakingRepository = bakingRepository;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new RecipesListViewModel(bakingRepository);
    }
}