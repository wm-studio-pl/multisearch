package com.example.multisearch.data.api

import com.example.multisearch.data.model.Offer
import com.example.multisearch.data.model.User
import io.reactivex.Single

interface ApiService {

    fun getOffers(phrase: String):Single<List<Offer>>
    fun getUsers(): Single<List<User>>

}