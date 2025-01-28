package pt.ipt.dam.movies

import Adapters.CategoryListAdapter
import Adapters.FilmListAdapter
import Adapters.SliderAdapters
import Domain.FilmItem
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

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.speech.RecognizerIntent
import android.content.ActivityNotFoundException
import android.content.Context
import java.util.*
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.sqrt


class MainActivity : AppCompatActivity(), SensorEventListener {
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

    private lateinit var searchEdt: EditText
    private lateinit var micButton: ImageView
    private val SPEECH_REQUEST_CODE = 0

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var lastUpdate: Long = 0
    private var lastX: Float = 0f
    private var lastY: Float = 0f
    private var lastZ: Float = 0f
    private val SHAKE_THRESHOLD = 200

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

        setupVoiceSearch()

        // Inicializar sensor
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
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
        sensorManager.unregisterListener(this)
    }

    override fun onResume() {
        super.onResume()
        sliderHandler.postDelayed(sliderRunnable, 2000)
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
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

    private fun setupVoiceSearch() {
        searchEdt = findViewById(R.id.editTextText2)
        micButton = findViewById(R.id.microphoneButton)
        micButton.setOnClickListener {
            if (checkAudioPermission()) {
                startVoiceRecognition()
            } else {
                requestAudioPermission()
            }
        }
    }
    private fun checkAudioPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
    }
    private fun requestAudioPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.RECORD_AUDIO),
            SPEECH_REQUEST_CODE
        )
    }
    private fun startVoiceRecognition() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Diga o nome do filme...")
        }
        try {
            startActivityForResult(intent, SPEECH_REQUEST_CODE)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                this,
                "Seu dispositivo não suporta reconhecimento de voz",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            val spokenText: String? =
                data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.let { results ->
                    results[0]
                }
            spokenText?.let {
                searchEdt.setText(it)
                searchMovies(it)
            }
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == SPEECH_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startVoiceRecognition()
            } else {
                Toast.makeText(
                    this,
                    "Permissão de áudio necessária para pesquisa por voz",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    private fun searchMovies(query: String) {
        loading1.visibility = View.VISIBLE
        mRequestQueue = Volley.newRequestQueue(this)
        // URL da API com o parâmetro de busca
        val url = "https://moviesapi.ir/api/v1/movies?q=$query"
        mStringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                loading1.visibility = View.GONE
                val gson = Gson()
                val listFilm = gson.fromJson(response, ListFilm::class.java)
                // Verifica se encontrou algum filme
                if (listFilm.data?.isNotEmpty() == true) {
                    // Pega o primeiro filme encontrado
                    val firstMovie = listFilm.data!![0]
                    // Abre o DetailActivity com o ID do filme encontrado
                    val intent = Intent(this, DetailActivity::class.java)
                    intent.putExtra("id", firstMovie.id)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this,
                        "Nenhum filme encontrado com esse nome",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            { error ->
                loading1.visibility = View.GONE
                Toast.makeText(
                    this,
                    "Erro ao buscar o filme: ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )
        mRequestQueue.add(mStringRequest)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val curTime = System.currentTimeMillis()
            // Só processa se passou mais de 100ms desde a última atualização
            if ((curTime - lastUpdate) > 100) {
                val diffTime = curTime - lastUpdate
                lastUpdate = curTime
                
                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]
                
                val speed = sqrt(
                    ((x - lastX) * (x - lastX) + 
                    (y - lastY) * (y - lastY) + 
                    (z - lastZ) * (z - lastZ)) / diffTime * 10000
                )
                
                if (speed > SHAKE_THRESHOLD) {
                    // Usuário agitou o dispositivo, buscar filme aleatório
                    getRandomMovie()
                }
                
                lastX = x
                lastY = y
                lastZ = z
            }
        }
    }
    
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Não é necessário implementar
    }
    
    private fun getRandomMovie() {
        loading1.visibility = View.VISIBLE
        mRequestQueue = Volley.newRequestQueue(this)
        
        // Gerar um ID aleatório entre 1 e 250 (ajuste conforme necessário)
        val randomId = (1..250).random()
        val url = "https://moviesapi.ir/api/v1/movies/$randomId"
        
        mStringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                loading1.visibility = View.GONE
                val gson = Gson()
                val movie = gson.fromJson(response, FilmItem::class.java)
                
                // Abrir DetailActivity com o filme aleatório
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra("id", movie.id)
                startActivity(intent)
                
                Toast.makeText(
                    this,
                    "Filme aleatório encontrado!",
                    Toast.LENGTH_SHORT
                ).show()
            },
            { error ->
                loading1.visibility = View.GONE
                Toast.makeText(
                    this,
                    "Erro ao buscar filme aleatório: ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )
        mRequestQueue.add(mStringRequest)
    }
}