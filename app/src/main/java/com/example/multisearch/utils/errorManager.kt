package com.example.multisearch.utils

import android.content.Context
import android.widget.Toast

class errorManager {
    private lateinit var context: Context
    private lateinit var message: String
    private var length: Int = Toast.LENGTH_LONG

    constructor(context: Context, message: String, length: Int) {
        this.context = context
        this.message = message
        this.length = length
    }

    fun setMessage(message: String): errorManager{
        this.message = message
        return this
    }

    fun show() {
        Toast.makeText(context, message, length).show()
    }
}