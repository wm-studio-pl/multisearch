package com.example.multisearch.data.api

import android.os.Build
import androidx.annotation.RequiresApi
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.example.multisearch.data.model.Offer
import com.example.multisearch.data.model.User
import com.example.multisearch.prefs
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Single
import org.json.JSONObject
import java.util.*
import java.util.concurrent.TimeUnit

class AllegroApiServiceImpl : ApiService {

    private val clientId: String = "62c82108d0c74ecda229f5fd3cd7d1bf"
    private val clientSecret : String = "uUIhLI4e15bJ3K7y7UeozNdqq3NfrZXJKSyIW32BeQ70Ji1d64s0broaRpesckto"
    private var token: String = ""
    private val authUrl:String = "https://allegro.pl.allegrosandbox.pl/auth/oauth/token?grant_type=client_credentials"
    private val searchUrl: String = "https://api.allegro.pl.allegrosandbox.pl/offers/listing?phrase="

    override fun getOffers(phrase: String): Single<List<Offer>> {
        return Rx2AndroidNetworking.get("https://5e510330f2c0d300147c034c.mockapi.io/users")
            .build()
            .getObjectListSingle(Offer::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getToken() {
        val authText:String = "$clientId:$clientSecret";
        val encodedAuthTxt: String = "Basic " + Base64.getEncoder().encodeToString(authText.toByteArray())
        val res = Rx2AndroidNetworking.post(authUrl)
            .addHeaders("Authorization", encodedAuthTxt)
            //.addHeaders("Accept", "application/vnd.allegro.public.v1+json")
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(
                object: JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject?) {
                        if (response !== null) {
                            prefs.allegroApiToken = response.get("access_token").toString()
                            var expire:Long = response.getInt("expires_in")
                            expire += TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())
                            prefs.allegroApiTokenExpire = expire
                        }

                    }

                    override fun onError(anError: ANError?) {
                        val error = ""
                    }
                }
            )
        val nowyToken:String = res.toString()

    }

    override fun getUsers(): Single<List<User>> {
        TODO("Not yet implemented")
    }
}