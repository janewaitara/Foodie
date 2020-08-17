package com.janewaitara.foodie.ui.recipeDetail

import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.janewaitara.foodie.R
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.janewaitara.foodie.model.data.Equipment
import com.janewaitara.foodie.model.data.Recipe
import com.janewaitara.foodie.model.response.SimilarRecipeResponse
import com.janewaitara.foodie.networking.NetworkStatusChecker
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_recipe_detail.*
import kotlinx.android.synthetic.main.fragment_recipe_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecipeDetailFragment : Fragment(), SimilarRecipeAdapter.SimilarRecipeClickListener {

    private val recipeDetailViewModel: RecipeDetailsViewModel by viewModel()

    private val networkStatusChecker by lazy {
        NetworkStatusChecker(activity?.getSystemService(ConnectivityManager::class.java))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_detail, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {

            val recipeIdArgs = RecipeDetailFragmentArgs.fromBundle(it)

            recipeDetailViewModel.getRecipeById(recipeIdArgs.recipeId)
            setUpData()
            setUpSimilarRecipe(recipeIdArgs.recipeId)

            nutrition.setOnClickListener {
                networkStatusChecker.performSearchIfConnectedToInternet(::showToast) {
                    showNutritionFragment(recipeIdArgs.recipeId)
                }
            }

        }
    }

    private fun showToast(){
        Toast.makeText(activity, "Connect to the Internet", Toast.LENGTH_LONG)
    }

    private fun showNutritionFragment(recipeId: Int) {
        view?.let {
            val action = RecipeDetailFragmentDirections.actionRecipeDetailFragmentToRecipeNutritionWidget(recipeId)

            it.findNavController().navigate(action)
        }
    }

    private fun setUpRecyclerViews(recipe: Recipe) {
        ingredientRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
        ingredientRecyclerView.adapter = IngredientsAdapter(recipe.extendedIngredients)

        Log.d("Equipments", "Setting up Equipment RecyclerView")
        equipmentsRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        Log.d("Equipments 1", "Setting up Equipment RecyclerView")
        var equipmentList = mutableListOf<Equipment>()
        recipe.analyzedInstructions.forEach{steps ->
            Log.d("Equipments 2", "Setting up Equipment RecyclerView")
            steps.steps.forEach{step->

                step.equipment.forEach {
                    equipmentList.add( it )
                }
            }
        }
        Log.d("Equipments", equipmentList.toString())

        equipmentsRecyclerView.adapter = EquipmentAdapter(equipmentLists = equipmentList.toSet().toList())

    }

    private fun setUpData() {
        recipeDetailViewModel.getDetailsViewState().observe(viewLifecycleOwner, Observer{ viewState ->
            viewState?.let {detailViewState ->
                onDetailsViewStateChanged(detailViewState)
            }
        })
    }

    private fun onDetailsViewStateChanged(detailViewState: DetailsViewState) {
        when(detailViewState){
            //DetailsLoading -> showLoading()
            is DetailsSuccess -> {
                if (detailViewState.recipe != null)
                    displayRecipeDetails(detailViewState.recipe)
                else showEmptyState()
            }
        }
    }

    private fun displayRecipeDetails(recipe: Recipe) {
        hideLoading()
        hideEmptyState()
        recipe_title.text = recipe.title
        servings.text = recipe.servings.toString()
        Picasso.get().load(recipe.image).fit().into(recipe_image_card)
        ready_in_minutes.text = recipe.readyInMinutes.toString()
        health_score_value.text = recipe.healthScore.toString()

        setUpRecyclerViews(recipe)

        val directions = recipe.analyzedInstructions

        val directionsBuilder = StringBuilder()
        directions?.forEach { steps ->
            steps.steps.forEach{step ->
                directionsBuilder.append(step.number).append(". ").append(step.step).append("\n")
            }

        }
        recipe_directions.text = directionsBuilder

    }

    private fun setUpSimilarRecipe(recipeId: Int){

        networkStatusChecker.performSearchIfConnectedToInternet(::displayNoInternetMessage) {
            recipeDetailViewModel.getSimilarRecipe(recipeId)

            recipeDetailViewModel.getSimilarRecipesLiveData().observe(viewLifecycleOwner, Observer{ similarRecipeResponse->

                similarRecipeResponse?.let { similarRecipeList ->

                    setUpSimilarRecipeRecyclerView(similarRecipeList)
                }
            })
        }
    }

    private fun setUpSimilarRecipeRecyclerView(similarRecipeList: List<SimilarRecipeResponse>) {

        similarRecipesRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        val adapter = SimilarRecipeAdapter(this)
        similarRecipesRecyclerView.adapter = adapter

        adapter.setSimilarRecipes(similarRecipeList)

    }

    private fun addToFavorites( view: View){

    }

    private fun displayNoInternetMessage() {
        similarRecipesRecyclerView.visibility = View.GONE

    }

    private fun showLoading() {
        TODO("Not yet implemented")
    }

    private fun hideLoading() {
        // Hide progress here
    }

    private fun showEmptyState() {
        // Show empty state here
    }

    private fun hideEmptyState() {
        // Hide empty state here
    }

    override fun similarRecipeItemClicked(similarRecipeId: Int) {

    }
}