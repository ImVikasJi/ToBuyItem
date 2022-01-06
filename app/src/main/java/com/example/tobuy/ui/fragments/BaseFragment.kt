package com.example.tobuy.ui.fragments

import ToBuyViewModelFactory
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tobuy.MainActivity
import com.example.tobuy.db.ItemDatabase
import com.example.tobuy.repository.ToBuyRepository
import com.example.tobuy.viewmodel.ToBuyViewModel

abstract class BaseFragment: Fragment() {
    protected val mainActivity: MainActivity
    get() = activity as MainActivity

    protected val itemDatabase: ItemDatabase
    get() =  ItemDatabase.invoke(requireContext())

    //protected val sharedToBuyViewModel: ToBuyViewModel by activityViewModels()
    protected val toBuyViewModel: ToBuyViewModel by viewModels {
        ToBuyViewModelFactory(ToBuyRepository(ItemDatabase.invoke(requireContext())))
}
//
//    }

    // region navigation helper methods
    protected fun navigateUp(){
        mainActivity.navController.navigateUp()
    }
    protected fun navigateViaNavGraph(actionId: Int){
        mainActivity.navController.navigate(actionId)
    }

    // End region navigation helper methods


}