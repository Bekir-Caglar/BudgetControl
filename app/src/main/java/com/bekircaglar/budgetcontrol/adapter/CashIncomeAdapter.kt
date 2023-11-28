package com.bekircaglar.budgetcontrol.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bekircaglar.budgetcontrol.databinding.ExpenseRowBinding
import com.bekircaglar.budgetcontrol.model.CashIncomeModel
import com.bekircaglar.budgetcontrol.viewmodel.BudgetFragmentViewModel

class CashIncomeAdapter(val viewModel: BudgetFragmentViewModel,val context: Context,val incomeCashList:ArrayList<CashIncomeModel>):RecyclerView.Adapter<CashIncomeAdapter.IncomeCashHolder>() {
    class IncomeCashHolder(val binding: ExpenseRowBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncomeCashHolder {
        val binding = ExpenseRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return IncomeCashHolder(binding)
    }

    override fun getItemCount(): Int {
        return incomeCashList.size
    }

    override fun onBindViewHolder(holder: IncomeCashHolder, position: Int) {
        val revincomeCashList = incomeCashList.reversed()

        holder.binding.closebutton.setOnClickListener {
            viewModel.deleteCashincomeList(revincomeCashList[position].income_id)

        }
        holder.binding.priceTxt.setTextColor(Color.rgb(36,179,74))

        holder.binding.imageView7.setImageResource(revincomeCashList[position].incomeImg)
        holder.binding.catagoryText.text = revincomeCashList[position].incomeBy
        holder.binding.dateText.text = revincomeCashList[position].incomeDate
        holder.binding.priceTxt.text = revincomeCashList[position].incomePrice.toString() + "€"


    }
}