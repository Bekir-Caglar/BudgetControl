package com.bekircaglar.budgetcontrol.viewmodel

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bekircaglar.budgetcontrol.database.repo.BudgetDaoRepo
import com.bekircaglar.budgetcontrol.databinding.FragmentBudgetBinding
import com.bekircaglar.budgetcontrol.model.AccountsMoney
import com.bekircaglar.budgetcontrol.model.BankIncomeModel
import com.bekircaglar.budgetcontrol.model.BankModel
import com.bekircaglar.budgetcontrol.model.CashIncomeModel
import com.bekircaglar.budgetcontrol.model.CashModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BudgetFragmentViewModel @Inject constructor(var repo: BudgetDaoRepo): ViewModel() {

    var expensesListCashM = MutableLiveData<List<CashModel>>()
    var expenseListBankM = MutableLiveData<List<BankModel>>()
    var incomeListCashM = MutableLiveData<List<CashIncomeModel>>()
    var incomeListBankM = MutableLiveData<List<BankIncomeModel>>()
    var AccountsMoneyListM = MutableLiveData<List<AccountsMoney>>()

    init {


        getAllLists()
        AccountsMoneyListM = repo.bringAccountsMoneyList()
        incomeListCashM = repo.bringIncomeCashList()
        incomeListBankM = repo.bringIncomeBankList()
        expenseListBankM = repo.bringExpenseBankList()
        expensesListCashM = repo.bringExpenseCashList()


    }

    fun goExpense(viewModel: BudgetFragmentViewModel,binding: FragmentBudgetBinding,context: Context,cashlist:ArrayList<CashModel>,bankList:ArrayList<BankModel>){
        repo.expenseButton(viewModel,binding,context,cashlist,bankList)

    }
    fun goIncome(viewModel:BudgetFragmentViewModel,binding: FragmentBudgetBinding,context: Context,incomeCashList:ArrayList<CashIncomeModel>,incomeBankList:ArrayList<BankIncomeModel>){
        repo.incomeButoon(viewModel,binding,context,incomeCashList,incomeBankList)

    }

    fun getAllLists(){
        repo.getAccountsMoney()
        repo.getBankexpenseList()
        repo.getCashexpenseList()
        repo.getBankincomeList()
        repo.getCashincomeList()
    }

    fun deleteBankexpenseList(bankexpense_id:Int){
        repo.deleteBankexpenseList(bankexpense_id)
    }
    fun deleteCashexpenseList(cashexpense_id:Int){
        repo.deleteCashexpenseList(cashexpense_id)

    }
    fun deleteBankincomeList(bankincome_id:Int){
        repo.deleteBankincomeList(bankincome_id)

    }
    fun deleteCashincomeList(cashincome_id:Int){
        repo.deleteCashincomeList(cashincome_id)

    }




}