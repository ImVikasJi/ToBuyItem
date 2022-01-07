package com.example.tobuy.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.epoxy.EpoxyTouchHelper
import com.example.tobuy.R
import com.example.tobuy.databinding.FragmentHomeBinding
import com.example.tobuy.model.ItemEntity
import com.example.tobuy.ui.epoxy.HomeEpoxyController

class HomeFragment : BaseFragment(), ItemEntityInterface {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {
            navigateViaNavGraph(R.id.action_homeFragment_to_addItemEntityFragment)
        }

        val controller = HomeEpoxyController(this)
        binding.epoxyRecyclerView.setController(controller) // to give charges to recyclerview

        toBuyViewModel.itemEntityLiveData.observe(viewLifecycleOwner) { itemEntityList ->
            controller.itemEntityList = itemEntityList as ArrayList<ItemEntity>
        }

        //Swipe to delete
        EpoxyTouchHelper.initSwiping(binding.epoxyRecyclerView)
            .right()
            .withTarget(HomeEpoxyController.ItemEntityEpoxyModel::class.java)
            .andCallbacks(object :
                EpoxyTouchHelper.SwipeCallbacks<HomeEpoxyController.ItemEntityEpoxyModel>() {
                override fun onSwipeCompleted(
                    model: HomeEpoxyController.ItemEntityEpoxyModel?,
                    itemView: View?,
                    position: Int,
                    direction: Int
                ) {
                    val itemToDelete = model?.itemEntity ?: return
                    toBuyViewModel.deleteItem(itemToDelete)
                }
            })

        // Drag the item
//        EpoxyTouchHelper.initDragging(controller)
//            .withRecyclerView(binding.epoxyRecyclerView)
//            .forVerticalList()
//            .withTarget(HomeEpoxyController.ItemEntityEpoxyModel::class.java)
//            .andCallbacks(object :
//                EpoxyTouchHelper.DragCallbacks<HomeEpoxyController.ItemEntityEpoxyModel>() {
//                override fun onModelMoved(
//                    fromPosition: Int,
//                    toPosition: Int,
//                    modelBeingMoved: HomeEpoxyController.ItemEntityEpoxyModel?,
//                    itemView: View?
//                ) {
//                    val remove = modelBeingMoved?.itemEntity ?: return
//                    // todo() drag and drop thing
//
//                }
//            })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        mainActivity.hideKeyboard(requireView())
    }

    override fun onDeleteItemEntity(itemEntity: ItemEntity) {
        toBuyViewModel.deleteItem(itemEntity)
    }

    override fun onBumpPriority(itemEntity: ItemEntity) {
        // todo()
    }
}