package pt.ipt.dam.movies

import Adapters.CategoryListAdapter
import Adapters.FilmListAdapter
import Adapters.SliderAdapters
import Domain.GenresItem
import Domain.ListFilm
import Domain.SliderItems
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class MainActivity : AppCompatActivity() {
    private lateinit var adapterBestMovies: RecyclerView.Adapter<*>
    private lateinit var adapterUpComing: RecyclerView.Adapter<*>
    private lateinit var adapterCategory: RecyclerView.Adapter<*>
    private lateinit var recyclerViewBestMovies: RecyclerView
    private lateinit var recyclerViewUpcoming: RecyclerView
    private lateinit var recyclerViewCategory: RecyclerView
    private lateinit var mRequestQueue: RequestQueue
    private lateinit var mStringRequest: StringRequest
    private lateinit var mStringRequest2: StringRequest
    private lateinit var mStringRequest3: StringRequest
    private lateinit var loading1: ProgressBar
    private lateinit var loading2: ProgressBar
    private lateinit var loading3: ProgressBar
    private lateinit var viewPager2: ViewPager2
    private val sliderHandler = android.os.Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initView()
        banners()
        sendRequestBestMovies()
        sendRequestUpComing()
        sendRequestCategory()
    }

    private fun sendRequestBestMovies() {
        mRequestQueue = Volley.newRequestQueue(this)
        loading1.visibility = View.VISIBLE

        mStringRequest = StringRequest(
            Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=1",
            { response ->
                val gson = Gson()
                loading1.visibility = View.GONE
                val items = gson.fromJson(response, ListFilm::class.java)
                adapterBestMovies = FilmListAdapter(items)
                recyclerViewBestMovies.adapter = adapterBestMovies
            },
            { error ->
                loading1.visibility = View.GONE
                Log.i("UiLover", "onErrorResponse: $error")
            }
        )
        mRequestQueue.add(mStringRequest)
    }

    private fun sendRequestUpComing() {
        mRequestQueue = Volley.newRequestQueue(this)
        loading3.visibility = View.VISIBLE

        mStringRequest3 = StringRequest(
            Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=2",
            { response ->
                val gson = Gson()
                loading3.visibility = View.GONE
                val items = gson.fromJson(response, ListFilm::class.java)
                adapterUpComing = FilmListAdapter(items)
                recyclerViewUpcoming.adapter = adapterUpComing
            },
            { error ->
                loading3.visibility = View.GONE
                Log.i("UiLover", "onErrorResponse: $error")
            }
        )
        mRequestQueue.add(mStringRequest3)
    }

    private fun sendRequestCategory() {
        mRequestQueue = Volley.newRequestQueue(this)
        loading2.visibility = View.VISIBLE

        mStringRequest2 = StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/genres",
            { response ->
                loading2.visibility = View.GONE
                val gson = Gson()
                val catList: ArrayList<GenresItem> = gson.fromJson(response, object : TypeToken<ArrayList<GenresItem>>() {}.type)
                adapterCategory = CategoryListAdapter(catList)
                recyclerViewCategory.adapter = adapterCategory
            },
            { error ->
                loading2.visibility = View.GONE
                Log.i("UiLover", "onErrorResponse: ${error.toString()}")
            }
        )
        mRequestQueue.add(mStringRequest2)
    }

    private fun banners() {
        val sliderItems = mutableListOf(
            SliderItems(R.drawable.wide),
            SliderItems(R.drawable.wide1),
            SliderItems(R.drawable.wide3)
        )

        viewPager2.adapter = SliderAdapters(sliderItems, viewPager2)
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.offscreenPageLimit = 3
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
            addTransformer(ViewPager2.PageTransformer { page, position ->
                val r = 1 - kotlin.math.abs(position)
                page.scaleY = 0.85f + r * 0.15f
            })
        }

        viewPager2.setPageTransformer(compositePageTransformer)
        viewPager2.setCurrentItem(1)
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderHandler.removeCallbacks(sliderRunnable)
            }
        })
    }

    private val sliderRunnable = Runnable {
        viewPager2.currentItem = viewPager2.currentItem + 1
    }

    override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacks(sliderRunnable)
    }

    override fun onResume() {
        super.onResume()
        sliderHandler.postDelayed(sliderRunnable, 2000)
    }

    private fun initView() {
        viewPager2 = findViewById(R.id.viewpagerSlider)
        recyclerViewBestMovies = findViewById(R.id.view1)
        recyclerViewBestMovies.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        recyclerViewUpcoming = findViewById(R.id.view3)
        recyclerViewUpcoming.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        recyclerViewCategory = findViewById(R.id.view2)
        recyclerViewCategory.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        loading1 = findViewById(R.id.progressBar1)
        loading2 = findViewById(R.id.progressBar2)
        loading3 = findViewById(R.id.progressBar3)
    }
}