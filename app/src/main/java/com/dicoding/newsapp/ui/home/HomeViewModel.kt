package com.dicoding.newsapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.newsapp.core.data.Resource
import com.dicoding.newsapp.core.domain.model.News
import com.dicoding.newsapp.core.domain.usecase.NewsUseCase
import com.dicoding.newsapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val newsUseCase: NewsUseCase): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<News>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<News>>> get() = _uiState

    init {
        viewModelScope.launch {
            getNews()
        }
    }

    suspend fun getNews() {
        _uiState.value = UiState.Loading
        newsUseCase.getNews().collect { resource ->
            _uiState.value = when (resource) {
                is Resource.Success -> UiState.Success(resource.data)
                is Resource.Error -> UiState.Error(resource.message.toString())
                is Resource.Loading -> UiState.Loading
            }
        }
    }

    suspend fun getNewsByTitle(title: String) {
        _uiState.value = UiState.Loading
        newsUseCase.getNewsByTitle(title).collect { resource ->
            _uiState.value = when (resource) {
                is Resource.Success -> UiState.Success(resource.data)
                is Resource.Error -> UiState.Error(resource.message.toString())
                is Resource.Loading -> UiState.Loading
            }
        }
    }
}