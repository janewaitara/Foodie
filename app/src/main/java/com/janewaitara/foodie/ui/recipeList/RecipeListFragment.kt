package com.janewaitara.foodie.ui.recipeList

import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.janewaitara.foodie.R
import com.janewaitara.foodie.networking.NetworkStatusChecker
import kotlinx.android.synthetic.main.fragment_recipe_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class RecipeListFragment : Fragment(), RecipeListAdapter.RecipeListClickListener {

    private val recipeViewModel: RecipeListViewModel by viewModel()

    private val networkStatusChecker by lazy {

        NetworkStatusChecker(activity?.getSystemService(ConnectivityManager::class.java))
    }

    private val recipeAdapter = RecipeListAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpWelcomeNoteColor()
        Log.d("Data Inserted 1","Success")
        setUpRecyclerView()
        Log.d("Data Inserted 2","Success")
        getRecipesInitially()
        setUpSynchronization()
        setUpData()


    }

    private fun setUpWelcomeNoteColor() {
        val note = getString(R.string.welcome_note)

        val spannableString = SpannableString(note)
        val homeColor = ForegroundColorSpan(resources.getColor(R.color.colorAccent))

        spannableString.setSpan(homeColor,29,33, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        welcome_recipe_note.text = spannableString
    }

    private fun setUpRecyclerView(){
        recipeListRecyclerView.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        recipeListRecyclerView.adapter = recipeAdapter
        //recipeViewModel.getAllRandomRecipes()
    }

    /**
     * Get recipes from api and add into the database when app is started*/
    private fun getRecipesInitially(){
        networkStatusChecker.performIfConnectedToInternet {
            Log.d("Data Inserted 3", "randomRecipes.data.toString()")
            recipeViewModel.getRecipes()
        }
    }

    /**
     * Synchronize data*/
    private fun setUpSynchronization(){
        networkStatusChecker.performIfConnectedToInternet {
            recipeViewModel.setUpSynchronization()
        }
    }

    private fun setUpData(){
        recipeViewModel.getAllRandomRecipes.observe(viewLifecycleOwner, Observer { recipes->

            Log.d("Data Test Observed 1", recipeViewModel.getAllRandomRecipes.value?.size.toString())

            recipes?.let { recipeAdapter.setRecipes(it) }
        })
    }


    override fun recipeItemClicked(view: View, recipeId: Int) {
       showDetailsFragment(view, recipeId)
    }

    /**
     * show Details Activity with a shared fragment*/
    private fun showDetailsFragment(view: View, recipeId: Int) {
        TODO("Not yet implemented")
    }

}