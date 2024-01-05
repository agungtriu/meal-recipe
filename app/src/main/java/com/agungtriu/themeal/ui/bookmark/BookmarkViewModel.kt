package com.agungtriu.themeal.ui.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agungtriu.themeal.data.Repository
import com.agungtriu.themeal.utils.MealItem
import com.agungtriu.themeal.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _resultSelect = MutableLiveData<ViewState<List<MealItem>>>()
    val resultSelect: LiveData<ViewState<List<MealItem>>> get() = _resultSelect

    init {
        selectMeals()
    }

    fun selectMeals() {
        _resultSelect.postValue(ViewState.Loading)
        viewModelScope.launch {
            try {
                repository.selectMeals().collect {
                    if (!it.isNullOrEmpty()) {
                        val data = mutableListOf<MealItem>()
                        it.forEach { item ->
                            data.add(item.toMealEntity())
                        }
                        _resultSelect.postValue(ViewState.Success(data))
                    } else {
                        throw NullPointerException("Data Not Found")
                    }
                }
            } catch (t: Throwable) {
                _resultSelect.postValue(ViewState.Error(t.message.toString()))
            }
        }
    }

    private val _resultDelete = MutableLiveData<ViewState<String>>()
    val resultDelete: LiveData<ViewState<String>> get() = _resultDelete

    fun deleteMeals(id: String) {
        _resultDelete.postValue(ViewState.Loading)
        viewModelScope.launch {
            try {
                repository.deleteMeal(id)
                _resultDelete.postValue(ViewState.Success("Success"))
                selectMeals()
            } catch (t: Throwable) {
                _resultDelete.postValue(ViewState.Error(t.message.toString()))
            }
        }
    }
}