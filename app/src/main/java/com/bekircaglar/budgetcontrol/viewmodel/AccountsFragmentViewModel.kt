package com.bekircaglar.budgetcontrol.viewmodel

import androidx.lifecycle.ViewModel
import com.bekircaglar.budgetcontrol.database.repo.BudgetDaoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountsFragmentViewModel @Inject constructor(var repo: BudgetDaoRepo):ViewModel() {


}