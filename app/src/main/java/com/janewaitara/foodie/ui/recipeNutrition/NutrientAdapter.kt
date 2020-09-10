package com.janewaitara.foodie.ui.recipeNutrition

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.janewaitara.foodie.R
import com.janewaitara.foodie.model.data.Nutrient

class NutrientAdapter : RecyclerView.Adapter<NutrientAdapter.NutrientViewHolder>(){

    /**Cached copy of recipes*/
    private var nutrientList = emptyList<Nutrient>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NutrientViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.nutrients_list, parent, false)
        return NutrientViewHolder(view)
    }

    override fun getItemCount() = nutrientList.size

    override fun onBindViewHolder(holder: NutrientViewHolder, position: Int) {
        holder.nutrientName.text = nutrientList[position].title
        holder.nutrientAmount.text = nutrientList[position].amount
        holder.nutrientPerCent.text = nutrientList[position].percentOfDailyNeeds.toString()
    }

    internal fun setNutrients(nutrients: List<Nutrient>){
        this.nutrientList = nutrients
        notifyDataSetChanged()
    }

    class NutrientViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val nutrientName = itemView.findViewById(R.id.nutrient_name) as TextView
        val nutrientAmount = itemView.findViewById(R.id.nutrient_amount)  as TextView
        val nutrientPerCent = itemView.findViewById(R.id.nutrient_percentOfDailyNeeds) as TextView

    }
}