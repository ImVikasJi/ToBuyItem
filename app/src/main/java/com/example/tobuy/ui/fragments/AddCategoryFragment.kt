package com.example.tobuy.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tobuy.databinding.FragmentAddCategoryBinding
import com.example.tobuy.model.CategoryEntity
import java.util.*

class AddCategoryFragment : BaseFragment() {

    private var _binding: FragmentAddCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.categoryNameEditText.requestFocus()
        mainActivity.showKeyBoard()
        binding.btnSaveCategory.setOnClickListener {
            saveCategoryToDatabase()
        }

        toBuyViewModel.transactionCompletedLiveData.observe(viewLifecycleOwner) { event ->
            event.getContent()?.let {
                navigateUp()
            }
        }
    }

    private fun saveCategoryToDatabase() {
        val categoryName = binding.categoryNameEditText.text.toString().trim()
        if (categoryName.isEmpty()) {
            binding.categoryNameTextField.error = "* Required field"
            return
        }

        val categoryEntity = CategoryEntity(
            id = UUID.randomUUID().toString(),
            name = categoryName
        )

        toBuyViewModel.insertCategory(categoryEntity)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}