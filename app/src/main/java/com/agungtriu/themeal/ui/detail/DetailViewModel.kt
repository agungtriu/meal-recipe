package com.agungtriu.themeal.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agungtriu.themeal.data.Repository
import com.agungtriu.themeal.data.local.MealEntity
import com.agungtriu.themeal.data.remote.response.MealsItem
import com.agungtriu.themeal.ui.detail.DetailFragment.Companion.ID_KEY
import com.agungtriu.themeal.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: Repository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _resultDetail = MutableLiveData<ViewState<MealsItem>>()
    val resultDetail: LiveData<ViewState<MealsItem>> get() = _resultDetail

    private val _resultSelect = MutableLiveData<ViewState<MealEntity>>()
    val resultSelect: LiveData<ViewState<MealEntity>> get() = _resultSelect

    private val id: String = savedStateHandle[ID_KEY] ?: ""

    var isBookmark: Boolean = false
    private var mealEntity: MealEntity? = null


    init {
        getDetail()
        selectMeal()
    }

    fun getDetail() {
        _resultDetail.postValue(ViewState.Loading)
        viewModelScope.launch {
            try {
                val data = repository.getDetail(id).meals
                if (!data.isNullOrEmpty()) {
                    mealEntity = data[0].toMealEntity()
                    _resultDetail.postValue(ViewState.Success(data[0]))
                } else {
                    throw NullPointerException("null")
                }
            } catch (t: Throwable) {
                _resultDetail.postValue(ViewState.Error(t.message.toString()))
            }
        }
    }

    fun selectMeal() {
        _resultSelect.postValue(ViewState.Loading)
        viewModelScope.launch {
            try {
                repository.selectMeal(id).collect {
                    if (it != null) {
                        isBookmark = true
                        _resultSelect.postValue(ViewState.Success(it))
                    } else {
                        isBookmark = false
                        throw NullPointerException("null")
                    }
                }
            } catch (t: Throwable) {
                isBookmark = false
                _resultSelect.postValue(ViewState.Error(t.message.toString()))
            }
        }
    }

    private val _resultInsert = MutableLiveData<ViewState<String>>()
    val resultInsert: LiveData<ViewState<String>> get() = _resultInsert
    fun insertMeal() {
        _resultInsert.postValue(ViewState.Loading)
        viewModelScope.launch {
            try {
                if (mealEntity != null) {
                    repository.insertMeal(mealEntity!!)
                    _resultInsert.postValue(ViewState.Success("success"))
                } else {
                    throw NullPointerException("null")
                }
            } catch (t: Throwable) {
                _resultInsert.postValue(ViewState.Error(t.message.toString()))
            }
        }
    }

    private val _resultDelete = MutableLiveData<ViewState<String>>()
    val resultDelete: LiveData<ViewState<String>> get() = _resultDelete
    fun deleteMeal() {
        _resultDelete.postValue(ViewState.Loading)
        viewModelScope.launch {
            try {
                repository.deleteMeal(id)
                _resultDelete.postValue(ViewState.Success("success"))
            } catch (t: Throwable) {
                _resultDelete.postValue(ViewState.Error(t.message.toString()))
            }
        }
    }
}