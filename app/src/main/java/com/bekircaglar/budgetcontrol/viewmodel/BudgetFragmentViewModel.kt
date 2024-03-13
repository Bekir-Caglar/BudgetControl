package com.bekircaglar.budgetcontrol.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bekircaglar.budgetcontrol.database.repo.BudgetDaoRepo
import com.bekircaglar.budgetcontrol.databinding.FragmentBudgetBinding
import com.bekircaglar.budgetcontrol.model.AccountsMoney
import com.bekircaglar.budgetcontrol.model.BankIncomeModel
import com.bekircaglar.budgetcontrol.model.BankModel
import com.bekircaglar.budgetcontrol.model.CashIncomeModel
import com.bekircaglar.budgetcontrol.model.CashExpenseModel
import com.bekircaglar.budgetcontrol.model.UserData
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BudgetFragmentViewModel @Inject constructor(var repo: BudgetDaoRepo): ViewModel() {


    var expensesListCashM = MutableLiveData<List<CashExpenseModel>>()
    var expenseListBankM = MutableLiveData<List<BankModel>>()
    var incomeListCashM = MutableLiveData<List<CashIncomeModel>>()
    var incomeListBankM = MutableLiveData<List<BankIncomeModel>>()
    var AccountsMoneyListM = MutableLiveData<List<AccountsMoney>>()
    var userDataList = MutableLiveData<List<UserData>>()

    init {
        userDataList = repo.bringUserDataList()
        getAllListByUser()
        updateData()
    }

    fun goExpense(viewModel: BudgetFragmentViewModel, binding: FragmentBudgetBinding, context: Context, cashlist:ArrayList<CashExpenseModel>, bankList:ArrayList<BankModel>){
        repo.expenseButton(viewModel,binding,context,cashlist,bankList)

    }
    fun goIncome(viewModel:BudgetFragmentViewModel,binding: FragmentBudgetBinding,context: Context,incomeCashList:ArrayList<CashIncomeModel>,incomeBankList:ArrayList<BankIncomeModel>){
        repo.incomeButoon(viewModel,binding,context,incomeCashList,incomeBankList)

    }

    fun updateData(){
        AccountsMoneyListM = repo.bringAccountsMoneyList()
        incomeListCashM = repo.bringIncomeCashList()
        incomeListBankM = repo.bringIncomeBankList()
        expenseListBankM = repo.bringExpenseBankList()
        expensesListCashM = repo.bringExpenseCashList()
    }

    fun getUserData(){
        repo.getUserData()
    }
    fun getAllListByUser(){
            repo.getAccountsMoneyByUser()
            repo.getBankexpenseListByUser()
            repo.getCashexpenseListByUser()
            repo.getBankincomeListByUser()
            repo.getCashincomeListByUser()

    }





}