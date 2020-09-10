package com.janewaitara.foodie.ui.recipeDetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.janewaitara.foodie.R
import com.janewaitara.foodie.model.data.Ingredient
import com.squareup.picasso.Picasso

class IngredientsAdapter(var ingredientLists: List<Ingredient>): RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ingredients_list,parent,false)
        return IngredientsViewHolder(view)

    }

    override fun getItemCount() = ingredientLists.size

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        Picasso.get()
            .load("https://spoonacular.com/cdn/ingredients_100x100/${ingredientLists[position].image}")
            .fit()

            .into(holder.ingredientImage)

        holder.ingredientName.text = ingredientLists[position].originalString
    }

    class IngredientsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ingredientName = itemView.findViewById (R.id.ingredient_name) as TextView
        var ingredientImage = itemView.findViewById (R.id.ingredient_image) as ImageView
    }
}