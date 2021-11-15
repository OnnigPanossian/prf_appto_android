package com.example.appto.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Filters(
    var category: List<String>? = null,
) : Parcelable {

    fun mapCategory(): HashMap<String, String> {
        val hash = HashMap<String, String>()
        hash["Categoría A"] = "a"
        hash["Categoría B"] = "b"
        hash["Categoría C"] = "c"
        return hash
    }
}