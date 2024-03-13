package com.bekircaglar.budgetcontrol.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bekircaglar.budgetcontrol.database.repo.BudgetDaoRepo
import com.bekircaglar.budgetcontrol.databinding.FragmentNewIncomeBinding
import com.bekircaglar.budgetcontrol.model.AccountsMoney
import com.bekircaglar.budgetcontrol.model.BankIncomeModel
import com.bekircaglar.budgetcontrol.model.CashIncomeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewIncomeFragmentViewModel @Inject constructor(var repo: BudgetDaoRepo): ViewModel() {

    var categoryNameincomeM = MutableLiveData<String>()
    var AccountsMoneyListM = MutableLiveData<List<AccountsMoney>>()



    init {
        AccountsMoneyListM = repo.bringAccountsMoneyList()
        categoryNameincomeM = repo.bringCategoryIncomeNameM()
    }
    fun updateAccountMoney(updatedCashMoney:String,updatedBankMoney:String){
        repo.updateAccountsMoneyByUser(updatedCashMoney,updatedBankMoney)
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

    fun addBankincomeList(bankincome:BankIncomeModel){
        repo.addBankincomeList(bankincome)

    }
    fun addCashincomeList(cashincome:CashIncomeModel){
        repo.addCasgincomeList(cashincome)

    }
    fun SelectIncomeBankAccountButton(binding: FragmentNewIncomeBinding) {

        repo.SelectIncomeBankAccountButton(binding)

    }

    fun SelectIncomeCashAccountButton(binding: FragmentNewIncomeBinding){

        repo.SelectIncomeCashAccountButton(binding)
    }




}