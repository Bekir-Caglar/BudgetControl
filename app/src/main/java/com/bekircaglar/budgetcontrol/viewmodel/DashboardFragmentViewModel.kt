package com.bekircaglar.budgetcontrol.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bekircaglar.budgetcontrol.database.repo.BudgetDaoRepo
import com.bekircaglar.budgetcontrol.model.AccountsMoney
import com.bekircaglar.budgetcontrol.model.BankIncomeModel
import com.bekircaglar.budgetcontrol.model.BankModel
import com.bekircaglar.budgetcontrol.model.CashIncomeModel
import com.bekircaglar.budgetcontrol.model.CashModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardFragmentViewModel @Inject constructor(var repo: BudgetDaoRepo): ViewModel() {

    var AccountsMoneyListM = MutableLiveData<List<AccountsMoney>>()
    var expensesListCashM = MutableLiveData<List<CashModel>>()
    var expenseListBankM = MutableLiveData<List<BankModel>>()
    var incomeListCashM = MutableLiveData<List<CashIncomeModel>>()
    var incomeListBankM = MutableLiveData<List<BankIncomeModel>>()


    init {
        getAllLists()
        AccountsMoneyListM = repo.bringAccountsMoneyList()
        incomeListCashM = repo.bringIncomeCashList()
        incomeListBankM = repo.bringIncomeBankList()
        expenseListBankM = repo.bringExpenseBankList()
        expensesListCashM = repo.bringExpenseCashList()
    }

    fun getAllLists(){
        repo.getAccountsMoney()
        repo.getBankexpenseList()
        repo.getCashexpenseList()
        repo.getBankincomeList()
        repo.getCashincomeList()
    }

}