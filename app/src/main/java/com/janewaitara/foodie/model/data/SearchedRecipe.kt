package com.janewaitara.foodie.model.data

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class SearchedRecipe(
    val id: Int,
    val title: String,
    val image: String
): Parcelable