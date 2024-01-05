package com.agungtriu.themeal.ui.main.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agungtriu.themeal.data.Repository
import com.agungtriu.themeal.data.remote.response.AreaItem
import com.agungtriu.themeal.data.remote.response.CategoryItem
import com.agungtriu.themeal.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _resultArea = MutableLiveData<ViewState<List<AreaItem>>>()
    val resultArea: LiveData<ViewState<List<AreaItem>>> get() = _resultArea

    private val _resultCategory = MutableLiveData<ViewState<List<CategoryItem>>>()
    val resultCategory: LiveData<ViewState<List<CategoryItem>>> get() = _resultCategory

    init {
        getArea()
        getCategory()
    }


    private fun getArea() {
        _resultArea.postValue(ViewState.Loading)
        viewModelScope.launch {
            try {
                val result = repository.getAreas().meals
                if (!result.isNullOrEmpty()) {
                    _resultArea.postValue(ViewState.Success(result))
                } else {
                    throw NullPointerException("Data Not Found")
                }
            } catch (t: Throwable) {
                _resultArea.postValue(ViewState.Error(t.message.toString()))
            }
        }
    }

    private fun getCategory() {
        _resultCategory.postValue(ViewState.Loading)
        viewModelScope.launch {
            try {
                val result = repository.getCategories().meals
                if (!result.isNullOrEmpty()) {
                    _resultCategory.postValue(ViewState.Success(result))
                } else {
                    throw NullPointerException("Data Not Found")
                }
            } catch (t: Throwable) {
                _resultCategory.postValue(ViewState.Error(t.message.toString()))
            }
        }
    }
}