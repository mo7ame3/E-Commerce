package com.example.e_commerce.screens.favorites

import androidx.lifecycle.ViewModel
import com.example.e_commerce.data.WrapperClass
import com.example.e_commerce.model.favorite.get.GetFavorite
import com.example.e_commerce.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    suspend fun getFavorite(authorization: String): WrapperClass<GetFavorite, Boolean, Exception> {
        return repository.getFavorite(authorization = authorization)
    }
}