package com.bekircaglar.budgetcontrol.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.bekircaglar.budgetcontrol.database.repo.BudgetDaoRepo
import com.bekircaglar.budgetcontrol.fragments.details.MorePageFragmentDirections
import com.bekircaglar.budgetcontrol.model.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentMorePageViewModel @Inject constructor(var repo: BudgetDaoRepo) :ViewModel() {


    var userDataList = MutableLiveData<List<UserData>>()

    init {
        getUserData()
        userDataList = repo.bringUserDataList()

    }

    fun getUserData(){
        repo.getUserData()
    }
    fun goProfilePage(view: View){
        repo.goProfilePageButton(view)
    }

    fun goHelpPage(view: View){
        repo.goHelpPageButton(view)
    }

    fun goAccount(view: View){
        repo.goAccountButton(view)
    }


}