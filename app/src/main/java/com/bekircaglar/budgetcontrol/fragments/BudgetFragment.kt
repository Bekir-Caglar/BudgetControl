package com.bekircaglar.budgetcontrol.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bekircaglar.budgetcontrol.R
import com.bekircaglar.budgetcontrol.databinding.FragmentBudgetBinding

class BudgetFragment : Fragment() {
 private lateinit var binding:FragmentBudgetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentBudgetBinding.inflate(layoutInflater,container,false)
        return binding.root
    }


}