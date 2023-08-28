package com.bekircaglar.budgetcontrol.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.bekircaglar.budgetcontrol.R
import com.bekircaglar.budgetcontrol.databinding.FragmentWelcome2Binding


class Welcome2Fragment : Fragment() {
 private lateinit var binding: FragmentWelcome2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding = FragmentWelcome2Binding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.previewbutton.setOnClickListener {

            Navigation.findNavController(it).navigate(R.id.action_welcome2Fragment2_to_welcomeFragment)

        }
        binding.nextbutton.setOnClickListener {

            Navigation.findNavController(it).navigate(R.id.action_welcome2Fragment2_to_welcome3Fragment)

        }

    }


}