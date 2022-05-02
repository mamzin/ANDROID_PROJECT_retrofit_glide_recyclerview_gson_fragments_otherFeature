package ru.mamzin.aleftask.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.mamzin.aleftask.utils.NetRepository
import ru.mamzin.aleftask.R
import ru.mamzin.aleftask.model.DataAdapter
import ru.mamzin.aleftask.net.RetrofitService
import java.lang.Math.sqrt

class MainActivity : AppCompatActivity(), DataAdapter.CellClickListener {

    lateinit var dataList: List<String>
    var recyclerView: RecyclerView? = null
    var glManager: GridLayoutManager? = null
    var adapter: DataAdapter? = null
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private val repository: NetRepository = NetRepository(RetrofitService.getInstance())
    var spanCount = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spanCount = if (isTablet(applicationContext)) {
            3
        } else {
            2
        }

        recyclerView = findViewById(R.id.recycler_view)
        glManager = GridLayoutManager(this, spanCount)
        recyclerView!!.layoutManager = glManager
        getData()


        swipeRefreshLayout = findViewById(R.id.swipetorefresh)
        swipeRefreshLayout.setOnRefreshListener {
            getData()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun getData() {
        val call: Call<List<String>> = repository.getAllImages()
        call.enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>?, response: Response<List<String>>?) {
                dataList = response?.body()!!
                adapter = DataAdapter(dataList, this@MainActivity, this@MainActivity)
                recyclerView!!.adapter = adapter
                recyclerView?.adapter?.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.localizedMessage, Toast.LENGTH_LONG).show()
            }
        })
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
