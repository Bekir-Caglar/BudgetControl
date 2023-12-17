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
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
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

        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
        val current = LocalDate.now().format(formatter)

        val date = current.toString()
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
        categoryImg = when(categoryName) {
            "Apart" -> R.drawable.apartmenticon
            "Beauty" -> R.drawable.beautiyicon
            "Car" -> R.drawable.caricon
            "Clothes" -> R.drawable.clothesicon
            "Pet" -> R.drawable.peticon
            "Donate" -> R.drawable.donateicon
            "Health" -> R.drawable.healthicon
            "Food" -> R.drawable.foodicon
            "Gift" -> R.drawable.gifticon
            "Other" -> R.drawable.othericon
            else -> {R.drawable.othericon}
        }
    }

    fun selectBankAccountButton() {
        viewModel.SelectBankAccountButton(binding)
        bankAccountType = "Bank"

    }

    fun selectCashAccountButton(){
        viewModel.SelectCashAccountButton(binding)
        bankAccountType = "Cash"

    }
    fun selectCatagory(){
        viewModel.selectCategoryMV(binding)

    }



}