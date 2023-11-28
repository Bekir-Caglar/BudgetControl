package com.bekircaglar.budgetcontrol.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bekircaglar.budgetcontrol.databinding.ExpenseRowBinding
import com.bekircaglar.budgetcontrol.model.BankModel
import com.bekircaglar.budgetcontrol.viewmodel.BudgetFragmentViewModel

class BankAdapter(var viewmodel: BudgetFragmentViewModel,val context: Context, val bankList: ArrayList<BankModel>):RecyclerView.Adapter<BankAdapter.BankHolder>() {
    class BankHolder(val binding: ExpenseRowBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankHolder {
        val binding = ExpenseRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BankHolder(binding)
    }

    override fun getItemCount(): Int {
        return bankList.size
    }

    override fun onBindViewHolder(holder: BankHolder, position: Int) {
        val revbanklist = bankList.reversed()

        holder.binding.closebutton.setOnClickListener {
            viewmodel.deleteBankexpenseList(revbanklist[position].expenseid)

        }
        holder.binding.imageView7.setImageResource(revbanklist[position].expenseImg)
        holder.binding.catagoryText.text = revbanklist[position].expenseCatagory
        holder.binding.dateText.text = revbanklist[position].expenseDate
        holder.binding.priceTxt.text = "-"+ revbanklist[position].expensePrice.toString() + "€"
    }
}