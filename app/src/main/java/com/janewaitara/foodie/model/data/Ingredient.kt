package com.janewaitara.foodie.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Ingredient (
    val id: Int,
    val image: String,
    val originalString: String) : Parcelable
