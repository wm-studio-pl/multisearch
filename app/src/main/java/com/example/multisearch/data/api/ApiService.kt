package com.example.multisearch.data.api

import android.content.Context

interface ApiService {

    fun getOffers(phrase: String)
    suspend fun getToken(context: Context)

}