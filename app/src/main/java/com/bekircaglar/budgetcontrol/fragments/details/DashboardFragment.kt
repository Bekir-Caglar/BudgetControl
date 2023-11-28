package com.bekircaglar.budgetcontrol.fragments.details

import android.graphics.Color
import android.graphics.ColorSpace.Rgb
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bekircaglar.budgetcontrol.R
import com.bekircaglar.budgetcontrol.databinding.FragmentDashboardBinding
import com.bekircaglar.budgetcontrol.databinding.FragmentHelpBinding
import com.bekircaglar.budgetcontrol.databinding.FragmentNewIncomeBinding
import com.bekircaglar.budgetcontrol.model.BankIncomeModel
import com.bekircaglar.budgetcontrol.model.BankModel
import com.bekircaglar.budgetcontrol.model.CashIncomeModel
import com.bekircaglar.budgetcontrol.model.CashModel
import com.bekircaglar.budgetcontrol.viewmodel.BudgetFragmentViewModel
import com.bekircaglar.budgetcontrol.viewmodel.DashboardFragmentViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var viewModel: DashboardFragmentViewModel
    private var bankMoneyWrite :String = "0"
    private var cashMoneyWrite :String = "0"
    var bankExpenseMoney = 0
    var cashExpenseMoney = 0
    var bankIncomeMoney = 0
    var cashIncomeMoney = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDashboardBinding.inflate(layoutInflater,container,false)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : DashboardFragmentViewModel by viewModels()
        viewModel = tempViewModel


    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.AccountsMoneyListM.observe(viewLifecycleOwner){
            bankMoneyWrite = it[0].bankMoney
            cashMoneyWrite = it[0].cashMoney

            val piechartList:ArrayList<PieEntry> = ArrayList()

            piechartList.add(PieEntry(bankMoneyWrite.toFloat(),"Bank"))
            piechartList.add(PieEntry(cashMoneyWrite.toFloat(),"Cash"))

            val pieDataSetMain = PieDataSet(piechartList,"")
           pieDataSetMain.setColors(Color.rgb(213,235,254),Color.rgb(204,241,229),Color.rgb(254,224,209),255)
           pieDataSetMain.valueTextSize = 12f
           pieDataSetMain.xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
           pieDataSetMain.yValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE
           pieDataSetMain.sliceSpace = 3f
           pieDataSetMain.valueTextColor = R.color.dark_text_color
           pieDataSetMain.valueLineColor = Color.TRANSPARENT

            val pieData = PieData(pieDataSetMain)

            binding.piechartMain.setEntryLabelColor(R.color.dark_text_color)
            binding.piechartMain.center
            binding.piechartMain.data = pieData
            binding.piechartMain.description.text = ""
            binding.piechartMain.animateY(2000)
            binding.piechartMain.isDrawHoleEnabled = false

            val totalMoney = bankMoneyWrite.toFloat() + cashMoneyWrite.toFloat()
            binding.totalMoney.text = ("$totalMoney €").toString()
            binding.bankMoney.text = bankMoneyWrite
            binding.cashMoney.text = cashMoneyWrite


        }
        viewModel.expenseListBankM.observe(viewLifecycleOwner){
            cashExpenseMoney = 0
            val piechartList:ArrayList<PieEntry> = ArrayList()
            for (i in it){
                piechartList.add(PieEntry(i.expensePrice.toFloat(),i.expenseCatagory))
            }

            val pieDataSetExpenses = PieDataSet(piechartList,"")
            pieDataSetExpenses.setColors(Color.rgb(213,235,254),Color.rgb(204,241,229),Color.rgb(254,224,209),Color.rgb(241, 144, 109),Color.rgb(226, 71, 15),Color.rgb(120, 218, 117),Color.rgb(12, 76, 11),255)
            pieDataSetExpenses.valueTextSize = 8f
            pieDataSetExpenses.xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
            pieDataSetExpenses.yValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE
            pieDataSetExpenses.sliceSpace = 2f
            pieDataSetExpenses.valueTextColor = R.color.dark_text_color
            pieDataSetExpenses.valueLineColor = Color.TRANSPARENT

            val pieData = PieData(pieDataSetExpenses)

            binding.piechartSpent.setEntryLabelColor(R.color.dark_text_color)
            binding.piechartSpent.center
            binding.piechartSpent.data = pieData
            binding.piechartSpent.description.text = ""
            binding.piechartSpent.animateY(2000)
            binding.piechartSpent.isDrawHoleEnabled = false
            binding.piechartSpent.setEntryLabelTextSize(8f)
        }
        viewModel.incomeListBankM.observe(viewLifecycleOwner){
            val piechartList:ArrayList<PieEntry> = ArrayList()
            for (i in it){
                piechartList.add(PieEntry(i.incomePrice.toFloat(),i.incomeBy))
            }
            val pieDataSetIncome = PieDataSet(piechartList,"")
            pieDataSetIncome.setColors(Color.rgb(213,235,254),Color.rgb(204,241,229),Color.rgb(254,224,209),Color.rgb(241, 144, 109),Color.rgb(226, 71, 15),Color.rgb(120, 218, 117),Color.rgb(12, 76, 11),255)
            pieDataSetIncome.valueTextSize = 8f
            pieDataSetIncome.xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
            pieDataSetIncome.yValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE
            pieDataSetIncome.sliceSpace = 2f
            pieDataSetIncome.valueTextColor = R.color.dark_text_color
            pieDataSetIncome.valueLineColor = Color.TRANSPARENT

            val pieData = PieData(pieDataSetIncome)

            binding.piechartEarn.setEntryLabelColor(R.color.dark_text_color)
            binding.piechartEarn.center
            binding.piechartEarn.data = pieData
            binding.piechartEarn.description.text = ""
            binding.piechartEarn.animateY(2000)
            binding.piechartEarn.isDrawHoleEnabled = false
            binding.piechartEarn.setEntryLabelTextSize(8f)

        }


    }



}
