package com.example.appto.utils

class FiltersUtil {
    companion object {
        fun mapCategory(): HashMap<String, String> {
            val hash = HashMap<String, String>()
            hash["Categoría A"] = "a"
            hash["Categoría B"] = "b"
            hash["Categoría C"] = "c"
            return hash
        }
    }
}