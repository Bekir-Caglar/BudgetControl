package com.bekircaglar.budgetcontrol.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bekircaglar.budgetcontrol.database.repo.BudgetDaoRepo
import com.bekircaglar.budgetcontrol.databinding.FragmentNewExpenseBinding
import com.bekircaglar.budgetcontrol.model.AccountsMoney
import com.bekircaglar.budgetcontrol.model.BankModel
import com.bekircaglar.budgetcontrol.model.CashExpenseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewExpenseFragmentViewModel @Inject constructor(var repo: BudgetDaoRepo):ViewModel() {
    var categoryNameM = MutableLiveData<String>()
    var AccountsMoneyListM = MutableLiveData<List<AccountsMoney>>()


    init {

        AccountsMoneyListM = repo.bringAccountsMoneyList()
        categoryNameM = repo.bringCagegoryNameM()


    }

    fun UpdateMoney(updatedCashMoney:String,updatedBankMoney:String){
        repo.updateAccountsMoneyByUser(updatedCashMoney, updatedBankMoney)

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
        repo.selectCategory(binding)
    }

    fun addBankexpenseList(bankExpense :BankModel){
        repo.addBankexpenseList(bankExpense)

    }
    fun addCashexpenseList(cashExpense:CashExpenseModel){
       repo.addCashexpenseList(cashExpense)

    }





}