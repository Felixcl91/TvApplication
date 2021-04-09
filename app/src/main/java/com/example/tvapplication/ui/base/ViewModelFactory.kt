package com.example.tvapplication.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tvapplication.data.api.ApiHelper
import com.example.tvapplication.data.repository.MainRepository
import com.example.tvapplication.ui.main.viewmodel.MainViewModel
import com.example.tvapplication.ui.movie.viewmodel.MovieViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val apiHelper: ApiHelper): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(MainRepository(apiHelper)) as T

        } else if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            return MovieViewModel(MainRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}