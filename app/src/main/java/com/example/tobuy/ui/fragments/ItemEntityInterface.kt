package com.example.tobuy.ui.fragments

import com.example.tobuy.model.ItemEntity

interface ItemEntityInterface {

    fun onDeleteItemEntity(itemEntity: ItemEntity)
    fun onBumpPriority(itemEntity: ItemEntity)
}