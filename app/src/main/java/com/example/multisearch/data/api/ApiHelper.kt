package com.example.multisearch.data.api

class ApiHelper(private val apiService: ApiService) {
    fun getOffers(phrase: String) = apiService.getOffers(phrase);
    fun getUsers() = apiService.getUsers()
}