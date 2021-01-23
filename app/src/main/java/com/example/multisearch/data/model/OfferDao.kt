package com.example.multisearch.data.model

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface OfferDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOffer(data:Offer) : Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOfferList(dataList:List<Offer>): Completable

    @Query("SELECT * FROM ${Offer.TABLE_NAME} ORDER BY price ASC")
    fun getAllOffers():Single<List<Offer>>

    @Query("SELECT * FROM ${Offer.TABLE_NAME} WHERE :search_id IS NULL OR search_id=:search_id ORDER BY price ASC")
    fun getOfferBySearch(search_id: String?= null): Single<List<Offer>>

    @Delete
    fun deleteOffer(offer: Offer) : Completable

    @Query("DELETE FROM ${Offer.TABLE_NAME} WHERE 1")
    fun deleteAll()

    @Update
    fun updateOffer(offer: Offer)
}