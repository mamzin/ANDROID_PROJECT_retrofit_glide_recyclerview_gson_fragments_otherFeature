package ru.mamzin.aleftask.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ru.mamzin.aleftask.R
import ru.mamzin.aleftask.model.DataAdapter
import ru.mamzin.aleftask.net.RetrofitService
import ru.mamzin.aleftask.utils.NetRepository
import ru.mamzin.aleftask.viewmodel.MainViewModel
import ru.mamzin.aleftask.viewmodel.MyViewModelFactory
import java.lang.Math.sqrt

class MainActivity : AppCompatActivity(), DataAdapter.CellClickListener {

    var recyclerView: RecyclerView? = null
    var glManager: GridLayoutManager? = null
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    var spanCount = 2

    lateinit var viewModel: MainViewModel
    private val adapter = DataAdapter(this)

    private val retrofitService = RetrofitService.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spanCount = if (isTablet(applicationContext)) {
            3
        } else {
            2
        }

        viewModel = ViewModelProvider(
            this,
            MyViewModelFactory(NetRepository(retrofitService))
        )[MainViewModel::class.java]

        recyclerView = findViewById(R.id.recycler_view)
        glManager = GridLayoutManager(this, spanCount)
        recyclerView!!.layoutManager = glManager
        recyclerView?.adapter = adapter

        getData()

        swipeRefreshLayout = findViewById(R.id.swipetorefresh)
        swipeRefreshLayout.setOnRefreshListener {
            getData()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun getData() {
        viewModel.dataList.observe(this, Observer {
            adapter.setImageList(it)
        })

        viewModel.errorMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

        viewModel.getAllImages()
    }

    override fun onCellClickListener(datalink: String) {
        val intent = Intent(this, FullImage::class.java)
        intent.putExtra("IMG_SRC", datalink)
        startActivity(intent)
    }

    private fun isTablet(context: Context): Boolean {
        val displayMetrics = context.resources.displayMetrics
        val yinch = displayMetrics.heightPixels / displayMetrics.ydpi
        val xinch = displayMetrics.widthPixels / displayMetrics.xdpi
        val diagonalinch = sqrt(xinch * xinch + yinch * yinch.toDouble())
        return diagonalinch >= 6.8
    }
}
