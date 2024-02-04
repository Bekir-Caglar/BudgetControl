package com.bekircaglar.budgetcontrol.fragments.details

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.MediatorLiveData
import com.bekircaglar.budgetcontrol.R
import com.bekircaglar.budgetcontrol.databinding.FragmentDashboardBinding
import com.bekircaglar.budgetcontrol.model.BankModel
import com.bekircaglar.budgetcontrol.model.BankIncomeModel
import com.bekircaglar.budgetcontrol.model.CashExpenseModel
import com.bekircaglar.budgetcontrol.model.CashIncomeModel
import com.bekircaglar.budgetcontrol.model.ExpenseModel
import com.bekircaglar.budgetcontrol.model.IncomeModel
import com.bekircaglar.budgetcontrol.viewmodel.DashboardFragmentViewModel
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var viewModel: DashboardFragmentViewModel
    private var bankMoneyWrite: String = "0"
    private var cashMoneyWrite: String = "0"
    private var IncomeBudget: ArrayList<IncomeModel> = ArrayList()
    private var ExpenseBudget: ArrayList<ExpenseModel> = ArrayList()

    val mediatorIncomeLiveData = MediatorLiveData<Pair<List<BankIncomeModel>, List<CashIncomeModel>>>()
    var incomeListBankMData: List<BankIncomeModel>? = null
    var incomeListCashMData: List<CashIncomeModel>? = null

    val mediatorExpenseLiveData = MediatorLiveData<Pair<List<BankModel>, List<CashExpenseModel>>>()
    var expenseListBankMData: List<BankModel>? = null
    var expenseListCashMData: List<CashExpenseModel>? = null

    val categorySumMap = mutableMapOf<String, Float>()

    val piechartExpenseList: ArrayList<PieEntry> = ArrayList()
    val piechartIncomeList: ArrayList<PieEntry> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: DashboardFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() {
        setupAccountMoneyObserver()
        setupExpenseObserver()
        setupIncomeObserver()
    }

    private fun setupAccountMoneyObserver() {
        viewModel.AccountsMoneyListM.observe(viewLifecycleOwner) {
            bankMoneyWrite = it[0].bankMoney
            cashMoneyWrite = it[0].cashMoney
            updatePieChartMain()
            updateTotalMoney()
            
        }
    }

    private fun updatePieChartMain() {
        val piechartList: ArrayList<PieEntry> = ArrayList()
        piechartList.add(PieEntry(bankMoneyWrite.toFloat(), "Bank"))
        piechartList.add(PieEntry(cashMoneyWrite.toFloat(), "Cash"))
        val pieDataSetMain = createPieDataSet(piechartList)
        val pieData = PieData(pieDataSetMain)
        binding.piechartMain.apply {
            setEntryLabelColor(R.color.dark_text_color)
            center
            data = pieData
            description.text = ""
            animateY(2000)
            isDrawHoleEnabled = false
        }
    }

    private fun updateTotalMoney() {
        val totalMoney = bankMoneyWrite.toFloat() + cashMoneyWrite.toFloat()
        binding.totalMoney.text = ("$totalMoney €").toString()
        binding.bankMoney.text = bankMoneyWrite
        binding.cashMoney.text = cashMoneyWrite
    }

    private fun createPieDataSet(piechartList: ArrayList<PieEntry>): PieDataSet {
        val pieDataSet = PieDataSet(piechartList, "")
        pieDataSet.setColors(
            Color.rgb(213, 235, 254),
            Color.rgb(204, 241, 229),
            Color.rgb(254, 224, 209),
            Color.rgb(241, 144, 109),
            Color.rgb(226, 71, 15),
            Color.rgb(120, 218, 117),
            Color.rgb(12, 76, 11),
            255
        )
        pieDataSet.valueTextSize = 8f
        pieDataSet.xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        pieDataSet.yValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE
        pieDataSet.sliceSpace = 3f
        pieDataSet.valueTextColor = R.color.dark_text_color
        pieDataSet.valueLineColor = Color.TRANSPARENT
        return pieDataSet
    }

    private fun setupExpenseObserver() {
        mediatorExpenseLiveData.addSource(viewModel.expenseListBankM) {
            expenseListBankMData = it
            val cashData = expenseListCashMData
            if (cashData != null) {
                mediatorExpenseLiveData.value = Pair(it, cashData)
            }
        }
        mediatorExpenseLiveData.addSource(viewModel.expensesListCashM) {
            expenseListCashMData = it
            val bankData = expenseListBankMData
            if (bankData != null) {
                mediatorExpenseLiveData.value = Pair(bankData, it)
            }
        }
        mediatorExpenseLiveData.observe(viewLifecycleOwner) { (bankList, cashList) ->
            ExpenseBudget.clear()
            categorySumMap.clear()
            piechartExpenseList.clear()

            for (bank in bankList) {
                for (cash in cashList) {
                    if (bank.expenseCatagory == cash.expenseCatagory) {
                        val total = bank.expensePrice.toFloat() + cash.expensePrice.toFloat()
                        categorySumMap[bank.expenseCatagory] = categorySumMap.getOrDefault(bank.expenseCatagory, 0f) + total
                        ExpenseBudget.add(bank)
                    }
                }
            }

            for (bank in bankList) {
                if (bank.expenseCatagory !in categorySumMap.keys) {
                    categorySumMap[bank.expenseCatagory] = bank.expensePrice.toFloat()
                    ExpenseBudget.add(bank)
                }
            }
            for (cash in cashList) {
                if (cash.expenseCatagory !in categorySumMap.keys) {
                    categorySumMap[cash.expenseCatagory] = cash.expensePrice.toFloat()
                    ExpenseBudget.add(cash)
                }
            }

            for ((category, sum) in categorySumMap) {
                piechartExpenseList.add(PieEntry(sum, category))
            }

            val pieDataSetExpenses = createPieDataSet(piechartExpenseList)
            val pieData = PieData(pieDataSetExpenses)

            binding.piechartSpent.apply {
                setEntryLabelColor(R.color.dark_text_color)
                center
                data = pieData
                description.text = ""
                animateY(2000)
                isDrawHoleEnabled = false
                setEntryLabelTextSize(8f)
            }
        }
    }

    private fun setupIncomeObserver() {
        mediatorIncomeLiveData.addSource(viewModel.incomeListBankM) {
            incomeListBankMData = it
            val cashData = incomeListCashMData
            if (cashData != null) {
                mediatorIncomeLiveData.value = Pair(it, cashData)
            }
        }
        mediatorIncomeLiveData.addSource(viewModel.incomeListCashM) {
            incomeListCashMData = it
            val bankData = incomeListBankMData
            if (bankData != null) {
                mediatorIncomeLiveData.value = Pair(bankData, it)
            }
        }

        mediatorIncomeLiveData.observe(viewLifecycleOwner) { (bankList, cashList) ->
            IncomeBudget.clear()
            categorySumMap.clear()
            piechartIncomeList.clear()

            for (bank in bankList) {
                for (cash in cashList) {
                    if (bank.incomeBy == cash.incomeBy) {
                        val total = bank.incomePrice.toFloat() + cash.incomePrice.toFloat()
                        categorySumMap[bank.incomeBy] = categorySumMap.getOrDefault(bank.incomeBy, 0f) + total
                        IncomeBudget.add(bank)
                    }
                }
            }

            for (bank in bankList) {
                if (bank.incomeBy !in categorySumMap.keys) {
                    categorySumMap[bank.incomeBy] = bank.incomePrice.toFloat()
                    IncomeBudget.add(bank)
                }
            }

            for (cash in cashList) {
                if (cash.incomeBy !in categorySumMap.keys) {
                    categorySumMap[cash.incomeBy] = cash.incomePrice.toFloat()
                    IncomeBudget.add(cash)
                }
            }

            for ((category, sum) in categorySumMap) {
                piechartIncomeList.add(PieEntry(sum, category))
            }

            val pieDataSetIncome = createPieDataSet(piechartIncomeList)
            val pieData = PieData(pieDataSetIncome)

            binding.piechartEarn.apply {

                setEntryLabelColor(R.color.dark_text_color)
                center
                data = pieData
                description.text = ""
                animateY(2000)
                isDrawHoleEnabled = false
                setEntryLabelTextSize(8f)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllLists()
    }
}