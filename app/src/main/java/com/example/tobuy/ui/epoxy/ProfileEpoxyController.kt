package com.example.tobuy.ui.epoxy

import android.view.View
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyModel
import com.example.tobuy.R
import com.example.tobuy.addHeaderModel
import com.example.tobuy.databinding.ModelCategoryBinding
import com.example.tobuy.databinding.ModelEmptyButtonBinding
import com.example.tobuy.db.CategoryEntityDao
import com.example.tobuy.model.CategoryEntity

class ProfileEpoxyController(
    private val onCategoryEmptyStateClicked: () -> Unit
) : EpoxyController() {

    var categories: List<CategoryEntity> = emptyList()
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        addHeaderModel("Categories")
        // Loop through every Category in categories in the list
        categories.forEach { item ->
            CategoryEpoxyModel(item).id(item.id).addTo(this)
        }

        EmptyButtonEpoxyModel("Add Category", onCategoryEmptyStateClicked)
            .id("add_category")
            .addTo(this)
    }

    data class CategoryEpoxyModel(val categoryEntity: CategoryEntity) :
        ViewBindingKotlinModel<ModelCategoryBinding>(R.layout.model_category) {
        override fun ModelCategoryBinding.bind() {
            tvCategoryTextView.text = categoryEntity.name
        }

    }

    data class EmptyButtonEpoxyModel(
        val buttonText: String,
        val onClicked: () -> Unit
    ) : ViewBindingKotlinModel<ModelEmptyButtonBinding>(R.layout.model_empty_button){
        override fun ModelEmptyButtonBinding.bind() {
            btnModelEmptyButton.text = buttonText
            btnModelEmptyButton.setOnClickListener {
                onClicked.invoke()
            }
        }

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }
    }
}