package com.janewaitara.foodie.ui.recipeDetail.searchRecipeDetails

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
import com.janewaitara.foodie.model.data.Equipment
import com.janewaitara.foodie.model.response.RecipeInformationResponse
import com.janewaitara.foodie.model.response.SimilarRecipeResponse
import com.janewaitara.foodie.networking.NetworkStatusChecker
import com.janewaitara.foodie.ui.recipeDetail.EquipmentAdapter
import com.janewaitara.foodie.ui.recipeDetail.IngredientsAdapter
import com.janewaitara.foodie.ui.recipeDetail.SimilarRecipeAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_search_recipe_detail.*
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
                    displayRecipeDetails(recipeInformationResponse)
                }
            })
        }
    }

    private fun displayRecipeDetails(recipeInformationResponse: RecipeInformationResponse) {
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


        val directions = recipeInformationResponse.analyzedInstructions

        val directionsBuilder = StringBuilder()
        directions?.forEach { steps ->
            steps.steps.forEach{step ->
                directionsBuilder.append(step.number).append(". ").append(step.step).append("\n")
            }

        }
        recipe_directions.text = directionsBuilder
    }

    private fun setUpRecyclerViews(recipeInformationResponse: RecipeInformationResponse) {

        ingredientRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
        ingredientRecyclerView.adapter = IngredientsAdapter(recipeInformationResponse.extendedIngredients)

        Log.d("1 Equipments", "Setting up Equipment RecyclerView")
        equipmentsRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        Log.d("2 Equipments 1", "Setting up Equipment RecyclerView")
        var equipmentList = mutableListOf<Equipment>()
        recipeInformationResponse.analyzedInstructions.forEach{steps ->
            Log.d("3 Equipments 2", "Setting up Equipment RecyclerView")
            steps.steps.forEach{step->

                step.equipment.forEach {
                    equipmentList.add( it )
                }
            }
        }
        Log.d("4 Equipments", equipmentList.toString())

        equipmentsRecyclerView.adapter = EquipmentAdapter(equipmentLists = equipmentList.toSet().toList())

    }

    override fun similarRecipeItemClicked(similarRecipeId: Int) {

    }


}