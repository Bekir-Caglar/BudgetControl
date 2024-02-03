package com.bekircaglar.budgetcontrol.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.bekircaglar.budgetcontrol.R
import com.bekircaglar.budgetcontrol.adapter.BankAdapter
import com.bekircaglar.budgetcontrol.adapter.BankIncomeAdapter
import com.bekircaglar.budgetcontrol.adapter.CashAdapter
import com.bekircaglar.budgetcontrol.adapter.CashIncomeAdapter
import com.bekircaglar.budgetcontrol.databinding.FragmentBudgetBinding
import com.bekircaglar.budgetcontrol.model.AccountsMoney
import com.bekircaglar.budgetcontrol.model.BankIncomeModel
import com.bekircaglar.budgetcontrol.model.BankModel
import com.bekircaglar.budgetcontrol.model.CashIncomeModel
import com.bekircaglar.budgetcontrol.model.CashExpenseModel
import com.bekircaglar.budgetcontrol.viewmodel.BudgetFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class BudgetFragment : Fragment() {
 private lateinit var binding:FragmentBudgetBinding
    private lateinit var cashList : ArrayList<CashExpenseModel>
    private lateinit var cashAdapter: CashAdapter
    private lateinit var bankList:ArrayList<BankModel>
    private lateinit var bankAdapter: BankAdapter
    private lateinit var incomeBankAdapter: BankIncomeAdapter
    private lateinit var incomeBankList:ArrayList<BankIncomeModel>
    private lateinit var incomeCashAdapter: CashIncomeAdapter
    private lateinit var incomeCashList:ArrayList<CashIncomeModel>
    private lateinit var viewModel: BudgetFragmentViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_budget,container,false)
        binding.budgetFragment = this
        return binding.root

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : BudgetFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var bankAccountMoney = "0"
        var cashAccountMoney = "0"
        var accountsMoneyList = ArrayList<AccountsMoney>()
        cashList = ArrayList<CashExpenseModel>()
        bankList = ArrayList<BankModel>()
        incomeBankList = ArrayList<BankIncomeModel>()
        incomeCashList = ArrayList<CashIncomeModel>()

        viewModel.AccountsMoneyListM.observe(viewLifecycleOwner){
            accountsMoneyList = it as ArrayList<AccountsMoney>

            bankAccountMoney = accountsMoneyList[0].bankMoney
            cashAccountMoney = accountsMoneyList[0].cashMoney

            val totalMoney = bankAccountMoney.toInt() + cashAccountMoney.toInt()

            binding.totalBalance = totalMoney.toString()
            binding.bankAcc = bankAccountMoney
            binding.cashAcc = cashAccountMoney

        }

        viewModel.expenseListBankM.observe(viewLifecycleOwner){
            bankList = it as ArrayList<BankModel>
            val adapter = BankAdapter(viewModel,requireContext(),bankList)
            binding.bankRectycle.adapter = adapter

        }
        viewModel.expensesListCashM.observe(viewLifecycleOwner){
            cashList = it as ArrayList<CashExpenseModel>
            val adapter2 = CashAdapter(viewModel,requireContext(),cashList)
            binding.cashRecycle.adapter = adapter2

        }







    }

    fun expenseButton(){
        viewModel.goExpense(viewModel,binding,requireContext(),cashList,bankList)

    }
    fun incomeButton(){
        viewModel.goIncome(viewModel,binding,requireContext(),incomeCashList,incomeBankList)

        viewModel.incomeListCashM.observe(viewLifecycleOwner){
            incomeCashList = it as ArrayList<CashIncomeModel>
            val adapter3 = CashIncomeAdapter(viewModel,requireContext(),incomeCashList)
            binding.cashRecycle.adapter = adapter3
        }
        viewModel.incomeListBankM.observe(viewLifecycleOwner){
            incomeBankList = it as ArrayList<BankIncomeModel>
            val adapter4 = BankIncomeAdapter(viewModel,requireContext(),incomeBankList)
            binding.bankRectycle.adapter = adapter4
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllLists()

    }


}