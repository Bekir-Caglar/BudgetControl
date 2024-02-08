package com.bekircaglar.budgetcontrol.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bekircaglar.budgetcontrol.database.repo.BudgetDaoRepo
import com.bekircaglar.budgetcontrol.model.AccountsMoney
import com.bekircaglar.budgetcontrol.model.BankIncomeModel
import com.bekircaglar.budgetcontrol.model.BankModel
import com.bekircaglar.budgetcontrol.model.CashIncomeModel
import com.bekircaglar.budgetcontrol.model.CashExpenseModel
import com.bekircaglar.budgetcontrol.model.DashboardData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardFragmentViewModel @Inject constructor(var repo: BudgetDaoRepo): ViewModel() {

    var AccountsMoneyListM = MutableLiveData<List<AccountsMoney>>()
    var expensesListCashM = MutableLiveData<List<CashExpenseModel>>()
    var expenseListBankM = MutableLiveData<List<BankModel>>()
    var incomeListCashM = MutableLiveData<List<CashIncomeModel>>()
    var incomeListBankM = MutableLiveData<List<BankIncomeModel>>()

    val combinedDataLiveData = MediatorLiveData<DashboardData>().apply {
        addSource(expenseListBankM) {
            value = DashboardData(
                it,
                expensesListCashM.value ?: emptyList(),
                incomeListBankM.value ?: emptyList(),
                incomeListCashM.value ?: emptyList()
            )
        }
        addSource(expensesListCashM) {
            value = DashboardData(
                expenseListBankM.value ?: emptyList(),
                it,
                incomeListBankM.value ?: emptyList(),
                incomeListCashM.value ?: emptyList()
            )
        }
        addSource(incomeListBankM) {
            value = DashboardData(
                expenseListBankM.value ?: emptyList(),
                expensesListCashM.value ?: emptyList(),
                it,
                incomeListCashM.value ?: emptyList()
            )
        }
        addSource(incomeListCashM) {
            value = DashboardData(
                expenseListBankM.value ?: emptyList(),
                expensesListCashM.value ?: emptyList(),
                incomeListBankM.value ?: emptyList(),
                it
            )
        }
    }


    init {
        getAllLists()
        AccountsMoneyListM = repo.bringAccountsMoneyList()
        incomeListCashM = repo.bringIncomeCashList()
        incomeListBankM = repo.bringIncomeBankList()
        expenseListBankM = repo.bringExpenseBankList()
        expensesListCashM = repo.bringExpenseCashList()
    }
    fun fetchCombinedData(){
        getAllLists()
        combinedDataLiveData.value = DashboardData(
            expenseListBankM.value ?: emptyList(),
            expensesListCashM.value ?: emptyList(),
            incomeListBankM.value ?: emptyList(),
            incomeListCashM.value ?: emptyList()
        )


    }

    fun getAllLists(){
        repo.getAccountsMoney()
        repo.getBankexpenseList()
        repo.getCashexpenseList()
        repo.getBankincomeList()
        repo.getCashincomeList()
    }

}