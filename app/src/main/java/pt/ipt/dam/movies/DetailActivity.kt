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
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.google.gson.Gson

/**
 * Activity responsável por mostrar os detalhes de um filme específico
 * Mostra informações completas como sinopse, atores, avaliações, etc.
 */
class DetailActivity : AppCompatActivity() {
    /**
     * Fila de "requests" Volley para chamadas à API
     * Componentes para requisições HTTP
     */
    private lateinit var mRequestQueue: RequestQueue
    /**
     * "Request" específica para obter detalhes do filme
     */
    private lateinit var mStringRequest: StringRequest
    /**
     * Indicador de carregamento
     */
    private lateinit var progressBar: ProgressBar
    /**
     * Views para mostrar as informações do filme
     */
    private lateinit var titleTxt: TextView
    private lateinit var movieRateTxt: TextView
    private lateinit var movieTimeTxt: TextView
    private lateinit var movieSummaryInfo: TextView
    private lateinit var movieActorsInfo: TextView
    /**
     * ID do filme a ser mostrado
     */
    private var idFilm: Int = 0
    /**
     * ImageViews para poster e botão de voltar
     */
    private lateinit var pic2: ImageView
    private lateinit var backImg: ImageView
    /**
     * Adaptadores para as listas de atores e categorias
     */
    private lateinit var adapterActorList: RecyclerView.Adapter<*>
    private lateinit var adapterCategory: RecyclerView.Adapter<*>
    /**
     * RecyclerViews para mostrar listas de atores e categorias
     */
    private lateinit var recyclerViewActors: RecyclerView
    private lateinit var recyclerViewCategory: RecyclerView
    /**
     * ScrollView para dar "scroll" ao conteúdo da tela
     */
    private lateinit var scrollView: NestedScrollView

    /**
     * Inicializa a Activity e configura a interface
     */
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

    /**
     * Faz o "request" HTTP para obter os detalhes do filme
     * Atualiza a interface com os dados recebidos
     */
    private fun sendRequest() {
        mRequestQueue = Volley.newRequestQueue(this)
        progressBar.visibility = View.VISIBLE
        scrollView.visibility = View.GONE

        mStringRequest = StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies/$idFilm",
            { response ->
                // Processar resposta
                val gson = Gson()
                progressBar.visibility = View.GONE
                scrollView.visibility = View.VISIBLE

                //Gson converte o JSON para objeto FilmItem
                val item = gson.fromJson(response, FilmItem::class.java)

                Glide.with(this@DetailActivity)
                    .load(item.poster)
                    .into(pic2)

                // Atualizar interface
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
                // Tratar erro
                progressBar.visibility = View.GONE
                Log.i("MoviesApp", "onErrorResponse: $error")
            }
        )
        // Adicionar requisição à fila
        mRequestQueue.add(mStringRequest)
    }

    /**
     * Inicializa e configura todos os componentes da interface
     * Define os listeners e layouts necessários
     */
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