package com.agungtriu.themeal.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agungtriu.themeal.data.Repository
import com.agungtriu.themeal.ui.main.filter.FilterModel
import com.agungtriu.themeal.utils.MealItem
import com.agungtriu.themeal.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _resultSearch = MutableLiveData<ViewState<List<MealItem>>>()
    val resultSearch: LiveData<ViewState<List<MealItem>>> get() = _resultSearch
    var key: String? = ""
    var filterModel: FilterModel = FilterModel()

    init {
        getSearch()
    }

    private var searchJob: Job? = null
    fun searchDebounced() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(1000L)
            getSearch()
        }
    }

    fun getSearch() {
        viewModelScope.launch {
            _resultSearch.postValue(ViewState.Loading)
            try {
                val result = repository.getSearch(key).meals
                if (!result.isNullOrEmpty()) {
                    val data = mutableListOf<MealItem>()
                    result.forEach { item ->
                        data.add(item.toMealEntity())
                    }
                    _resultSearch.postValue(ViewState.Success(data))
                } else {
                    throw NullPointerException("Data Not Found")
                }
            } catch (t: Throwable) {
                _resultSearch.postValue(ViewState.Error(t.message.toString()))
            }
        }
    }

    fun getFilter(area: String?, category: String?) {
        viewModelScope.launch {
            _resultSearch.postValue(ViewState.Loading)
            try {
                val result = repository.getFilter(area = area, category = category).meals
                if (!result.isNullOrEmpty()) {
                    val data = mutableListOf<MealItem>()
                    result.forEach { item ->
                        data.add(item.toMealEntity())
                    }
                    _resultSearch.postValue(ViewState.Success(data))
                } else {
                    throw NullPointerException("Data Not Found")
                }
            } catch (t: Throwable) {
                _resultSearch.postValue(ViewState.Error(t.message.toString()))
            }
        }
    }
}