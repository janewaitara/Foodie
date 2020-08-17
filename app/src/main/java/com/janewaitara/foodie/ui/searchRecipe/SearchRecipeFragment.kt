package com.janewaitara.foodie.ui.searchRecipe

import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.janewaitara.foodie.R
import com.janewaitara.foodie.model.data.SearchedRecipe
import com.janewaitara.foodie.networking.NetworkStatusChecker
import kotlinx.android.synthetic.main.fragment_search_recipe.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchRecipeFragment : Fragment(), SearchRecipeAdapter.SearchRecipeListClickListener {

    private val searchRecipeViewModel: SearchRecipeViewModel by viewModel()

    private val networkStatusChecker by lazy {
        NetworkStatusChecker(activity?.getSystemService(ConnectivityManager::class.java))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_recipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val searchParameter = SearchRecipeFragmentArgs.fromBundle(it).searchParameter

            performRecipeSearch(searchParameter)

        }
    }

    private fun performRecipeSearch(searchParameter: String) {

        networkStatusChecker.performSearchIfConnectedToInternet(::displayNoInternetMessage){

            lifecycleScope.launch {

                searchRecipeViewModel.searchRecipeFromApiUsingSearchParameter(searchParameter)

                Log.d("Search Parameter", searchParameter)
                searchRecipeViewModel.getSearchedRecipeLiveData().observe(viewLifecycleOwner, Observer{ searchedRecipes ->
                searchedRecipes?.let {searchedRecipeList ->
                    setUpRecyclerView(searchedRecipeList)
                    Log.d("Searched Recipes", searchedRecipeList.toString())
                    }
                })

            }
        }

    }

    private fun setUpRecyclerView(searchedRecipeList: List<SearchedRecipe>) {

        searchRecipeRecyclerView.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        val adapter = SearchRecipeAdapter(this)
        searchRecipeRecyclerView.adapter = adapter
        adapter.setSearchRecipes(searchedRecipeList)
    }

    private fun displayNoInternetMessage() {

    }

    override fun searchRecipeItemClicked(searchedRecipe: SearchedRecipe) {
       showDetailsFragments(searchedRecipe)
    }

    private fun showDetailsFragments(searchedRecipe: SearchedRecipe) {

        view?.let {
            val action = SearchRecipeFragmentDirections.actionSearchRecipeFragmentToSearchRecipeDetailFragment(searchedRecipe.id)

            it.findNavController().navigate(action)
        }
    }

}