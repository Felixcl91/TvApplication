package com.example.tvapplication.ui.movie.view

import android.app.Dialog
import android.app.SearchManager
import android.content.Intent
import android.database.Cursor
import android.database.MatrixCursor
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.cursoradapter.widget.CursorAdapter
import androidx.cursoradapter.widget.SimpleCursorAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tvapplication.R
import com.example.tvapplication.data.api.ApiHelper
import com.example.tvapplication.data.api.ApiServiceImpl
import com.example.tvapplication.data.model.Movie
import com.example.tvapplication.data.model.Recommendations
import com.example.tvapplication.data.model.ResponseItem
import com.example.tvapplication.ui.base.ViewModelFactory
import com.example.tvapplication.ui.main.adapter.MainAdapter
import com.example.tvapplication.ui.main.view.MainActivity
import com.example.tvapplication.ui.movie.adapter.RecommendationsAdapter
import com.example.tvapplication.ui.movie.viewmodel.MovieViewModel
import com.example.tvapplication.utils.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_movie.*

class MovieActivity : AppCompatActivity() {

    private lateinit var movieViewModel: MovieViewModel
    private var progressDialog: Dialog? = null
    private lateinit var adapter: RecommendationsAdapter

    private var imageMovieBack: ImageView? = null
    private var movieImage: ImageView? = null
    private var movieName: TextView? = null
    private var movieYear: TextView? = null
    private var movieGenre: TextView? = null
    private var movieTime: TextView? = null
    private var movieDesc: TextView? = null

    private var valueInt: Int? = null

    private var listMovies = ArrayList<ResponseItem?>()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        imageMovieBack = findViewById(R.id.image_movie_back)
        movieImage = findViewById(R.id.movie_image)
        movieName = findViewById(R.id.movie_name)
        movieYear = findViewById(R.id.movie_year)
        movieGenre = findViewById(R.id.movie_genre)
        movieTime = findViewById(R.id.movie_time)
        movieDesc = findViewById(R.id.movie_desc)

        // get id film
        val id = intent.getStringExtra("id")
        val ext_id = id + "_PAGE_HD"

        // get value
        val valInt = intent.getIntExtra("val", 0)
        valueInt = valInt

        setupViewModel()
        if (id != null) {
            setupObserver("json", ext_id)
        }

        //setupList()
        val ext = "external_content_id:$id"
        setupRecommendations("json", "all", "false", "true",
                10, "ar_od_blend_2424video", ext)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView

        searchView.queryHint = getString(R.string.search)
        searchView.findViewById<AutoCompleteTextView>(R.id.search_src_text).threshold = 1

        val from = arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1)
        val to = intArrayOf(R.id.item_label)
        val cursorAdapter = SimpleCursorAdapter(this, R.layout.search_item, null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER)

        searchView.suggestionsAdapter = cursorAdapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                hideKeyboard()
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                val cursor = MatrixCursor(arrayOf(BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1))
                query?.let {

                    listMovies.forEachIndexed { index, movie ->
                        if (movie?.name!!.contains(query, true))
                            cursor.addRow(arrayOf(index, movie.name))
                    }
                }

                cursorAdapter.changeCursor(cursor)
                return true
            }

        })

        searchView.setOnSuggestionListener(object: SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {
                return false
            }

            override fun onSuggestionClick(position: Int): Boolean {
                hideKeyboard()
                val cursor = searchView.suggestionsAdapter.getItem(position) as Cursor
                val selection = cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))
                searchView.setQuery(selection, false)

                listMovies.forEach { movie ->
                    if (movie?.name == selection) {
                        goToMovieActivity(movie?.externalContentId!!)
                    }
                }

                // Do something with selection
                return true
            }

        })

        //return super.onCreateOptionsMenu(menu)
        return true
    }

    private fun goToMovieActivity(id: String) {
        val intent = Intent(this, MovieActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showData(movie: Movie) {
        if (valueInt == 1) {
            movieName?.setTextColor(getColor(R.color.yellow))
        }
        val url = "https://smarttv.orangetv.orange.es/stv/api/rtv/v1/images"
        imageMovieBack?.loadImageFromLink(url+ movie.attachments?.get(1)?.value)
        movieImage?.loadImageFromLink(url+ movie.attachments?.get(2)?.value)
        movieName?.text = movie.name
        movieYear?.text = movie.year.toString()
        movieGenre?.text = movie.genreEntityList!![0]?.name
        val duration = movie.duration!!/60000

        movieTime?.text = "$duration ${getString(R.string.minutes)}"
        movieDesc?.text = movie.description

    }

    private fun setupViewModel() {
        movieViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(ApiServiceImpl()))
        ).get(MovieViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupObserver(json_id: String, id_ext: String) {
        showProgressDialog()
        movieViewModel.fetchMovie(json_id, id_ext)
        movieViewModel.getMovie().observe(this, Observer {
            Log.e("MOVIE", "observer-----$it" )
            when (it.status) {
                Status.SUCCESS -> {
                    dismissProgressDialog()
                    it.data.let { movies ->
                        if (movies != null) {
                            showData(movies)
                        }
                    }
                }
                Status.LOADING -> {
                    showProgressDialog()
                }
                Status.ERROR -> {
                    dismissProgressDialog()
                    toast(it.message.toString())
                }
            }
        })

    }

    private fun setupRecommendations(one: String, two: String, three: String,
                                     four: String, five: Int, six: String, seven: String) {
        movieViewModel.fetchRecommendations(one, two, three, four, five, six, seven)
        setupList()
        movieViewModel.getMovies().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBarMovie.visibility = View.GONE
                    it.data.let { movies ->
                        if (movies != null) {
                            renderListRec(movies)
                            listMovies = it.data!!
                        }
                    }
                    recycler_rec.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBarMovie.visibility = View.VISIBLE
                    recycler_rec.visibility = View.GONE
                }
                Status.ERROR -> {
                    progressBarMovie.visibility = View.GONE
                    toast(it.message.toString())
                }
            }
        })

    }

    private fun setupList() {
        recycler_rec.layoutManager = GridLayoutManager(this, 3)
        adapter = RecommendationsAdapter(arrayListOf())
        recycler_rec.addItemDecoration(
                DividerItemDecoration(
                        recycler_rec.context,
                        (recycler_rec.layoutManager as GridLayoutManager).orientation
                )
        )
        recycler_rec.adapter = adapter
    }

    private fun renderListRec(responseItem: ArrayList<ResponseItem?>?) {
        adapter.addData(responseItem)
        adapter.notifyDataSetChanged()
    }

    fun showProgressDialog() {
        progressDialog = createProgressDialog()
    }

    fun dismissProgressDialog() {
        progressDialog?.dismiss()
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}