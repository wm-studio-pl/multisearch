package com.example.multisearch.ui.main.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.multisearch.R
import com.example.multisearch.data.api.AllegroApiServiceImpl
import com.example.multisearch.data.api.ApiService
import com.example.multisearch.data.api.OlxApiServiceImpl
import com.example.multisearch.data.model.Offer
import com.example.multisearch.data.model.OffersDataBase
import com.example.multisearch.prefs
import com.example.multisearch.ui.main.adapter.MainAdapter
import com.example.multisearch.ui.main.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private var mainViewModel: MainViewModel ?= null
    private var adapter: MainAdapter ?= null
    private var apis = ArrayList<ApiService>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            var dataBaseInstance = OffersDataBase.getDatabasenIstance(this)
            mainViewModel?.setInstanceOfDb(dataBaseInstance)
            setContentView(R.layout.activity_main)
            setupAPIS()
            setupViewModel()
            setupUI()
            setupObserver()
    }

    private fun setupAPIS() {
        apis.add(AllegroApiServiceImpl(mainViewModel))
        apis.add(OlxApiServiceImpl())
        val ileApi = 2
        search_button.isEnabled = false
         GlobalScope.launch(Dispatchers.Main) {
             prefs.allegroApiToken = ""
             for (i in 0 until ileApi) {
                 val token = withContext(Dispatchers.IO) { apis.get(i).getToken(applicationContext) }
                 delay(2000)
             }
             search_button.isEnabled = true
         }
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(
            this
        ).get(MainViewModel::class.java)
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter()
        {
            it.let {
                mainViewModel?.deleteOffer(it)
            }
        }
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
    }

    private fun setupObserver() {
        mainViewModel?.offersList?.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                handleData(it)
            } else {
                handleZeroCase()
            }
        })
        /*mainViewModel.getUsers().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data?.let { users -> renderList(users) }
                    recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })*/
    }

    private fun handleData(data: List<Offer>) {
        adapter?.setData(data)
    }

    private fun handleZeroCase() {
        Toast.makeText(this, "Brak ofert", Toast.LENGTH_LONG).show()
    }

    private fun setListeners() {
        search_button.setOnClickListener {
            searchAndSaveData()
        }
    }

    private fun searchAndSaveData() {
        GlobalScope.launch(Dispatchers.Main) {
            val ileApi = 2
            search_button.isEnabled = false
            val searchPhrase:String = search_input.text.toString()
            withContext(Dispatchers.IO) {
                for (i in 0 until ileApi) {
                    apis.get(i).getOffers(searchPhrase)
                }
            }
            search_button.isEnabled = false
        }
    }

    /*private fun renderList(users: List<User>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }*/
}