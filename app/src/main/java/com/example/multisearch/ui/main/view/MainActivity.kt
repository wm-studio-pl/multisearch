package com.example.multisearch.ui.main.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.multisearch.R
import com.example.multisearch.data.api.AllegroApiServiceImpl
import com.example.multisearch.data.api.ApiHelper
import com.example.multisearch.data.api.ApiServiceImpl
import com.example.multisearch.data.model.User
import com.example.multisearch.prefs
import com.example.multisearch.ui.base.ViewModelFactory
import com.example.multisearch.ui.main.adapter.MainAdapter
import com.example.multisearch.ui.main.viewmodel.MainViewModel
import com.example.multisearch.utils.Status
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val api: AllegroApiServiceImpl = AllegroApiServiceImpl()
        prefs.allegroApiToken = ""
        api.getToken()
        setContentView(R.layout.activity_main)
        setupUI()
        setupViewModel()
        setupObserver()
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrayListOf())
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    private fun setupObserver() {
        mainViewModel.getUsers().observe(this, Observer {
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
        })
    }

    private fun renderList(users: List<User>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(ApiServiceImpl()))
        ).get(MainViewModel::class.java)
    }
}

//class MainActivity : AppCompatActivity() {
//
//    private lateinit var mainViewModel: MainViewModel
//    private lateinit var adapter: MainAdapter
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        setupUI()
//        setupViewModel()
//        setupObserver()
//    }
//
//    private fun setupUI() {
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        adapter = MainAdapter(arrayListOf())
//        recyclerView.addItemDecoration(
//            DividerItemDecoration(
//                recyclerView.context,
//                (recyclerView.layoutManager as LinearLayoutManager).orientation
//            )
//        )
//        recyclerView.adapter = adapter
//    }
//
//    private fun setupObserver() {
//        mainViewModel.getOffers().observe(this, Observer {
//            when (it.status) {
//                Status.SUCCESS -> {
//                    progressBar.visibility = View.GONE
//                    it.data?.let { users -> renderList(users) }
//                    recyclerView.visibility = View.VISIBLE
//                }
//                Status.LOADING -> {
//                    progressBar.visibility = View.VISIBLE
//                    recyclerView.visibility = View.GONE
//                }
//                Status.ERROR -> {
//                    //Handle Error
//                    progressBar.visibility = View.GONE
//                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
//                }
//            }
//        })
//    }
//
//    private fun renderList(offers: List<Offer>) {
//        adapter.addData(offers)
//        adapter.notifyDataSetChanged()
//    }
//
//    private fun setupViewModel() {
//        mainViewModel = ViewModelProviders.of(
//            this,
//            ViewModelFactory(ApiHelper(AllegroApiServiceImpl()))
//        ).get(MainViewModel::class.java)
//    }
//}