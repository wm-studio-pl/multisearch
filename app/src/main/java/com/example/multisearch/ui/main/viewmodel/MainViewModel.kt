package com.example.multisearch.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.multisearch.data.model.User
import com.example.multisearch.data.repository.MainRepository
import com.example.multisearch.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

//    private val offers = MutableLiveData<Resource<List<Offer>>>()
//    private val compositeDisposable = CompositeDisposable()
//
//    init {
//        fetchOffers()
//    }
//
//    private fun fetchOffers() {
//        offers.postValue(Resource.loading(null))
//        compositeDisposable.add(
//            mainRepository.getOffers("Samsung")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ offerList ->
//                    offers.postValue(Resource.success(offerList))
//                }, { throwable ->
//                    offers.postValue(Resource.error("Something Went Wrong", null))
//                })
//        )
//    }
//
//    override fun onCleared() {
//        super.onCleared()
//        compositeDisposable.dispose()
//    }
//
//    fun getOffers(): LiveData<Resource<List<Offer>>> {
//        return offers
//    }

    private val users = MutableLiveData<Resource<List<User>>>()
    private val compositeDisposable = CompositeDisposable()

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        users.postValue(Resource.loading(null))
        compositeDisposable.add(
            mainRepository.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ userList ->
                    users.postValue(Resource.success(userList))
                }, { throwable ->
                    users.postValue(Resource.error("Something Went Wrong", null))
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun getUsers(): LiveData<Resource<List<User>>> {
        return users
    }
}