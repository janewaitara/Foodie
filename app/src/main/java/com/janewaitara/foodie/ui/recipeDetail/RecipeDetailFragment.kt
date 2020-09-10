package com.janewaitara.foodie.ui.recipeDetail

import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.Gravity
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
import com.janewaitara.foodie.utils.isVisible
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_recipe_detail.*
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
        Toast.makeText(activity, "Connect to the Internet", Toast.LENGTH_LONG).show()
    }

    private fun showNutritionFragment(recipeId: Int) {
        view?.let {
            val action = RecipeDetailFragmentDirections.actionRecipeDetailFragmentToRecipeNutritionWidget(recipeId)

            it.findNavController().navigate(action)
        }
    }

    private fun setUpRecyclerViews(recipe: Recipe) {


            ingredientRecyclerView.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            ingredientRecyclerView.adapter = IngredientsAdapter(recipe.extendedIngredients)


        if (!recipe.extendedIngredients.isNullOrEmpty()) {
            Log.d("Equipments", "Setting up Equipment RecyclerView")
            equipmentsRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

            Log.d("Equipments 1", "Setting up Equipment RecyclerView")
            var equipmentList = mutableListOf<Equipment>()
            recipe.analyzedInstructions.forEach { steps ->
                Log.d("Equipments 2", "Setting up Equipment RecyclerView")
                steps.steps.forEach { step ->

                    step.equipment.forEach {
                        equipmentList.add(it)
                    }
                }
            }
            Log.d("Equipments", equipmentList.toString())

            equipmentsRecyclerView.adapter =
                EquipmentAdapter(equipmentLists = equipmentList.toSet().toList())
        }else{
            hideEquipments()

        }

    }

    private fun hideEquipments() {
        equipments.isVisible(false)
        equipmentsRecyclerView.isVisible(false)
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
            DetailsLoading -> showLoading(true)
            is DetailsSuccess -> {
                if (detailViewState.recipe != null)
                    displayRecipeDetails(detailViewState.recipe)
                else showEmptyState()
            }
        }
    }

    private fun displayRecipeDetails(recipe: Recipe) {
       showLoading(false)
        hideEmptyState()
        recipe_title.text = recipe.title

        if (recipe.servings == 1){
            servings.text = resources.getString(R.string.serving, recipe.servings.toString())
        }else{
            servings.text = resources.getString(R.string.serving, recipe.servings.toString())
        }


        Picasso.get().load(recipe.image).fit().into(recipe_image_card)
        ready_in_minutes.text = recipe.readyInMinutes.toString()
        health_score_value.text = recipe.healthScore.toString()

        setUpRecyclerViews(recipe)

        if (!recipe.analyzedInstructions.isNullOrEmpty()){

            val directions = recipe.analyzedInstructions

            val directionsBuilder = StringBuilder()
            directions.forEach { steps ->
                steps.steps.forEach { step ->
                    directionsBuilder.append(step.number).append(". ").append(step.step)
                        .append("\n")
                }

            }
            recipe_directions.text = directionsBuilder
        }else {

            recipe_directions.text = getString(R.string.directions_unavailable)
            recipe_directions.gravity = Gravity.CENTER

        }


    }

    private fun setUpSimilarRecipe(recipeId: Int){

        networkStatusChecker.performSearchIfConnectedToInternet(::displayNoInternetMessage) {

            hideNoInternetMessage()

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
        similar_recipes_unavailable.isVisible(true)
    }

    private fun hideNoInternetMessage(){
        similar_recipes_unavailable.isVisible(false)
    }

    private fun showLoading( status: Boolean) {
        recipe_loading_anim.isVisible(status)

    }

    private fun showEmptyState() {
        // Show empty state here
    }

    private fun hideEmptyState() {
        // Hide empty state here
    }

    override fun similarRecipeItemClicked(similarRecipeId: Int) {
        showRecipeDetails(similarRecipeId)
    }

    private fun showRecipeDetails(similarRecipeId: Int) {
        view?.let {
            val action = RecipeDetailFragmentDirections.actionRecipeDetailFragmentToSearchRecipeDetailFragment(similarRecipeId)

            it.findNavController().navigate(action)
        }
    }
}