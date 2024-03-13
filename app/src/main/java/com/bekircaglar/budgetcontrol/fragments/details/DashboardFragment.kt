package com.bekircaglar.budgetcontrol.fragments.details

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.bekircaglar.budgetcontrol.R
import com.bekircaglar.budgetcontrol.databinding.FragmentDashboardBinding
import com.bekircaglar.budgetcontrol.model.BankModel
import com.bekircaglar.budgetcontrol.model.BankIncomeModel
import com.bekircaglar.budgetcontrol.model.CashExpenseModel
import com.bekircaglar.budgetcontrol.model.CashIncomeModel
import com.bekircaglar.budgetcontrol.model.DashboardData
import com.bekircaglar.budgetcontrol.model.ExpenseModel
import com.bekircaglar.budgetcontrol.model.IncomeModel
import com.bekircaglar.budgetcontrol.model.MonthAxisValueFormatter
import com.bekircaglar.budgetcontrol.viewmodel.DashboardFragmentViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var viewModel: DashboardFragmentViewModel
    private var bankMoneyWrite: String = "0"
    private var cashMoneyWrite: String = "0"
    private var IncomeBudget: ArrayList<IncomeModel> = ArrayList()
    private var ExpenseBudget: ArrayList<ExpenseModel> = ArrayList()
    private lateinit var auth : FirebaseAuth
    private lateinit var lineChart: LineChart


    val mediatorIncomeLiveData =
        MediatorLiveData<Pair<List<BankIncomeModel>, List<CashIncomeModel>>>()
    var incomeListBankMData: List<BankIncomeModel>? = null
    var incomeListCashMData: List<CashIncomeModel>? = null

    val mediatorExpenseLiveData = MediatorLiveData<Pair<List<BankModel>, List<CashExpenseModel>>>()
    var expenseListBankMData: List<BankModel>? = null
    var expenseListCashMData: List<CashExpenseModel>? = null

    val categorySumMap = mutableMapOf<String, Float>()

    val piechartExpenseList: ArrayList<PieEntry> = ArrayList()
    val piechartIncomeList: ArrayList<PieEntry> = ArrayList()

    val formatter = DateTimeFormatter.ofPattern("MMMM")
    val current = LocalDate.now().format(formatter)
    val date = current.toString()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDashboardBinding.inflate(layoutInflater, container, false)
        auth = FirebaseAuth.getInstance()


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
        viewModel.fetchCombinedData(auth.currentUser?.email.toString())

    }

    private fun setupObservers() {
        setupAccountMoneyObserver()
        setupExpenseObserver()
        setupIncomeObserver()
        setupLineChart()
    }

    private fun setupAccountMoneyObserver() {
        viewModel.AccountsMoneyListM.observe(viewLifecycleOwner) {
            if (it.isEmpty().not()) {
                bankMoneyWrite = it[0].bankMoney
                cashMoneyWrite = it[0].cashMoney
            }
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

                    var comingMonthCash = cash.expenseDate.split(" ")[1]
                    var comingMonthBank = bank.expenseDate.split(" ")[1]
                    if (date.equals(comingMonthCash) && date.equals(comingMonthBank)) {
                        if (bank.expenseCatagory == cash.expenseCatagory) {
                            val total = bank.expensePrice.toFloat() + cash.expensePrice.toFloat()
                            categorySumMap[bank.expenseCatagory] =
                                categorySumMap.getOrDefault(bank.expenseCatagory, 0f) + total
                            ExpenseBudget.add(bank)
                        }
                    }
                }

            }

            for (bank in bankList) {
                var comingMonthBank = bank.expenseDate.split(" ")[1]
                if (date == comingMonthBank) {
                    if (bank.expenseCatagory !in categorySumMap.keys) {
                        categorySumMap[bank.expenseCatagory] = bank.expensePrice.toFloat()
                        ExpenseBudget.add(bank)
                    }
                }
            }
            for (cash in cashList) {
                var comingMonthCash = cash.expenseDate.split(" ")[1]

                if (date == comingMonthCash) {
                    if (cash.expenseCatagory !in categorySumMap.keys) {
                        categorySumMap[cash.expenseCatagory] = cash.expensePrice.toFloat()
                        ExpenseBudget.add(cash)
                    }
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
                    var comingMonthCash = cash.incomeDate.split(" ")[1]
                    var comingMonthBank = bank.incomeDate.split(" ")[1]
                    if (date.equals(comingMonthCash) && date.equals(comingMonthBank)) {
                        if (bank.incomeBy == cash.incomeBy) {
                            val total = bank.incomePrice.toFloat() + cash.incomePrice.toFloat()
                            categorySumMap[bank.incomeBy] =
                                categorySumMap.getOrDefault(bank.incomeBy, 0f) + total
                            IncomeBudget.add(bank)
                        }
                    }
                }
            }

            for (bank in bankList) {
                var comingMonthBank = bank.incomeDate.split(" ")[1]
                if (date == comingMonthBank) {
                    if (bank.incomeBy !in categorySumMap.keys) {
                        categorySumMap[bank.incomeBy] = bank.incomePrice.toFloat()
                        IncomeBudget.add(bank)
                    }
                }
            }

            for (cash in cashList) {
                var comingMonthCash = cash.incomeDate.split(" ")[1]
                if (date == comingMonthCash) {
                    if (cash.incomeBy !in categorySumMap.keys) {
                        categorySumMap[cash.incomeBy] = cash.incomePrice.toFloat()
                        IncomeBudget.add(cash)
                    }
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


    fun setupLineChart() {
        viewModel.combinedDataLiveData.observe(viewLifecycleOwner) { combinedData ->
            val lineChart = binding.lineChart
            val expenseEntry = mutableListOf<Entry>()
            val incomeEntry = mutableListOf<Entry>()

            val monthToFloatMap = mapOf(
                "January" to 1f,
                "February" to 2f,
                "March" to 3f,
                "April" to 4f,
                "May" to 5f,
                "June" to 6f,
                "July" to 7f,
                "August" to 8f,
                "September" to 9f,
                "October" to 10f,
                "November" to 11f,
                "December" to 12f
            )

            val monthlyExpenseMap = mutableMapOf<Float, Float>()
            val monthlyIncomeMap = mutableMapOf<Float, Float>()

            for (expense in combinedData.bankExpenseModels + combinedData.cashExpenseModels) {
                val month = expense.expenseDate.split(" ")[1]
                val monthFloat = monthToFloatMap[month] ?: continue
                if (monthFloat != null) {
                    val totalExpense = monthlyExpenseMap.getOrDefault(monthFloat, 0f) + expense.expensePrice.toFloat()
                    monthlyExpenseMap[monthFloat] = totalExpense
                }
            }

            for (income in combinedData.bankIncomeModels + combinedData.cashIncomeModels) {
                val month = income.incomeDate.split(" ")[1]
                val monthFloat = monthToFloatMap[month] ?: continue
                if (monthFloat != null) {
                    val totalIncome = monthlyIncomeMap.getOrDefault(monthFloat, 0f) + income.incomePrice.toFloat()
                    monthlyIncomeMap[monthFloat] = totalIncome
                }
            }

            for ((month, totalExpense) in monthlyExpenseMap) {
                expenseEntry.add(Entry(month, totalExpense))
            }

            for ((month, totalIncome) in monthlyIncomeMap) {
                incomeEntry.add(Entry(month, totalIncome))
            }

            val expenseDataSet = LineDataSet(expenseEntry,"Expensed money")
            expenseDataSet.color = resources.getColor(R.color.expense_red)
            expenseDataSet.lineWidth = 2f
            expenseDataSet.circleColors = mutableListOf(resources.getColor(R.color.expense_red))

            val incomeDataSet = LineDataSet(incomeEntry, "Incoming money")
            incomeDataSet.color = resources.getColor(R.color.income_green)
            incomeDataSet.lineWidth = 2f
            incomeDataSet.circleColors = mutableListOf(resources.getColor(R.color.income_green))

            val lineData = LineData(expenseDataSet, incomeDataSet)
            lineChart.data = lineData

            lineChart.description.isEnabled = false
            lineChart.legend.isEnabled = true
            lineChart.xAxis.valueFormatter = MonthAxisValueFormatter()
            lineChart.axisLeft.isEnabled = true
            lineChart.axisRight.isEnabled = false
            lineChart.setTouchEnabled(true)

            lineChart.invalidate()
        }
    }










    override fun onResume() {
        super.onResume()
        viewModel.combinedDataLiveData
        viewModel.getAllLists()
    }
}

