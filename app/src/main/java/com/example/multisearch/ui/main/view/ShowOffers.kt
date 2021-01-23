
import android.annotation.SuppressLint
import android.app.ListActivity
import android.os.Bundle
import android.widget.ListView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.multisearch.R
import com.example.multisearch.ui.main.adapter.MainAdapter
import com.example.multisearch.ui.main.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_layout.*

class ShowOffers : ListActivity() {

    private var adapter: MainAdapter?= null
    private var mainViewModel: MainViewModel?= null

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.offers_layout)
//        mainViewModel = ViewModelProviders.of(
//            this
//        ).get(MainViewModel::class.java)
        setupUI()
    }

    private fun setupUI() {
        var listView:ListView = offerList
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
}