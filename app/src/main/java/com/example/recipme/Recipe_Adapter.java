package com.example.recipme;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Recipe_Adapter extends RecyclerView.Adapter<Recipe_Adapter.RecipeViewHolder> {

    private Context myContext;
    private List<Recipe> recipeList;
    private OnRecipeListener mOnRecipeListener;

    public Recipe_Adapter(Context myContext, List<Recipe> recipeList, OnRecipeListener mOnRecipeListener) {
        this.myContext = myContext;
        this.recipeList = recipeList;
        this.mOnRecipeListener = mOnRecipeListener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(myContext);
        View view = inflater.inflate(R.layout.layout_main_recipes, null);
        RecipeViewHolder holder = new RecipeViewHolder(view, mOnRecipeListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder recipeViewHolder, int position) {
        Recipe recipe = recipeList.get(position);
        recipeViewHolder.recycler_recipe_title.setText(recipe.getTitle());
        recipeViewHolder.recycler_recipe_desc.setText(recipe.getDescription());
        recipeViewHolder.recycler_recipe_preptime.setText(recipe.getPrepTime());

        if(recipe.imagePath1 != null) {
            Picasso.get().load(recipe.getImagePath1()).into(recipeViewHolder.imagePreview);
        }

        else if(recipe.imagePath1 == null){
            if(recipe.imagePath2 != null) {
                Picasso.get().load(recipe.getImagePath2()).into(recipeViewHolder.imagePreview);
            }
            else if(recipe.imagePath3 != null) {
                Picasso.get().load(recipe.getImagePath3()).into(recipeViewHolder.imagePreview);
            }
        }//else


    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imagePreview;
        TextView recycler_recipe_title;
        TextView recycler_recipe_desc;
        TextView recycler_recipe_preptime;
        OnRecipeListener onRecipeListener;

        public RecipeViewHolder(@NonNull View itemView, OnRecipeListener onRecipeListener) {
            super(itemView);

            imagePreview = itemView.findViewById(R.id.tmp_image);
            recycler_recipe_title = itemView.findViewById(R.id.recycle_name);
            recycler_recipe_desc = itemView.findViewById(R.id.recycle_desc);
            recycler_recipe_preptime = itemView.findViewById(R.id.recycle_preptime);
            this.onRecipeListener = onRecipeListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onRecipeListener.onRecipeClick(getAdapterPosition());
        }

    }//RecipeViewHolder

    public interface OnRecipeListener{
        void onRecipeClick(int position);
    }


}//Recipe_Adapter
