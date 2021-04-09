package com.example.tvapplication.ui.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tvapplication.data.api.ListMovies
import com.example.tvapplication.data.model.Movie
import com.example.tvapplication.data.repository.MainRepository
import com.example.tvapplication.utils.Resource
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val movies = MutableLiveData<Resource<ArrayList<Movie>>>()
    private val compositeDisposable = CompositeDisposable()

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        movies.postValue(Resource.loading(null))
        compositeDisposable.add(
            mainRepository.getMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    movies.postValue(Resource.success(it.movies))
                           Log.d("MAIN", "list movies: $it")
                }, { throwable ->
                    Log.d("MAIN", "list movies error: $throwable")
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun getMovies(): LiveData<Resource<ArrayList<Movie>>> {
        return movies
    }
}



