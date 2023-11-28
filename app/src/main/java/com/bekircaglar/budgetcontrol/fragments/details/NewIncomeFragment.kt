package com.bekircaglar.budgetcontrol.fragments.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.bekircaglar.budgetcontrol.R
import com.bekircaglar.budgetcontrol.adapter.BankIncomeAdapter
import com.bekircaglar.budgetcontrol.databinding.FragmentAddBinding
import com.bekircaglar.budgetcontrol.databinding.FragmentNewIncomeBinding
import com.bekircaglar.budgetcontrol.model.AccountsMoney
import com.bekircaglar.budgetcontrol.model.BankIncomeModel
import com.bekircaglar.budgetcontrol.viewmodel.NewExpenseFragmentViewModel
import com.bekircaglar.budgetcontrol.viewmodel.NewIncomeFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class NewIncomeFragment : Fragment() {
    private lateinit var binding:FragmentNewIncomeBinding
    private var categoryNameIncome :String = "Null"
    private lateinit var viewModel:NewIncomeFragmentViewModel
    private var bankAccountType:String = "Null"
    private var categoryImg:Int ?= null
    private var bankAccountMoney :String = "0"
    private var cashAccountMoney :String = "0"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewIncomeBinding.inflate(layoutInflater,container,false)
        binding.newIncomeFragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.categoryNameincomeM.observe(viewLifecycleOwner){
            categoryNameIncome = it as String
            println(categoryNameIncome)
            getCatagoryIncomeImage()
        }
        viewModel.AccountsMoneyListM.observe(viewLifecycleOwner){
            var accountsMoneyList = it as ArrayList<AccountsMoney>

            bankAccountMoney = accountsMoneyList[0].bankMoney
            cashAccountMoney = accountsMoneyList[0].cashMoney
        }



        selectcategoryincome()
    }


    fun confirmButton(){
        val date = "10.11.1938"
        val expensePrice = binding.editTextInput.text.toString()

        if (bankAccountType == null){

        }
        if (bankAccountType == "Bank" && categoryNameIncome != "Null"){
            categoryImg?.let { viewModel.addBankincomeList(it,date,categoryNameIncome,expensePrice.toInt()) }
            Navigation.findNavController(binding.root).navigate(R.id.action_addFragment_to_budgetFragment)

            var lastBankmoney = bankAccountMoney.toInt() + expensePrice.toInt()
            viewModel.UpdateMoney(lastBankmoney.toString(),cashAccountMoney)
        }
        if (bankAccountType == "Cash" && categoryNameIncome != "Null"){
            categoryImg?.let { viewModel.addCashincomeList(it,date,categoryNameIncome,expensePrice.toInt()) }
            Navigation.findNavController(binding.root).navigate(R.id.action_addFragment_to_budgetFragment)

            var lastCashMoney = cashAccountMoney.toInt() + expensePrice.toInt()
            viewModel.UpdateMoney(bankAccountMoney,lastCashMoney.toString())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempviewmodel: NewIncomeFragmentViewModel by viewModels()
        viewModel = tempviewmodel

    }
    fun getCatagoryIncomeImage(){
        if (categoryNameIncome == "Salary"){
            categoryImg = R.drawable.salaryicon
        }
        else if(categoryNameIncome.equals("Income")){
            categoryImg = R.drawable.incomemoneycircle
        }
        else if(categoryNameIncome.equals("Savings")){
            categoryImg = R.drawable.savings
        }
        else if(categoryNameIncome.equals("Other")){
            categoryImg = R.drawable.othericon
        }

    }




    fun selectBankAccountButton() {
        viewModel.SelectIncomeBankAccountButton(binding)
        bankAccountType = "Bank"
        println(bankAccountType)

    }
    fun selectCashAccountButton(){
        viewModel.SelectIncomeCashAccountButton(binding)
        bankAccountType = "Cash"
        println(bankAccountType)

    }

    fun selectcategoryincome(){
        viewModel.selectcategoryincome(binding)

    }

    fun nullallcategoryincome(){
        viewModel.nullallcategoryincome(binding)

    }
    fun nullaccountall(){
       viewModel.nullaccountall(binding)

    }
}