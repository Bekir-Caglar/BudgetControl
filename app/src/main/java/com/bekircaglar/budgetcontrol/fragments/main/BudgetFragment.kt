package com.bekircaglar.budgetcontrol.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
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
import com.bekircaglar.budgetcontrol.model.UserData
import com.bekircaglar.budgetcontrol.viewmodel.BudgetFragmentViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
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
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient : GoogleSignInClient


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_budget,container,false)
        binding.budgetFragment = this
        requireActivity() as AppCompatActivity
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.VISIBLE

        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.clientId))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)



        return binding.root

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : BudgetFragmentViewModel by viewModels()
        viewModel = tempViewModel
        viewModel.getAllListByUser()
        auth = FirebaseAuth.getInstance()
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
        viewModel.updateData()
        viewModel.getAllListByUser()

        viewModel.AccountsMoneyListM.observe(viewLifecycleOwner){
            accountsMoneyList = it as ArrayList<AccountsMoney>

            if (accountsMoneyList.isEmpty().not()) {
                bankAccountMoney = accountsMoneyList[0].bankMoney
                cashAccountMoney = accountsMoneyList[0].cashMoney
            }
            val totalMoney = bankAccountMoney.toInt() + cashAccountMoney.toInt()

            binding.totalBalance = totalMoney.toString()
            binding.bankAcc = bankAccountMoney
            binding.cashAcc = cashAccountMoney

        }

        viewModel.expenseListBankM.observe(viewLifecycleOwner){
            println("burası çalıştı")
            bankList.clear()
            bankList = it as ArrayList<BankModel>
            println(bankList.joinToString())
            val adapter = BankAdapter(viewModel, requireContext(), bankList)
            adapter.notifyDataSetChanged()
            binding.bankRectycle.adapter = adapter

        }
        viewModel.expensesListCashM.observe(viewLifecycleOwner){
            cashList.clear()
            cashList = it as ArrayList<CashExpenseModel>
            val adapter2 = CashAdapter(viewModel,requireContext(),cashList)
            adapter2.notifyDataSetChanged()
            binding.cashRecycle.adapter = adapter2


        }
        viewModel.userDataList.observe(viewLifecycleOwner){
            val userDataList = it as ArrayList<UserData>
            if (userDataList.isEmpty().not()) {
                binding.toolbar.title = userDataList[0].user_name
            }

        }

        binding.toolbar.setOnClickListener {
            auth.signOut()
            googleSignInClient.signOut()
            Toast.makeText(requireContext(),"Logged out",Toast.LENGTH_SHORT).show()
            val actionlogout = BudgetFragmentDirections.actionBudgetFragmentToLoginFragment()
            Navigation.findNavController(view).navigate(actionlogout)


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
            adapter3.notifyDataSetChanged()
            binding.cashRecycle.adapter = adapter3
        }
        viewModel.incomeListBankM.observe(viewLifecycleOwner){
            incomeBankList = it as ArrayList<BankIncomeModel>
            val adapter4 = BankIncomeAdapter(viewModel,requireContext(),incomeBankList)
            adapter4.notifyDataSetChanged()
            binding.bankRectycle.adapter = adapter4
        }
    }

    override fun onResume() {
        super.onResume()
        println("onResume() called from BudgetFragment.kt")
        viewModel.getAllListByUser()
        viewModel.updateData()
    }




}