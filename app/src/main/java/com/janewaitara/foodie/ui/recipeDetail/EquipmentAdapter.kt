package com.janewaitara.foodie.ui.recipeDetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.janewaitara.foodie.R
import com.janewaitara.foodie.model.data.Equipment
import com.janewaitara.foodie.model.data.Ingredient
import com.squareup.picasso.Picasso

class EquipmentAdapter(var equipmentLists: List<Equipment>):RecyclerView.Adapter<EquipmentAdapter.EquipmentViewHolder>() {

    class EquipmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var equipmentName = itemView.findViewById (R.id.equipment_name) as TextView
        var equipmentImage = itemView.findViewById (R.id.equipment_image) as ImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.equipments_list,parent,false)
        return EquipmentViewHolder(view)
    }

    override fun getItemCount() = equipmentLists.size

    override fun onBindViewHolder(holder: EquipmentViewHolder, position: Int) {
        Picasso.get()
            .load("https://spoonacular.com/cdn/equipment_100x100/${equipmentLists[position].image}")
            .fit()
            .centerCrop()
            .into(holder.equipmentImage)

        holder.equipmentName.text = equipmentLists[position].name
    }
}