package com.example.tobuy.ui.epoxy

import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.airbnb.epoxy.EpoxyController
import com.example.tobuy.R
import com.example.tobuy.databinding.ModelItemEntityBinding
import com.example.tobuy.model.ItemEntity
import com.example.tobuy.ui.fragments.ItemEntityInterface

class HomeEpoxyController(
    private val itemEntityInterface: ItemEntityInterface
) : EpoxyController() {

    var isLoading: Boolean = true
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    var itemEntityList = ArrayList<ItemEntity>()
        set(value) {
            field = value
            isLoading = false
            requestModelBuild()
        }

    override fun buildModels() {

        if (isLoading) {
            // todo
            return
        }

        if (itemEntityList.isEmpty()) {
            // todo
            return
        }

        itemEntityList.forEach { item ->
            ItemEntityEpoxyModel(item,itemEntityInterface).id(item.id).addTo(this)
        }
    }

    data class ItemEntityEpoxyModel(
        val itemEntity: ItemEntity,
        val itemEntityInterface: ItemEntityInterface
    ) : ViewBindingKotlinModel<ModelItemEntityBinding>(R.layout.model_item_entity) {
        override fun ModelItemEntityBinding.bind() {
            titleTextView.text = itemEntity.id

            if(itemEntity.description == null){
                descriptionTextView.isGone = true
            }else{
                descriptionTextView.isVisible = true
                descriptionTextView.text = itemEntity.description
            }

            deleteImageView.setOnClickListener {
                itemEntityInterface.onDeleteItemEntity(itemEntity)
            }
            priorityTextView.setOnClickListener {
                itemEntityInterface.onBumpPriority(itemEntity)
            }
        }

    }
}