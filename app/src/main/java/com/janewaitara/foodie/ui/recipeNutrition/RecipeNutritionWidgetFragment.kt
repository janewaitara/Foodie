package com.janewaitara.foodie.ui.recipeNutrition

import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.janewaitara.foodie.R
import com.janewaitara.foodie.model.response.NutritionWidgetResponse
import com.janewaitara.foodie.networking.NetworkStatusChecker
import kotlinx.android.synthetic.main.fragment_recipe_nutrition_widget.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class RecipeNutritionWidget : Fragment() {


    private val nutritionViewModel: RecipeNutritionViewModel by viewModel()
    private val networkStatusChecker by lazy {

        NetworkStatusChecker(activity?.getSystemService(ConnectivityManager::class.java))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_nutrition_widget, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {

            val recipeId = RecipeNutritionWidgetArgs.fromBundle(it).recipeId

            getNutritionValues(recipeId)
        }


    }

    private fun getNutritionValues(recipeId: Int) {
        networkStatusChecker.performIfConnectedToInternet {

            nutritionViewModel.getRecipeNutrition(recipeId)

            nutritionViewModel.getRecipeNutritionLiveData().observe(viewLifecycleOwner, Observer{nutritionWidgetResponse ->
                Log.d("Nutrition Widget"," it.toString()")
                nutritionWidgetResponse?.let{
                    Log.d("Nutrition Widget", it.toString())
                    setUpView(it)
                    setUpRecyclerViews(it)
                }
            })
        }
    }

    private fun setUpView(nutrientWidget: NutritionWidgetResponse) {
        calories_value.text = nutrientWidget.calories
        carbs_value.text = nutrientWidget.carbs
        fats_value.text = nutrientWidget.fat
        protein_value.text = nutrientWidget.protein
    }

    private fun setUpRecyclerViews(nutrientWidget: NutritionWidgetResponse) {
        val nutrientsAdapter = NutrientAdapter()
        badNutrientsListRecyclerView.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)

        badNutrientsListRecyclerView.adapter =nutrientsAdapter
        nutrientsAdapter.setNutrients(nutrientWidget.bad)

        goodNutrientsListRecyclerView.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
        badNutrientsListRecyclerView.adapter = nutrientsAdapter
        nutrientsAdapter.setNutrients(nutrientWidget.bad)


    }

}