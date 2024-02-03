package com.bekircaglar.budgetcontrol.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bekircaglar.budgetcontrol.database.repo.BudgetDaoRepo
import com.bekircaglar.budgetcontrol.databinding.FragmentNewExpenseBinding
import com.bekircaglar.budgetcontrol.model.AccountsMoney
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewExpenseFragmentViewModel @Inject constructor(var repo: BudgetDaoRepo):ViewModel() {
    var categoryNameM = MutableLiveData<String>()
    var AccountsMoneyListM = MutableLiveData<List<AccountsMoney>>()


    init {
        repo.getAccountsMoney()
        AccountsMoneyListM = repo.bringAccountsMoneyList()
        categoryNameM = repo.bringCagegoryNameM()


    }

    fun UpdateMoney(newBankMoney:String,newCashMoney:String){
        repo.updateAccountsMoney(newBankMoney,newCashMoney)

    }

    fun NullAllImg(binding:FragmentNewExpenseBinding){
        repo.NullAllImg(binding)

    }

    fun SelectBankAccountButton(binding: FragmentNewExpenseBinding) {
        repo.SelectBankAccountButton(binding)

    }

    fun SelectCashAccountButton(binding: FragmentNewExpenseBinding){
        repo.SelectCashAccountButton(binding)

    }
    fun selectCategoryMV(binding: FragmentNewExpenseBinding){
        repo.selectCatagory(binding)
    }

    fun addBankexpenseList(exenseImg:Int,expenseDate:String,expensePrice:Int,expenseCategory:String){
        repo.addBankexpenseList(exenseImg,expenseDate,expensePrice,expenseCategory)

    }
    fun addCashexpenseList(exenseImg:Int,expenseDate:String,expensePrice:Int,expenseCategory:String){
       repo.addCashexpenseList(exenseImg,expenseDate,expensePrice,expenseCategory)

    }





}