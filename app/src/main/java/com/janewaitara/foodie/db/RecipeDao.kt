package com.janewaitara.foodie.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.janewaitara.foodie.model.data.FavoriteRecipe
import com.janewaitara.foodie.model.data.Recipe

@Dao
interface RecipeDao {
    /**
     *Room has coroutines support, allowing your queries to be
     *annotated with the suspend modifier and then called
     *from a coroutine or from another suspension function.
     * */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllRandomRecipe(recipes: List<Recipe>)

    @Query("SELECT * FROM recipe_table ORDER BY `recipe title` ASC")
    fun getAllRandomRecipes(): LiveData<List<Recipe>>

    @Query("SELECT * FROM recipe_table where id =:recipeId" )
    suspend fun getRecipeById(recipeId: Int): Recipe

    @Query("DELETE FROM recipe_table")
    suspend fun clearRecipes()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavRecipe(favoriteRecipe: FavoriteRecipe)

    @Query("SELECT * FROM favorite_recipes_table ORDER BY `recipe title` ASC")
    fun getFavoriteRecipes(): LiveData<List<FavoriteRecipe>>

    @Query("SELECT * FROM favorite_recipes_table where id =:favRecipeId")
    suspend fun getFavoriteById(favRecipeId: Int): FavoriteRecipe

    @Delete
    suspend fun clearFavorites(vararg favoriteRecipe: FavoriteRecipe)

}