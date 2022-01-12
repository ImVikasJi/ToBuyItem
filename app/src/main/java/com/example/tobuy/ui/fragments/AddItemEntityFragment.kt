package com.example.tobuy.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.tobuy.R
import com.example.tobuy.databinding.FragmentAddItemEntityBinding
import com.example.tobuy.model.ItemEntity
import java.util.*

class AddItemEntityFragment : BaseFragment() {

    private var _binding: FragmentAddItemEntityBinding? = null
    private val binding get() = _binding!!

    private var isInEditMode: Boolean = false

    private val safeArgs: AddItemEntityFragmentArgs by navArgs()
    private val selectedItemEntity: ItemEntity? by lazy {
        toBuyViewModel.itemEntityLiveData.value?.find {
            it.id == safeArgs.selectedItemEntityId
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddItemEntityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener {
            saveItemEntityToDatabase()
        }

        binding.quantitySeekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val currentText = binding.titleEditText.text.toString().trim()
                if (currentText.isEmpty()) {
                    return
                }
                val startindex = currentText.indexOf("<") -1

                val newText = if(startindex > 0){
                    "${currentText.substring(0,startindex)} <$progress>"
                }else{
                    "$currentText <$progress>"
                }
                val sanitizedText = newText.replace("<1>","")
                binding.titleEditText.setText(sanitizedText)
                binding.titleEditText.setSelection(sanitizedText.length)

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                //
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                //
            }

        })

        toBuyViewModel.transactionCompletedLiveData.observe(viewLifecycleOwner) { complete ->
            if (complete) {

                if (isInEditMode) {  // this will help avoiding duplicate items
                    navigateUp()
                    return@observe
                }
                Toast.makeText(requireContext(), "Item Saved!", Toast.LENGTH_SHORT).show()
                binding.titleEditText.text = null
                binding.titleEditText.requestFocus()
                mainActivity.showKeyBoard()
                binding.descriptionEditText.text = null
                binding.radioGroup.check(R.id.radioButtonLow)
            }
        }
        // Show keyboard as default and focus on title
        mainActivity.showKeyBoard()
        binding.titleEditText.requestFocus()

        selectedItemEntity?.let { itemEntity ->
            isInEditMode = true
            binding.titleEditText.setText(itemEntity.title)
            binding.titleEditText.setSelection(itemEntity.title.length)
            binding.descriptionEditText.setText(itemEntity.description)
            when (itemEntity.priority) {
                1 -> binding.radioGroup.check(R.id.radioButtonLow)
                2 -> binding.radioGroup.check(R.id.radioButtonMedium)
                else -> binding.radioGroup.check(R.id.radioButtonHigh)
            }

            binding.saveButton.text = "Update"
            mainActivity.supportActionBar?.title = "Update item"
        }
    }

    override fun onPause() {
        super.onPause()
        toBuyViewModel.transactionCompletedLiveData.postValue(false)
    }

    private fun saveItemEntityToDatabase() {
        val itemTitle = binding.titleEditText.text.toString().trim()
        if (itemTitle.isEmpty()) {
            binding.titleTextField.error = "Required Field"
            return
        }
        binding.titleTextField.error = null

        var itemDescription: String? = binding.descriptionEditText.text.toString().trim()
        val itemPriority = when (binding.radioGroup.checkedRadioButtonId) {
            R.id.radioButtonLow -> 1
            R.id.radioButtonMedium -> 2
            R.id.radioButtonHigh -> 3
            else -> 0
        }

        if (isInEditMode) {
            val itemEntity = selectedItemEntity!!.copy(
                title = itemTitle,
                priority = itemPriority,
                description = itemDescription

            )

            toBuyViewModel.updateItem(itemEntity)
            return
        }

        val itemEntity = ItemEntity(
            id = UUID.randomUUID().toString(),
            title = itemTitle,
            description = itemDescription,
            priority = itemPriority,
            createdAt = System.currentTimeMillis(),
            category = ""//todo
        )
        toBuyViewModel.insertItem(itemEntity)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}