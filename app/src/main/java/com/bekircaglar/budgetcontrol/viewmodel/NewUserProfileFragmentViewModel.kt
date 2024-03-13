package com.bekircaglar.budgetcontrol.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Insert
import com.bekircaglar.budgetcontrol.database.repo.BudgetDaoRepo
import com.bekircaglar.budgetcontrol.model.AccountsMoney
import com.bekircaglar.budgetcontrol.model.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewUserProfileFragmentViewModel@Inject constructor(var repo:BudgetDaoRepo): ViewModel(){

    var userDataList = MutableLiveData<List<UserData>>()
    init {
        getUserdata()
        userDataList = repo.bringUserDataList()

    }
    fun getUserdata(){
        repo.getUserData()
    }


    fun insertUserData(newUserData:UserData){
        repo.insertUserData(newUserData)
    }
    fun insertAccountMoney(accountsMoney: AccountsMoney){
        repo.insertAccountsMoney(accountsMoney)
    }


}