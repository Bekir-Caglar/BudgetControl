package com.bekircaglar.budgetcontrol.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bekircaglar.budgetcontrol.database.repo.BudgetDaoRepo
import com.bekircaglar.budgetcontrol.model.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileFragmentViewModel@Inject constructor(var repo: BudgetDaoRepo): ViewModel() {

    var userDataList = MutableLiveData<List<UserData>>()

    init {
        getUserdata()
        userDataList = repo.bringUserDataList()

    }
    fun getUserdata(){
        repo.getUserData()
    }

    fun updateUser(newUsername:String,newUserPhone:String,newUserImg:String,newUserDob:String){
        repo.updateUserData(newUserImg,newUsername,newUserPhone,newUserDob)
    }

}