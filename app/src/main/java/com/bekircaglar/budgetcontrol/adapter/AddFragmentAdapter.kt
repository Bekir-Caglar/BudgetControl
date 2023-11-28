package com.bekircaglar.budgetcontrol.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bekircaglar.budgetcontrol.fragments.details.NewExpenseFragment
import com.bekircaglar.budgetcontrol.fragments.details.NewIncomeFragment

class AddFragmentAdapter(
    fragmentManager:FragmentManager,
    lifecycle: Lifecycle
):FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {

        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0 ){
            NewExpenseFragment()
        }
        else
            NewIncomeFragment()



    }
}