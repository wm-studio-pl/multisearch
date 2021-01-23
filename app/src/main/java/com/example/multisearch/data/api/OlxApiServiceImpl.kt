package com.example.multisearch.data.api

import android.content.Context
import com.example.multisearch.ui.main.viewmodel.MainViewModel

class OlxApiServiceImpl (val viewModel: MainViewModel?): ApiService {

    private val searchUrl: String = "https://www.olx.pl/oferty/q-"
    private val site:String = "OLX"
    private val prodUrl: String = "https://www.olx.pl/oferta/"
    private val icon: String = "olx_icon.jpg"

    override fun getOffers(phrase: String) {
//TODO

    }

    override suspend fun getToken(context: Context) {
        //nothing to work
    }
}