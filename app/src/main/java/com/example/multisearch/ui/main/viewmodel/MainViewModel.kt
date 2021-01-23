package com.example.multisearch.ui.main.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.multisearch.data.model.Offer
import com.example.multisearch.data.model.OffersDataBase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel() : ViewModel() {

    protected val compositeDisposable = CompositeDisposable()

    private var dataBaseInstance: OffersDataBase ?= null

    var offersList = MutableLiveData<List<Offer>>()

    fun setInstanceOfDb(dataBaseInstance: OffersDataBase) {
        this.dataBaseInstance = dataBaseInstance
    }

    fun saveDataIntoDb(data: Offer){

        dataBaseInstance?.OfferDao()?.insertOffer(data)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe ({
            },{

            })?.let {
                compositeDisposable.add(it)
            }
    }

    fun saveDatasIntoDb(data: List<Offer>){

        dataBaseInstance?.OfferDao()?.insertOfferList(data)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe ({
            },{

            })?.let {
                compositeDisposable.add(it)
            }
    }

    fun getOffer(){

        dataBaseInstance?.OfferDao()?.getAllOffers()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe ({
                if(!it.isNullOrEmpty()){
                    offersList.postValue(it)
                }else{
                    offersList.postValue(listOf())
                }
                it?.forEach {
                    Log.v("Offer Name",it.name)
                }
            },{
            })?.let {
                compositeDisposable.add(it)
            }
    }

    fun getSearchOffers(searchId: String){

        dataBaseInstance?.OfferDao()?.getOfferBySearch(searchId)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe ({
                if(!it.isNullOrEmpty()){
                    offersList.postValue(it)
                }else{
                    offersList.postValue(listOf())
                }
                it?.forEach {
                    Log.v("Offer Name",it.name)
                }
            },{
            })?.let {
                compositeDisposable.add(it)
            }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }

    fun deleteOffer(offer: Offer) {
        dataBaseInstance?.OfferDao()?.deleteOffer(offer)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe ({
                //Refresh Page data
                getOffer()
            },{

            })?.let {
                compositeDisposable.add(it)
            }
    }

}