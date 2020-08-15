package com.janewaitara.foodie.ui.recipeList

import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.janewaitara.foodie.R
import com.janewaitara.foodie.RecipeApplication
import com.janewaitara.foodie.model.data.Recipe
import com.squareup.picasso.Picasso

class RecipeListAdapter(private val clickListener: RecipeListClickListener) : RecyclerView.Adapter<RecipeListAdapter.RecipeListViewHolder>() {

    /**Cached copy of recipes*/
    private var recipeList = emptyList<Recipe>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_list,parent,false)
        return RecipeListViewHolder(view)
    }

    override fun getItemCount() = recipeList.size

    override fun onBindViewHolder(holder: RecipeListViewHolder, position: Int) {

        Picasso.get()
            .load(recipeList[position].image)
            .fit()
            .centerCrop()
            .into(holder.recipeImage)

        holder.recipeName.text = recipeList[position].title

        if (recipeList[position].servings == 1){
            holder.recipeServings.text = RecipeApplication.getResources().getString(R.string.serving, recipeList[position].servings.toString())
        }else{
            holder.recipeServings.text = RecipeApplication.getResources().getString(R.string.servings, recipeList[position].servings.toString())
        }


        holder.itemView.setOnClickListener{ view->
            clickListener.recipeItemClicked(view,recipeList[position].id)
        }

    }

    internal fun setRecipes(recipes: List<Recipe>)  {
        this.recipeList = recipes
        Log.d("Data in RecyclerView" , recipeList.toString())
        notifyDataSetChanged()
    }

    class RecipeListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var recipeImage = itemView.findViewById (R.id.recipe_image_card) as ImageView
        var recipeName = itemView.findViewById (R.id.recipe_name) as TextView
        var recipeServings = itemView.findViewById (R.id.recipe_servings) as TextView

    }

    /**notifies all other objects whenever a View is clicked*/
    interface RecipeListClickListener{
        //pass in the view for shared animation
        fun recipeItemClicked(view: View, recipeId: Int)
    }
}