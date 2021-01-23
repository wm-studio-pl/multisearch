package com.example.multisearch.data.api

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.example.multisearch.data.model.Offer
import com.example.multisearch.prefs
import com.example.multisearch.ui.main.viewmodel.MainViewModel
import com.example.multisearch.utils.errorManager
import com.rx2androidnetworking.Rx2AndroidNetworking
import org.json.JSONArray
import org.json.JSONObject
import java.net.URLEncoder
import java.util.*
import java.util.concurrent.TimeUnit

class AllegroApiServiceImpl (val viewModel: MainViewModel?): ApiService {

    private val clientId: String = "62c82108d0c74ecda229f5fd3cd7d1bf"
    private val clientSecret : String = "uUIhLI4e15bJ3K7y7UeozNdqq3NfrZXJKSyIW32BeQ70Ji1d64s0broaRpesckto"
    private var token: String = ""
    private val authUrl:String = "https://allegro.pl.allegrosandbox.pl/auth/oauth/token?grant_type=client_credentials"
    private val searchUrl: String = "https://api.allegro.pl.allegrosandbox.pl/offers/listing?phrase="
    private val site:String = "Allegro(sandbox)"
    private val prodUrl: String = "https://allegro.pl.allegrosandbox.pl/oferta/"
    private val icon: String = "allegro_icon.jpg"

    override fun getOffers(phrase: String) {
        val encodedPhrase:String = URLEncoder.encode(phrase, "utf-8")
        val res = Rx2AndroidNetworking.get(searchUrl + encodedPhrase)
            .addHeaders("Authorization", "Bearer  ${prefs.allegroApiToken}")
            .addHeaders("Accept", "application/vnd.allegro.public.v1+json")
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(
                object: JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject?) {
                        if (response !== null) {
                            val items = response.get("items") as JSONObject
                            val promoted = items.get("promoted") as JSONArray
                            val regular = items.get("regular") as JSONArray
                            var offers: List<Offer> = ArrayList<Offer>()
                            if (promoted.length()>0 )
                            {
                                offers = parseOffers(promoted)
                            }
                            if (regular.length()>0)
                            {
                                offers += parseOffers(regular)
                            }
                            saveData(offers)
                        }
                    }

                    override fun onError(anError: ANError?) {
                        val error = "Pobranie ofert Allegro nie powiodło się!"
                        //val em: errorManager = errorManager(context, error, Toast.LENGTH_LONG)
                    }
                }
            )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getToken(context: Context) {
            val authText: String = "$clientId:$clientSecret";
            val encodedAuthTxt: String =
                "Basic " + Base64.getEncoder().encodeToString(authText.toByteArray())
            val res = Rx2AndroidNetworking.post(authUrl)
                .addHeaders("Authorization", encodedAuthTxt)
                //.addHeaders("Accept", "application/vnd.allegro.public.v1+json")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(
                    object : JSONObjectRequestListener {
                        override fun onResponse(response: JSONObject?) {
                            if (response !== null) {
                                prefs.allegroApiToken = response.get("access_token").toString()
                                var expire: Long = response.getLong("expires_in")
                                expire += TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())
                                prefs.allegroApiTokenExpire = expire
                            }
                        }

                        override fun onError(anError: ANError?) {
                            val error = "Pobranie tokenu API Allegro nie powiodło się!"
                            val em: errorManager = errorManager(context, error, Toast.LENGTH_LONG)
                        }
                    }
                )
            /* val request = AndroidNetworking.post(authUrl)
            .addHeaders("Authorization", encodedAuthTxt)
            .build()

        val res = request.executeForJSONArray();
        if (res.isSuccess) {
            val result = res.result
        }*/

    }

    private fun parseOffers(jlist: JSONArray):List<Offer> {
        var offers = ArrayList<Offer>()
        val uuid = System.currentTimeMillis().toString()
        for (i in 0 until jlist.length())
        {
            val item = jlist.getJSONObject(i)
            val oName = item.getString("name")
            val oId = item.getInt("id")
            val priceObject = item.getJSONObject("sellingMode").getJSONObject("price")
            val oCurrency = priceObject.getString("currency")
            val oPrice = priceObject.getDouble("amount")
            val oImages = item.getJSONArray("images")
            var oIcon = ""
            if (oImages.length() > 0)
            {
                oIcon = oImages.getJSONObject(0).getString("url")
            }
            val oLink = "$prodUrl-$oId"

            val offer:Offer = Offer(offerId = oId, name = oName, price = oPrice,
                currency = oCurrency, icon = oIcon, link = oLink, site = site, searchId = uuid)
            offers.add(offer)
        }
        return offers
    }

    private fun saveData(data: List<Offer>) {
        viewModel?.saveDatasIntoDb(data)
    }
}