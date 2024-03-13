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
import com.bekircaglar.budgetcontrol.model.CashIncomeModel
import com.bekircaglar.budgetcontrol.viewmodel.NewExpenseFragmentViewModel
import com.bekircaglar.budgetcontrol.viewmodel.NewIncomeFragmentViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint

class NewIncomeFragment : Fragment() {
    private lateinit var binding:FragmentNewIncomeBinding
    private var categoryNameIncome :String = "Null"
    private lateinit var viewModel:NewIncomeFragmentViewModel
    private var bankAccountType:String = "Null"
    private var categoryImg:Int ?= null
    private var bankAccountMoney :String = "0"
    private var cashAccountMoney :String = "0"
    private lateinit var auth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewIncomeBinding.inflate(layoutInflater,container,false)
        binding.newIncomeFragment = this

        auth = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.categoryNameincomeM.observe(viewLifecycleOwner){
            categoryNameIncome = it as String
            getCatagoryIncomeImage()
        }
        viewModel.AccountsMoneyListM.observe(viewLifecycleOwner){
            val accountsMoneyList = it as ArrayList<AccountsMoney>

            if (accountsMoneyList.isEmpty().not()) {
                bankAccountMoney = accountsMoneyList[0].bankMoney
                cashAccountMoney = accountsMoneyList[0].cashMoney
            }
        }



        selectcategoryincome()
    }


    fun confirmButton(){
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
        val current = LocalDate.now().format(formatter)

        val date = current.toString()
        val incomePrice = binding.editTextInput.text.toString()


        if (bankAccountType == "Bank" && categoryNameIncome != "Null"){
            val bankincome = BankIncomeModel(0,categoryImg!!,categoryNameIncome,date,incomePrice.toInt(),auth.currentUser?.email.toString())
            viewModel.addBankincomeList(bankincome)

            val lastBankmoney = bankAccountMoney.toInt() + incomePrice.toInt()
            viewModel.updateAccountMoney(cashAccountMoney,lastBankmoney.toString())

            Navigation.findNavController(binding.root).navigate(R.id.action_addFragment_to_budgetFragment)

        }
        if (bankAccountType == "Cash" && categoryNameIncome != "Null"){
            val cashincome = CashIncomeModel(0,categoryImg!!,categoryNameIncome,date,incomePrice.toInt(),auth.currentUser?.email.toString())
            viewModel.addCashincomeList(cashincome)
            Navigation.findNavController(binding.root).navigate(R.id.action_addFragment_to_budgetFragment)

            val lastCashMoney = cashAccountMoney.toInt() + incomePrice.toInt()
            viewModel.updateAccountMoney(lastCashMoney.toString(),bankAccountMoney)
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

    }
    fun selectCashAccountButton(){
        viewModel.SelectIncomeCashAccountButton(binding)
        bankAccountType = "Cash"

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