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
import com.bekircaglar.budgetcontrol.model.CashExpenseModel
import com.bekircaglar.budgetcontrol.util.switch
import com.bekircaglar.budgetcontrol.viewmodel.NewExpenseFragmentViewModel
import com.google.firebase.auth.FirebaseAuth
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
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewExpenseBinding.inflate(layoutInflater,container,false)
        binding.newExpenseFragment = this
        expenseListBank = ArrayList<BankModel>()

        auth = FirebaseAuth.getInstance()

        viewModel.categoryNameM.observe(viewLifecycleOwner){
            categoryName = it
            getCatagoryImage()

        }
        viewModel.AccountsMoneyListM.observe(viewLifecycleOwner){
            var accountsMoneyList = it as ArrayList<AccountsMoney>
            if (accountsMoneyList.isEmpty().not()) {
                bankAccountMoney = accountsMoneyList[0].bankMoney
                cashAccountMoney = accountsMoneyList[0].cashMoney
            }
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
        val expensePrice = binding.expenseInput.text.toString()

        if (bankAccountType == "Bank" && categoryName != "Null"){

            val bankexpense = BankModel(0,categoryImg,expensePrice.toInt(),categoryName,date,auth.currentUser?.email.toString())

            viewModel.addBankexpenseList(bankexpense)
            val lastBankmoney = bankAccountMoney.toInt() - expensePrice.toInt()

            viewModel.UpdateMoney(cashAccountMoney,lastBankmoney.toString())

            val actiongobudget = AddFragmentDirections.actionAddFragmentToBudgetFragment()
            Navigation.findNavController(binding.root).navigate(actiongobudget)
        }
        if (bankAccountType == "Cash" && categoryName != "Null"){
            val cashexpense = CashExpenseModel(0,categoryImg,expensePrice.toInt(),categoryName,date,auth.currentUser?.email.toString())
            viewModel.addCashexpenseList(cashexpense)

            val lastCashMoney = cashAccountMoney.toInt() - expensePrice.toInt()
            viewModel.UpdateMoney(lastCashMoney.toString(),bankAccountMoney)

            val actiongobudget = AddFragmentDirections.actionAddFragmentToBudgetFragment()
            Navigation.findNavController(binding.root).navigate(actiongobudget)
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