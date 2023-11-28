package com.bekircaglar.budgetcontrol.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bekircaglar.budgetcontrol.databinding.ExpenseRowBinding
import com.bekircaglar.budgetcontrol.model.CashModel
import com.bekircaglar.budgetcontrol.viewmodel.BudgetFragmentViewModel

class CashAdapter(val viewModel: BudgetFragmentViewModel,val contex:Context, val cashlist: ArrayList<CashModel>):RecyclerView.Adapter<CashAdapter.CashHolder>() {
    class CashHolder(val binding:ExpenseRowBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CashHolder {
        val binding = ExpenseRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CashHolder(binding)
    }

    override fun getItemCount(): Int {
        return cashlist.size
    }

    override fun onBindViewHolder(holder: CashHolder, position: Int) {
        val revcashlist = cashlist.reversed()

        holder.binding.closebutton.setOnClickListener {
            viewModel.deleteCashexpenseList(revcashlist[position].expenseid)

        }
        holder.binding.imageView7.setImageResource(revcashlist[position].expenseImg)
        holder.binding.catagoryText.text = revcashlist[position].expenseCatagory
        holder.binding.dateText.text = revcashlist[position].expenseDate
        holder.binding.priceTxt.text =  "-"+revcashlist[position].expensePrice.toString() + "€"


    }
}