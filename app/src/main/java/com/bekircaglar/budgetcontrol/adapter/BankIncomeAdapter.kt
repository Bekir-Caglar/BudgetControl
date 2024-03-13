package com.bekircaglar.budgetcontrol.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bekircaglar.budgetcontrol.databinding.ExpenseRowBinding
import com.bekircaglar.budgetcontrol.model.BankIncomeModel
import com.bekircaglar.budgetcontrol.viewmodel.BudgetFragmentViewModel

class BankIncomeAdapter(var viewmodel:BudgetFragmentViewModel,val context: Context,val incomeBankList:ArrayList<BankIncomeModel>):RecyclerView.Adapter<BankIncomeAdapter.IncomeHolder>() {
    class IncomeHolder(val binding: ExpenseRowBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncomeHolder {
        val binding = ExpenseRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return IncomeHolder(binding)
    }

    override fun getItemCount(): Int {
        return incomeBankList.size
    }

    override fun onBindViewHolder(holder: IncomeHolder, position: Int) {
        val revincomeBankList = incomeBankList.reversed()


        holder.binding.priceTxt.setTextColor(Color.rgb(36,179,74))

        holder.binding.imageView7.setImageResource(revincomeBankList[position].incomeImg)
        holder.binding.catagoryText.text = revincomeBankList[position].incomeBy
        holder.binding.dateText.text = revincomeBankList[position].incomeDate
        holder.binding.priceTxt.text = revincomeBankList[position].incomePrice.toString() + "€"

    }
}