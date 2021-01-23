package com.example.multisearch.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity (tableName = Offer.TABLE_NAME)
data class Offer (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    val id: Int ?= null,
    @ColumnInfo(name = OFFER_ID)
    val offerId: Int ?= null,
    @ColumnInfo(name = NAME)
    val name: String ?= null,
    @ColumnInfo(name = PRICE)
    val price: Double ?= null,
    @ColumnInfo(name = CURRENCY)
    val currency: String ?= null,
    @ColumnInfo(name = ICON)
    val icon : String ?= null,
    @ColumnInfo(name = LINK)
    val link : String ?= null,
    @ColumnInfo(name = SITE)
    val site: String ?= null,
    @ColumnInfo(name = SEARCH_ID)
    val searchId: String ?= null
) : Parcelable {
    companion object {
        const val TABLE_NAME = "offers"
        const val ID = "id"
        const val OFFER_ID = "offer_id"
        const val NAME = "name"
        const val PRICE = "price"
        const val CURRENCY = "currency"
        const val ICON = "icon"
        const val LINK = "link"
        const val SITE = "site"
        const val SEARCH_ID = "search_id"
    }
}