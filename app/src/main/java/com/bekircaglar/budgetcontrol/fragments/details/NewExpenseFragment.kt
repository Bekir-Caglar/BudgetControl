package com.bekircaglar.budgetcontrol.fragments.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.bekircaglar.budgetcontrol.R
import com.bekircaglar.budgetcontrol.databinding.FragmentNewExpenseBinding
import com.bekircaglar.budgetcontrol.fragments.main.AddFragmentDirections
import com.bekircaglar.budgetcontrol.model.AccountsMoney
import com.bekircaglar.budgetcontrol.model.BankModel
import com.bekircaglar.budgetcontrol.util.switch
import com.bekircaglar.budgetcontrol.viewmodel.NewExpenseFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.exp

@AndroidEntryPoint

class NewExpenseFragment : Fragment() {
    private lateinit var binding:FragmentNewExpenseBinding
    private  var categoryName:String = "Null"
    private  var bankAccountType:String = "Null"
    private var categoryImg:Int = 0
    private lateinit var expenseListBank:ArrayList<BankModel>
    private lateinit var viewModel:NewExpenseFragmentViewModel
    private var bankAccountMoney :String = "0"
    private var cashAccountMoney :String = "0"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewExpenseBinding.inflate(layoutInflater,container,false)
        binding.newExpenseFragment = this
        expenseListBank = ArrayList<BankModel>()

        viewModel.categoryNameM.observe(viewLifecycleOwner){
            categoryName = it
            getCatagoryImage()

        }
        viewModel.AccountsMoneyListM.observe(viewLifecycleOwner){
            var accountsMoneyList = it as ArrayList<AccountsMoney>

            bankAccountMoney = accountsMoneyList[0].bankMoney
            cashAccountMoney = accountsMoneyList[0].cashMoney
        }


        selectCatagory()

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempviewmodel:NewExpenseFragmentViewModel by viewModels()
        viewModel = tempviewmodel
    }



    fun confirmButton(){
        val date = "10.11.1938"
        val expensePrice = binding.editTextInput.text.toString()

        if (bankAccountType == null){
            bankAccountType = "Null"
        }
        if (bankAccountType == "Bank" && categoryName != "Null"){
            viewModel.addBankexpenseList(categoryImg,date,expensePrice.toInt(),categoryName)
            val actiongobudget = AddFragmentDirections.actionAddFragmentToBudgetFragment()
            Navigation.findNavController(binding.root).navigate(actiongobudget)

            var lastBankmoney = bankAccountMoney.toInt() - expensePrice.toInt()
            viewModel.UpdateMoney(lastBankmoney.toString(),cashAccountMoney)


        }
        if (bankAccountType == "Cash" && categoryName != "Null"){
            viewModel.addCashexpenseList(categoryImg,date,expensePrice.toInt(),categoryName)
            val actiongobudget = AddFragmentDirections.actionAddFragmentToBudgetFragment()
            Navigation.findNavController(binding.root).navigate(actiongobudget)

            var lastCashMoney = cashAccountMoney.toInt() - expensePrice.toInt()
            viewModel.UpdateMoney(bankAccountMoney,lastCashMoney.toString())

        }


    }

    fun getCatagoryImage(){

        if (categoryName == "Apart"){
            categoryImg = R.drawable.apartmenticon
        }
        else if(categoryName.equals("Beauty")){
            categoryImg = R.drawable.beautiyicon
        }
        else if(categoryName.equals("Car")){
            categoryImg = R.drawable.caricon
        }
        else if(categoryName.equals("Clothes")){
            categoryImg = R.drawable.clothesicon
        }
        else if(categoryName.equals("Beauty")){
            categoryImg = R.drawable.beautiyicon
        }
        else if(categoryName.equals("Pet")){
            categoryImg = R.drawable.peticon
        }
        else if(categoryName.equals("Donate")){
            categoryImg = R.drawable.donateicon
        }
        else if(categoryName.equals("Health")){
            categoryImg = R.drawable.healthicon
        }
        else if(categoryName.equals("Food")){
            categoryImg = R.drawable.foodicon
        }
        else if(categoryName.equals("Gift")){
            categoryImg = R.drawable.gifticon
        }
        else if(categoryName.equals("Other")){
            categoryImg = R.drawable.othericon
        }

    }

    fun selectBankAccountButton() {
        viewModel.SelectBankAccountButton(binding)
        bankAccountType = "Bank"
        println(bankAccountType)

    }

    fun selectCashAccountButton(){
        viewModel.SelectCashAccountButton(binding)
        bankAccountType = "Cash"
        println(bankAccountType)

    }
    fun selectCatagory(){
        viewModel.selectCategoryMV(binding)

    }



}