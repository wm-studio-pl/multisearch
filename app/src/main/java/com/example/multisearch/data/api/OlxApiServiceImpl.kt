package com.example.multisearch.data.api

import com.example.multisearch.data.model.Offer
import com.example.multisearch.data.model.User
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Single

class OlxApiServiceImpl: ApiService {
    override fun getOffers(phrase: String): Single<List<Offer>> {
//        val request: ANRequest<> =  AndroidNetworking.get(olx.address)
//            .addPathParameter("q-"+phrase)
//            .build();
//        ANResponse
        return Rx2AndroidNetworking.get("https://5e510330f2c0d300147c034c.mockapi.io/users")
            .build()
            .getObjectListSingle(Offer::class.java)

    }

    override fun getUsers(): Single<List<User>> {
        return Rx2AndroidNetworking.get("https://5e510330f2c0d300147c034c.mockapi.io/users")
            .build()
            .getObjectListSingle(User::class.java)
    }
}