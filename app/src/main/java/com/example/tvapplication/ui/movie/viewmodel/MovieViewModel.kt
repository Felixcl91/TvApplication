package com.example.tvapplication.ui.movie.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tvapplication.data.model.Movie
import com.example.tvapplication.data.model.ResponseItem
import com.example.tvapplication.data.repository.MainRepository
import com.example.tvapplication.ui.movie.view.MovieActivity
import com.example.tvapplication.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val movie = MutableLiveData<Resource<Movie>>()
    private val movies = MutableLiveData<Resource<ArrayList<ResponseItem?>?>>()

    fun fetchMovie(json_id: String, id_ext: String) {
        compositeDisposable.add(
                mainRepository.getMovie(json_id, id_ext)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            Log.e("Movie", it.toString())
                            movie.postValue(Resource.success(it.response))
                        }, { t ->
                            Log.e("Movie", "Error ${t.localizedMessage}")
                            Log.e("Movie", "Error cause ${t.cause}")
                            Log.e("Movie", "mesg ${t.message}")
                        })
        )
    }

    fun getMovie(): LiveData<Resource<Movie>> {
        return movie
    }

    fun fetchRecommendations(one: String, two: String, three: String,
                             four: String, five: Int, six: String, seven: String) {
        compositeDisposable.add(
                mainRepository.getRecommendations(one, two, three, four, five, six, seven)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            Log.e("REC", it.toString())
                            movies.postValue(Resource.success(it?.response))
                        }, {
                            t ->
                            Log.e("REC", "Error ${t.localizedMessage}")
                            Log.e("REC", "Error cause ${t.cause}")
                            Log.e("REC", "mesg ${t.message}")
                        })
        )
    }
    fun getMovies(): LiveData<Resource<ArrayList<ResponseItem?>?>> {
        return movies
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}