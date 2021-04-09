package com.example.tvapplication.ui.main.view

import android.app.SearchManager
import android.content.Intent
import android.database.Cursor
import android.database.MatrixCursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.AutoCompleteTextView
import androidx.appcompat.widget.SearchView
import androidx.cursoradapter.widget.SimpleCursorAdapter
import androidx.cursoradapter.widget.CursorAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tvapplication.R
import com.example.tvapplication.data.api.ApiHelper
import com.example.tvapplication.data.api.ApiServiceImpl
import com.example.tvapplication.data.model.Movie
import com.example.tvapplication.ui.base.ViewModelFactory
import com.example.tvapplication.ui.main.adapter.MainAdapter
import com.example.tvapplication.ui.main.viewmodel.MainViewModel
import com.example.tvapplication.ui.movie.view.MovieActivity
import com.example.tvapplication.utils.Status
import com.example.tvapplication.utils.hideKeyboard
import com.example.tvapplication.utils.toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: MainAdapter
    private var listMovies = ArrayList<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        setupViewModel()
        setupObserver()

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
                        if (movie.name!!.contains(query, true))
                            cursor.addRow(arrayOf(index, movie.name))
                        /*if (movie.contains(query, true))
                            cursor.addRow(arrayOf(index, movie))*/
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
                    if (movie.name == selection) {
                        goToMovieActivity(movie.assetExternalId!!)
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

    private fun setupUI() {
        recycler_movies.layoutManager = GridLayoutManager(this, 2)
        adapter = MainAdapter(arrayListOf())
        recycler_movies.addItemDecoration(
            DividerItemDecoration(
                recycler_movies.context,
                (recycler_movies.layoutManager as GridLayoutManager).orientation
            )
        )
        recycler_movies.adapter = adapter
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(ApiServiceImpl()))
        ).get(MainViewModel::class.java)
    }

    private fun setupObserver() {
        mainViewModel.getMovies().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data.let { movies ->
                        if (movies != null) {
                            renderList(movies)
                            listMovies = it.data!!
                            Log.e("MOVIES","listado de movies add-----$listMovies")
                        }
                    }
                    recycler_movies.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.GONE
                    recycler_movies.visibility = View.GONE
                }
                Status.ERROR -> {
                    progressBar.visibility = View.GONE
                    toast(it.message.toString())
                }
            }
        })
    }

    private fun renderList(movies: ArrayList<Movie>) {
        adapter.addData(movies)
        adapter.notifyDataSetChanged()
    }
}