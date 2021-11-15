package com.example.appto.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Filters(
    var category: List<String>? = null,
) : Parcelable