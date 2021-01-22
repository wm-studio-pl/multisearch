package com.example.multisearch.data.model

import android.content.Context
import android.content.SharedPreferences

class Prefs (context: Context) {
    val PREFS_FILENAME = "com.example.multisearch.data.model.prefs"
    val ALLEGROAPITOKEN = "AllegroApiToken"
    val ALLEGROAPITOKENEXPIRE = "AllegroApiTokenExpire"
    val EMPTY_STRING = ""
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)

    var allegroApiToken : String?
        get() = prefs.getString(ALLEGROAPITOKEN, EMPTY_STRING)
        set(value) = prefs.edit().putString(ALLEGROAPITOKEN, value).apply()

    var allegroApiTokenExpire : Long
        get() = prefs.getLong(ALLEGROAPITOKENEXPIRE, 0)
        set(value) = prefs.edit().putLong(ALLEGROAPITOKENEXPIRE, value).apply()
}