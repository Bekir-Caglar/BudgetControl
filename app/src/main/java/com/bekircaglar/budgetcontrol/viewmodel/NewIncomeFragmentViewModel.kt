package com.bekircaglar.budgetcontrol.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bekircaglar.budgetcontrol.R
import com.bekircaglar.budgetcontrol.database.repo.BudgetDaoRepo
import com.bekircaglar.budgetcontrol.databinding.FragmentNewExpenseBinding
import com.bekircaglar.budgetcontrol.databinding.FragmentNewIncomeBinding
import com.bekircaglar.budgetcontrol.model.AccountsMoney
import com.bekircaglar.budgetcontrol.model.BankModel
import com.bekircaglar.budgetcontrol.model.CashModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewIncomeFragmentViewModel @Inject constructor(var repo: BudgetDaoRepo): ViewModel() {

    var categoryNameincomeM = MutableLiveData<String>()
    var AccountsMoneyListM = MutableLiveData<List<AccountsMoney>>()



    init {
        repo.getAccountsMoney()
        AccountsMoneyListM = repo.bringAccountsMoneyList()
        categoryNameincomeM = repo.bringCategoryIncomeNameM()


    }
    fun UpdateMoney(newBankMoney:String,newCashMoney:String){
        repo.updateAccountsMoney(newBankMoney,newCashMoney)

    }
    fun nullallcategoryincome(binding: FragmentNewIncomeBinding){
        repo.nullallcategoryincome(binding)

    }
    fun nullaccountall(binding: FragmentNewIncomeBinding){
        repo.nullaccountall(binding)

    }
    fun selectcategoryincome(binding: FragmentNewIncomeBinding) {
        repo.selectcategoryincome(binding)
    }

    fun addBankincomeList(incomeImg:Int,incomeDate:String,incomeBy:String,incomePrice:Int){
        repo.addBankincomeList(incomeImg,incomeDate, incomeBy, incomePrice)

    }
    fun addCashincomeList(incomeImg:Int,incomeDate:String,incomeBy:String,incomePrice:Int){
        repo.addCasgincomeList(incomeImg, incomeDate, incomeBy, incomePrice)

    }
    fun SelectIncomeBankAccountButton(binding: FragmentNewIncomeBinding) {

        repo.SelectIncomeBankAccountButton(binding)

    }

    fun SelectIncomeCashAccountButton(binding: FragmentNewIncomeBinding){

        repo.SelectIncomeCashAccountButton(binding)
    }




}