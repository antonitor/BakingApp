package com.torres.toni.bakingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.torres.toni.bakingapp.pojo.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.BakingAdapterViewHolder> {

    private List<Recipe> mRecipesList;
    private final Context mContext;
    private final RecipeListClickHandler mClickHandler;

    public RecipeListAdapter(List<Recipe> mRecipesList, Context context, RecipeListClickHandler clickHandler) {
        this.mRecipesList = mRecipesList;
        this.mContext = context;
        this.mClickHandler = clickHandler;
    }

    public interface RecipeListClickHandler{
        void onRecipeItemClicked(int recipeId);
    }

    @NonNull
    @Override
    public BakingAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recipe_item, parent, false);
        return new BakingAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BakingAdapterViewHolder holder, int position) {
        Recipe recipe = mRecipesList.get(position);
        holder.recipeTitle.setText(recipe.getName());
        holder.servings.setText(String.valueOf(recipe.getServings()));
        holder.recipeId = recipe.getId();
    }

    @Override
    public int getItemCount() {
        if (mRecipesList == null) return 0;
        return mRecipesList.size();
    }

    class BakingAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.recipe_title) TextView recipeTitle;
        @BindView(R.id.servings) TextView servings;
        int recipeId;

        public BakingAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mClickHandler.onRecipeItemClicked(recipeId);
        }
    }

    public void setRecipes(List<Recipe> recipes) {
        mRecipesList = recipes;
        notifyDataSetChanged();
    }
}
