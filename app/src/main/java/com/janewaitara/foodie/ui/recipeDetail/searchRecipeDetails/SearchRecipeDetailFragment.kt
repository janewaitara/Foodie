package com.janewaitara.foodie.ui.recipeDetail.searchRecipeDetails

import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.janewaitara.foodie.R
import com.janewaitara.foodie.model.data.Equipment
import com.janewaitara.foodie.model.data.SearchedRecipe
import com.janewaitara.foodie.model.response.RecipeInformationResponse
import com.janewaitara.foodie.model.response.SimilarRecipeResponse
import com.janewaitara.foodie.model.results.Loading
import com.janewaitara.foodie.model.results.Result
import com.janewaitara.foodie.model.results.Success
import com.janewaitara.foodie.networking.NetworkStatusChecker
import com.janewaitara.foodie.ui.recipeDetail.EquipmentAdapter
import com.janewaitara.foodie.ui.recipeDetail.IngredientsAdapter
import com.janewaitara.foodie.ui.recipeDetail.SimilarRecipeAdapter
import com.janewaitara.foodie.utils.isVisible
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_search_recipe.*
import kotlinx.android.synthetic.main.fragment_search_recipe_detail.*
import kotlinx.android.synthetic.main.fragment_search_recipe_detail.equipmentsRecyclerView
import kotlinx.android.synthetic.main.fragment_search_recipe_detail.health_score_value
import kotlinx.android.synthetic.main.fragment_search_recipe_detail.ingredientRecyclerView
import kotlinx.android.synthetic.main.fragment_search_recipe_detail.ready_in_minutes
import kotlinx.android.synthetic.main.fragment_search_recipe_detail.recipe_directions
import kotlinx.android.synthetic.main.fragment_search_recipe_detail.recipe_image_card
import kotlinx.android.synthetic.main.fragment_search_recipe_detail.recipe_loading_anim
import kotlinx.android.synthetic.main.fragment_search_recipe_detail.recipe_title
import kotlinx.android.synthetic.main.fragment_search_recipe_detail.servings
import kotlinx.android.synthetic.main.fragment_search_recipe_detail.similarRecipesRecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchRecipeDetailFragment : Fragment() , SimilarRecipeAdapter.SimilarRecipeClickListener{

    private val searchRecipeDetailsViewModel : SearchRecipeDetailsViewModel by viewModel()

    private val networkStatusChecker by lazy {
        NetworkStatusChecker(activity?.getSystemService(ConnectivityManager::class.java))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_recipe_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let{

            val recipeId = SearchRecipeDetailFragmentArgs.fromBundle(it).recipeId

            getRecipeDetails(recipeId)

            setUpSimilarRecipe(recipeId)

        }
    }

    private fun setUpSimilarRecipe(recipeId: Int) {


        networkStatusChecker.performSearchIfConnectedToInternet(::displayNoInternetMessage) {
            searchRecipeDetailsViewModel.getSimilarRecipe(recipeId)

            searchRecipeDetailsViewModel.getSimilarRecipesLiveData().observe(viewLifecycleOwner, Observer{ similarRecipeResponse->

                similarRecipeResponse?.let { similarRecipeList ->

                    setUpSimilarRecipeRecyclerView(similarRecipeList)
                }
            })
        }
    }
    private fun displayNoInternetMessage() {
        similarRecipesRecyclerView.visibility = View.GONE

    }


    private fun setUpSimilarRecipeRecyclerView(similarRecipeList: List<SimilarRecipeResponse>) {

        similarRecipesRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        val adapter = SimilarRecipeAdapter(this)
        similarRecipesRecyclerView.adapter = adapter

        adapter.setSimilarRecipes(similarRecipeList)

    }

    private fun getRecipeDetails(recipeId: Int) {
        networkStatusChecker.performIfConnectedToInternet {
            searchRecipeDetailsViewModel.getRecipeDetailsFromApiUsingRecipeId(recipeId)

            searchRecipeDetailsViewModel.getRecipeDetailsLiveData().observe(viewLifecycleOwner, Observer{ recipeInformationResponse ->

                recipeInformationResponse?.let { recipeInformationResponse ->
                    handleState(recipeInformationResponse)

                }
            })
        }
    }

    private fun handleState(recipeInformationResponse: Result<RecipeInformationResponse>) {

            when(recipeInformationResponse){
                is Loading -> showLoading(true)
                is Success -> displayRecipeDetails(recipeInformationResponse.data)
            }

    }

    private fun showLoading(isLoading: Boolean) {
        recipe_loading_anim.isVisible(isLoading)
        details_container.isVisible(!isLoading)
    }


    private fun displayRecipeDetails(recipeInformationResponse: RecipeInformationResponse) {

        showLoading(false)

        recipe_title.text = recipeInformationResponse.title

        if (recipeInformationResponse.servings == 1) {
            servings.text = resources.getString(
                R.string.serving,
                recipeInformationResponse.servings.toString()
            )
        }else{
            servings.text = resources.getString(
                R.string.servings,
                recipeInformationResponse.servings.toString()
            )
        }

        Picasso.get().load(recipeInformationResponse.image).fit().into(recipe_image_card)
        ready_in_minutes.text = recipeInformationResponse.readyInMinutes.toString()
        health_score_value.text = recipeInformationResponse.healthScore.toString()

        setUpRecyclerViews(recipeInformationResponse)

        if (!recipeInformationResponse.analyzedInstructions.isNullOrEmpty()) {

            val directions = recipeInformationResponse.analyzedInstructions

            val directionsBuilder = StringBuilder()
            directions?.forEach { steps ->
                steps.steps.forEach { step ->
                    directionsBuilder.append(step.number).append(". ").append(step.step)
                        .append("\n")
                }

            }
            recipe_directions.text = directionsBuilder

        } else {

                recipe_directions.text = getString(R.string.directions_unavailable)
                recipe_directions.gravity = Gravity.CENTER

        }
    }

    private fun setUpRecyclerViews(recipeInformationResponse: RecipeInformationResponse) {

        ingredientRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
        ingredientRecyclerView.adapter = IngredientsAdapter(recipeInformationResponse.extendedIngredients)

        if (!recipeInformationResponse.extendedIngredients.isNullOrEmpty()) {
            equipmentsRecyclerView.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

            var equipmentList = mutableListOf<Equipment>()
            recipeInformationResponse.analyzedInstructions.forEach { steps ->
                steps.steps.forEach { step ->

                    step.equipment.forEach {
                        equipmentList.add(it)
                    }
                }
            }

            equipmentsRecyclerView.adapter =
                EquipmentAdapter(equipmentLists = equipmentList.toSet().toList())
        }else {
            hideEquipments()
        }
    }

    private fun hideEquipments() {
        equipments.isVisible(false)
        equipmentsRecyclerView.isVisible(false)
    }

    override fun similarRecipeItemClicked(similarRecipeId: Int) {
         showRecipeDetails(similarRecipeId)
    }

    private fun showRecipeDetails(similarRecipeId: Int) {
        view?.let {
            val action = SearchRecipeDetailFragmentDirections.actionSearchRecipeDetailFragmentSelf(similarRecipeId)

            it.findNavController().navigate(action)
        }
    }

}