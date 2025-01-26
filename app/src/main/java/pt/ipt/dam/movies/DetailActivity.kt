package pt.ipt.dam.movies

import Adapters.ActorsListAdapter
import Adapters.CategoryEachFilmListAdapter
import Domain.FilmItem
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.google.gson.Gson

class DetailActivity : AppCompatActivity() {
    private lateinit var mRequestQueue: RequestQueue
    private lateinit var mStringRequest: StringRequest
    private lateinit var progressBar: ProgressBar
    private lateinit var titleTxt: TextView
    private lateinit var movieRateTxt: TextView
    private lateinit var movieTimeTxt: TextView
    private lateinit var movieSummaryInfo: TextView
    private lateinit var movieActorsInfo: TextView
    private var idFilm: Int = 0
    private lateinit var pic2: ImageView
    private lateinit var backImg: ImageView
    private lateinit var adapterActorList: RecyclerView.Adapter<*>
    private lateinit var adapterCategory: RecyclerView.Adapter<*>
    private lateinit var recyclerViewActors: RecyclerView
    private lateinit var recyclerViewCategory: RecyclerView
    private lateinit var scrollView: NestedScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        idFilm = intent.getIntExtra("id", 0)
        initView()
        sendRequest()
    }

    private fun sendRequest() {
        mRequestQueue = Volley.newRequestQueue(this)
        progressBar.visibility = View.VISIBLE
        scrollView.visibility = View.GONE

        mStringRequest = StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies/$idFilm",
            { response ->
                val gson = Gson()
                progressBar.visibility = View.GONE
                scrollView.visibility = View.VISIBLE

                val item = gson.fromJson(response, FilmItem::class.java)

                Glide.with(this@DetailActivity)
                    .load(item.poster)
                    .into(pic2)

                titleTxt.text = item.title
                movieRateTxt.text = item.imdbRating
                movieTimeTxt.text = item.runtime
                movieSummaryInfo.text = item.plot
                movieActorsInfo.text = item.actors

                if (item.images != null) {
                    adapterActorList = ActorsListAdapter(item.images!!)
                    recyclerViewActors.adapter = adapterActorList
                }
                if (item.genres != null) {
                    adapterCategory = CategoryEachFilmListAdapter(item.genres!!)
                    recyclerViewCategory.adapter = adapterCategory
                }
            },
            { error ->
                progressBar.visibility = View.GONE
                Log.i("UiLover", "onErrorResponse: ${error.toString()}")
            }
        )
        mRequestQueue.add(mStringRequest)
    }

    private fun initView() {
        titleTxt = findViewById(R.id.MovieNameTxt)
        progressBar = findViewById(R.id.ProgressBarDetail)
        scrollView = findViewById(R.id.scrollView3)
        pic2 = findViewById(R.id.PicDetail)
        movieRateTxt = findViewById(R.id.MovieStar)
        movieTimeTxt = findViewById(R.id.MovieTime)
        movieSummaryInfo = findViewById(R.id.MovieSummary)
        movieActorsInfo = findViewById(R.id.MovieActorInfo)
        backImg = findViewById(R.id.BackImg)
        recyclerViewCategory = findViewById(R.id.GenreView)
        recyclerViewActors = findViewById(R.id.ImagesRecycler)
        recyclerViewActors.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewCategory.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        backImg.setOnClickListener { finish() }
    }
}