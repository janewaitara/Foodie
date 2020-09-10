package com.janewaitara.foodie.ui.searchRecipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.janewaitara.foodie.R
import com.janewaitara.foodie.model.data.SearchedRecipe
import com.squareup.picasso.Picasso


class SearchRecipeAdapter(private val clickListener: SearchRecipeListClickListener) : RecyclerView.Adapter<SearchRecipeAdapter.SearchRecipeViewHolder>() {

    /**Cached copy of recipes*/
    private var recipeList = emptyList<SearchedRecipe>()

    /**notifies all other objects whenever a View is clicked*/
    interface SearchRecipeListClickListener{
        fun searchRecipeItemClicked(searchedRecipe: SearchedRecipe)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchRecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_list,parent,false)
        return SearchRecipeViewHolder(view)
    }

    override fun getItemCount() = recipeList.size

    override fun onBindViewHolder(holder: SearchRecipeViewHolder, position: Int) {

        Picasso.get()
            .load(recipeList[position].image)
            .fit()
            .centerCrop()
            .into(holder.recipeImage)

        holder.recipeName.text = recipeList[position].title

        holder.recipeServings.visibility = View.INVISIBLE

        holder.itemView.setOnClickListener {
            clickListener.searchRecipeItemClicked(recipeList[position])
        }

    }

    internal fun setSearchRecipes(recipes: List<SearchedRecipe>)  {
        this.recipeList = recipes
        notifyDataSetChanged()
    }
    class SearchRecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var recipeName = itemView.findViewById (R.id.recipe_title) as TextView
        var recipeImage = itemView.findViewById (R.id.recipe_image_card) as ImageView
        var recipeServings = itemView.findViewById (R.id.recipe_servings) as TextView

    }
}