package com.janewaitara.foodie.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Steps (val steps: List<Step>):Parcelable

@Parcelize
class Step (
    val number: Int,
    val step: String,
    val equipment: List<Equipment>
):Parcelable

@Parcelize
class Equipment(
    val id: Int,
    val name: String,
    val image: String
):Parcelable
