package com.example.multisearch

import android.app.Application
import com.example.multisearch.data.model.Prefs

val prefs: Prefs by lazy {
    Multisearch.prefs!!
}

class Multisearch: Application() {
    var allegroApiToken: String? = null
    companion object {
        var prefs: Prefs? = null
    }

    override fun onCreate() {
        prefs = Prefs(applicationContext)
        super.onCreate()
    }
}