package com.example.tobuy.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tobuy.model.CategoryEntity
import com.example.tobuy.model.ItemEntity
import com.example.tobuy.repository.ToBuyRepository
import kotlinx.coroutines.launch

class ToBuyViewModel(
    private val toBuyRepository: ToBuyRepository
) : ViewModel() {

    val itemEntityLiveData = MutableLiveData<List<ItemEntity>>()
    val categoryEntityLiveData = MutableLiveData<List<CategoryEntity>>()
    val transactionCompletedLiveData = MutableLiveData<Boolean>()

    init {
        viewModelScope.launch {
            toBuyRepository.getAllItems().collect { items ->
                itemEntityLiveData.postValue(items)
            }
        }

        viewModelScope.launch {
            toBuyRepository.getAllCategories().collect { category ->
                categoryEntityLiveData.postValue(category)
            }
        }
    }

    // start region ItemEntity
    fun insertItem(itemEntity: ItemEntity) {
        viewModelScope.launch {
            toBuyRepository.insertItem(itemEntity)
        }
        transactionCompletedLiveData.postValue(true)
    }

    fun deleteItem(itemEntity: ItemEntity) {
        viewModelScope.launch {
            toBuyRepository.deleteItem(itemEntity)
        }
    }

    fun updateItem(itemEntity: ItemEntity) {
        viewModelScope.launch {
            toBuyRepository.updateItem(itemEntity)
            transactionCompletedLiveData.postValue(true)
        }
    }
    // end region ItemEntity

    // start region CategoryEntity
    fun insertCategory(categoryEntity: CategoryEntity) {
        viewModelScope.launch {
            toBuyRepository.insertCategory(categoryEntity)
        }
        transactionCompletedLiveData.postValue(true)
    }

    fun deleteCategory(categoryEntity: CategoryEntity) {
        viewModelScope.launch {
            toBuyRepository.deleteCategory(categoryEntity)
        }
    }

    fun updateCategory(categoryEntity: CategoryEntity) {
        viewModelScope.launch {
            toBuyRepository.updateCategory(categoryEntity)
            transactionCompletedLiveData.postValue(true)
        }
    }
    // end region CategoryEntity
}