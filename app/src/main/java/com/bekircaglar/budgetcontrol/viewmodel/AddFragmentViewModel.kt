package com.bekircaglar.budgetcontrol.viewmodel

import android.view.View
import androidx.lifecycle.ViewModel
import com.bekircaglar.budgetcontrol.database.repo.BudgetDaoRepo
import com.bekircaglar.budgetcontrol.databinding.FragmentAddBinding
import com.bekircaglar.budgetcontrol.databinding.FragmentBudgetBinding
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddFragmentViewModel @Inject constructor(var repo: BudgetDaoRepo):ViewModel() {


}