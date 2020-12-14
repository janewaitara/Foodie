package com.janewaitara.foodie.ui.favoriteRecipes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.janewaitara.foodie.R
import com.janewaitara.foodie.RecipeApplication
import com.janewaitara.foodie.model.data.FavoriteRecipe
import com.janewaitara.foodie.ui.recipeList.RecipeListAdapter
import com.squareup.picasso.Picasso

class FavoriteRecipeAdapter (var favRecipeList: List<FavoriteRecipe>, private val clickListener: FavRecipeClickListener): RecyclerView.Adapter<FavoriteRecipeAdapter.FavoriteRecipeViewHolder>(){

    class FavoriteRecipeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var favRecipeTitle = itemView.findViewById(R.id.fav_recipe_title) as TextView
        var favRecipeImage = itemView.findViewById(R.id.fav_recipe_image) as ImageView
        var favRecipeServings = itemView.findViewById(R.id.fav_recipe_servings) as TextView
        var favRecipeReadyInMinutes = itemView.findViewById(R.id.fav_recipe_cooking_time) as TextView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteRecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favorite_recipe_list,parent,false)
        return FavoriteRecipeViewHolder(view)
    }

    override fun getItemCount() = favRecipeList.size

    override fun onBindViewHolder(holder: FavoriteRecipeViewHolder, position: Int) {

        Picasso.get()
            .load(favRecipeList[position].image)
            .fit()
            .into(holder.favRecipeImage)

        holder.favRecipeTitle.text = favRecipeList[position].title
        holder.favRecipeReadyInMinutes.text = favRecipeList[position].readyInMinutes.toString()

        if (favRecipeList[position].servings == 1) {
            holder.favRecipeServings.text =
                RecipeApplication.getResources().getString(R.string.serving, favRecipeList[position].servings.toString())
        }else{
            RecipeApplication.getResources().getString(R.string.servings, favRecipeList[position].servings.toString())
        }

        holder.itemView.setOnClickListener {
            clickListener.favRecipeItemClicked(favRecipeList[position].id)
        }
    }

    interface FavRecipeClickListener{
        fun favRecipeItemClicked(favRecipeId: Int)
    }
}