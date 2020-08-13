package com.janewaitara.foodie.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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
    fun getRecipeById(recipeId: Int): Recipe

    @Query("DELETE FROM recipe_table")
    suspend fun clearRecipes()
}