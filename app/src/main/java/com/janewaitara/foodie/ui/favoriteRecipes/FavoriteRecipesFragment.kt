package com.janewaitara.foodie.ui.favoriteRecipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.janewaitara.foodie.R
import com.janewaitara.foodie.model.data.FavoriteRecipe
import com.janewaitara.foodie.utils.isVisible
import kotlinx.android.synthetic.main.fragment_favorite_recipes.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteRecipesFragment : Fragment() , FavoriteRecipeAdapter.FavRecipeClickListener {

    private val favRecipeViewModel: FavoriteRecipeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_recipes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpData()
    }

    private fun setUpData() {
        favRecipeViewModel.getFavoriteRecipe()

        favRecipeViewModel.getFavRecipeViewState().observe(viewLifecycleOwner, Observer{ favRecipeViewState ->

            favRecipeViewState?.let {favoriteRecipeViewState ->
                onDetailsViewStateChanged(favoriteRecipeViewState)
            }
        })
    }

    private fun onDetailsViewStateChanged(favoriteRecipeViewState: FavoriteRecipeViewState) {
        when(favoriteRecipeViewState){
            FavRecipeLoading -> showLoading(true)
            is FavRecipeSuccess -> {
               displayFavoriteRecipes(favoriteRecipeViewState.favRecipes)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {

        recipe_loading_anim.isVisible(isLoading)

    }

    private fun displayFavoriteRecipes(favRecipes: LiveData<List<FavoriteRecipe>>) {

        favRecipes.observe(viewLifecycleOwner,Observer{favoriteRecipeList->
            if (favoriteRecipeList != null){
                setUpRecyclerView(favoriteRecipeList)
            }else{
                showEmptyState()
            }
        })

    }

    private fun setUpRecyclerView(favoriteRecipeList: List<FavoriteRecipe>) {
        showLoading(false)
        favoriteRecipeRecyclerView.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        favoriteRecipeRecyclerView.adapter = FavoriteRecipeAdapter(favoriteRecipeList,this)

    }

    private fun showEmptyState() {
        showLoading(false)
        no_fav_recipe.isVisible(true)
    }

    override fun favRecipeItemClicked(favRecipeId: Int) {
        TODO("Not yet implemented")
    }

}