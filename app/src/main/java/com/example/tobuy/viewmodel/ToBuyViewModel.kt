package com.example.tobuy.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tobuy.model.ItemEntity
import com.example.tobuy.repository.ToBuyRepository
import kotlinx.coroutines.launch

class ToBuyViewModel(
    private val toBuyRepository: ToBuyRepository
) : ViewModel() {

    val itemEntityLiveData = MutableLiveData<List<ItemEntity>>()
    val transactionCompletedLiveData = MutableLiveData<Boolean>()

    init {
        viewModelScope.launch {
            toBuyRepository.getAllItems().collect{ items ->
                 itemEntityLiveData.postValue(items)
            }
        }
    }

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
}