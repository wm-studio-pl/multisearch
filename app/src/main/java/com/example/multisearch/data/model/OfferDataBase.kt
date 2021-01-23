package com.example.multisearch.data.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Offer::class], version = DB_VERSION)
abstract class OffersDataBase : RoomDatabase() {
    abstract fun OfferDao(): OfferDao

    companion object {
        @Volatile
        private var databseInstance: OffersDataBase? = null

        fun getDatabasenIstance(mContext: Context): OffersDataBase =
            databseInstance ?: synchronized(this) {
                databseInstance ?: buildDatabaseInstance(mContext).also {
                    databseInstance = it
                }
            }

        private fun buildDatabaseInstance(mContext: Context) =
            Room.databaseBuilder(mContext, OffersDataBase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()

    }
}


const val DB_VERSION = 1

const val DB_NAME = "OfferDB.db"