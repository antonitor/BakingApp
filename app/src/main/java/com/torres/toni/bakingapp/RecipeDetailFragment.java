package com.torres.toni.bakingapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.torres.toni.bakingapp.pojo.Ingredient;
import com.torres.toni.bakingapp.pojo.Recipe;
import com.torres.toni.bakingapp.pojo.Step;
import com.torres.toni.bakingapp.viewmodel.RecipeDetailViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailFragment extends Fragment implements StepListAdapter.StepListClickHandler{

    @BindView(R.id.steps_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.ingedients)
    TextView ingredientsTextView;
    StepListAdapter stepListAdapter;
    RecipeDetailViewModel mViewModel;

    public RecipeDetailFragment(){
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        ButterKnife.bind(this, rootView);
        stepListAdapter = new StepListAdapter(null, getContext(), this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(stepListAdapter);
        mRecyclerView.setFocusable(false);
        ingredientsTextView.requestFocus();
        setUpViewModel();
        return rootView;
    }

    private void setUpViewModel() {
        mViewModel = ViewModelProviders.of(getActivity()).get(RecipeDetailViewModel.class);
        final Observer<Recipe> recipeObserver = new Observer<Recipe>() {
            @Override
            public void onChanged(@Nullable Recipe recipe) {
                getActivity().setTitle(recipe.getName());
            }
        };
        final Observer<List<Ingredient>> ingredientListObserver = new Observer<List<Ingredient>>() {
            @Override
            public void onChanged(@Nullable List<Ingredient> ingredients) {
                ingredientsTextView.setText("");
                for(Ingredient ingredient : ingredients) {
                    ingredientsTextView.append("· "+ingredient.getIngredient() + " ");
                    ingredientsTextView.append("( " +((int) ingredient.getQuantity()) + " ");
                    ingredientsTextView.append(ingredient.getMeasure() + " )");
                    ingredientsTextView.append("\n");
                }
            }
        };
        final Observer<List<Step>> stepListObserver = new Observer<List<Step>>() {
            @Override
            public void onChanged(@Nullable List<Step> steps) {
                stepListAdapter.setSteps(steps);
            }
        };
        mViewModel.getRecipe().observe(this, recipeObserver);
        mViewModel.getIngredientList().observe(this, ingredientListObserver);
        mViewModel.getStepList().observe(this, stepListObserver);
    }

    @Override
    public void onStepItemClicked(int stepId) {
        mViewModel.setStepSelected(stepId);
        if (mViewModel.istwoPane()) {
            StepFragment stepFragment = new StepFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.step_detail_two_pane, stepFragment)
                    .commit();
        } else {
            StepFragment stepFragment = new StepFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, stepFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
}
