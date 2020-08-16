package com.janewaitara.foodie.ui.recipeDetail

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.janewaitara.foodie.R
import com.janewaitara.foodie.RecipeApplication
import com.janewaitara.foodie.model.response.SimilarRecipeResponse
import com.squareup.picasso.Picasso

class SimilarRecipeAdapter(private val similarRecipeClickListener: SimilarRecipeClickListener) : RecyclerView.Adapter<SimilarRecipeAdapter.SimilarRecipeViewHolder>(){

    private var similarRecipeList = emptyList<SimilarRecipeResponse>() //Cached copy of recipes

    interface SimilarRecipeClickListener {
        fun similarRecipeItemClicked(similarRecipeId: Int)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarRecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.similar_recipes,parent,false)
        return SimilarRecipeViewHolder(
            view
        )
    }

    override fun getItemCount() = similarRecipeList.size

    override fun onBindViewHolder(holder: SimilarRecipeViewHolder, position: Int) {

        val urls =  "https://spoonacular.com/recipeImages/525211-480x360.jpg"

        val id = similarRecipeList[position].id
        val imageType = similarRecipeList[position].imageType
        Picasso.get()
            .load("https://spoonacular.com/recipeImages/${id}-480x360.${imageType}")
            .fit()
            .centerCrop()
            .into(holder.similarRecipeImage)

        Log.d("Similar Recipe Image","https://spoonacular.com/recipeImages/{$id}-480x360.{$imageType}" )

        holder.similarRecipeTitle.text = similarRecipeList[position].title

        holder.similarRecipeServings.text = RecipeApplication.getResources().getString(R.string.servings, similarRecipeList[position].servings.toString())

        holder.itemView.setOnClickListener{
            similarRecipeClickListener.similarRecipeItemClicked(similarRecipeList[position].id)
        }

    }

    internal fun setSimilarRecipes(similarRecipes: List<SimilarRecipeResponse>)  {
        this.similarRecipeList = similarRecipes
        notifyDataSetChanged()
    }

    class SimilarRecipeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val similarRecipeImage = itemView.findViewById(R.id.similar_recipe_image) as ImageView
        val similarRecipeTitle = itemView.findViewById(R.id.similar_recipe_name) as TextView
        val similarRecipeServings = itemView.findViewById(R.id.similar_recipe_servings) as TextView
    }

}

