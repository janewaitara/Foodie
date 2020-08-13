package com.janewaitara.foodie.db

import com.janewaitara.foodie.model.data.Steps
import androidx.room.TypeConverter
import com.google.gson.Gson

class StepsConverter {
    @TypeConverter
    fun fromSteps(stepsList: List<Steps>?): String? =
        Gson().toJson(stepsList)

    @TypeConverter
    fun toSteps(stepsListString: String): List<Steps> =
        Gson().fromJson(stepsListString, Array<Steps>::class.java).toList()

}