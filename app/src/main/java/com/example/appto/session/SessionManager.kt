package com.example.appto.session

import android.content.Context
import android.content.SharedPreferences
import com.example.appto.R

/**
 * Session manager to save and fetch data from SharedPreferences
 */
class SessionManager(context: Context) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "token"
        const val USER_EMAIL = "email"
        const val RENTAL_IN_PROGRESS = "inProgress"
        const val USER_NAME = "name"
        const val USER_IMAGE = "image"
    }

    /**
     * Function to save auth token
     */
    fun saveAuthToken(token: String?) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    /**
     * Function to fetch auth token
     */
    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    /**
     * Function to save user email
     */
    fun saveUserEmail(email: String?) {
        val editor = prefs.edit()
        editor.putString(USER_EMAIL, email)
        editor.apply()
    }

    /**
     * Function to fetch user email
     */
    fun fetchUserEmail(): String? {
        return prefs.getString(USER_EMAIL, null)
    }

    /**
     * Function to fetch user email
     */
    fun fetchUserName(): String? {
        return prefs.getString(USER_NAME, null)
    }

    /**
     * Function to save user email
     */
    fun saveUserName(name: String?) {
        val editor = prefs.edit()
        editor.putString(USER_NAME, name)
        editor.apply()
    }

    /**
     * Function to fetch user email
     */
    fun fetchUserImage(): String? {
        return prefs.getString(USER_IMAGE, null)
    }

    /**
     * Function to save user email
     */
    fun saveUserImage(image: String?) {
        val editor = prefs.edit()
        editor.putString(USER_IMAGE, image)
        editor.apply()
    }


    fun saveRentalInProgress(inProgress: Boolean) {
        val editor = prefs.edit()
        editor.putBoolean(RENTAL_IN_PROGRESS, inProgress)
        editor.apply()
    }

    fun isRentalInProgress(): Boolean {
        return prefs.getBoolean(RENTAL_IN_PROGRESS, false)
    }
}