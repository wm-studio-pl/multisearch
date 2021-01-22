package com.example.multisearch.data.repository

import com.example.multisearch.data.api.ApiHelper
import com.example.multisearch.data.model.Offer
import com.example.multisearch.data.model.User
import io.reactivex.Single

class MainRepository(private val apiHelper: ApiHelper) {
    fun getOffers(phrase:String): Single<List<Offer>> {
        return apiHelper.getOffers(phrase)
    }

    fun getUsers(): Single<List<User>> {
        return apiHelper.getUsers()
    }
}