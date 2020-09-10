package com.janewaitara.foodie.utils

import android.content.Context
import android.view.View
import android.widget.Toast

fun Context.toast(msg: String){
    Toast.makeText(this,msg,Toast.LENGTH_LONG).show()
}
fun View.isVisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}