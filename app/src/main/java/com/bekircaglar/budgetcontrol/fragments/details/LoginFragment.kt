package com.bekircaglar.budgetcontrol.fragments.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.bekircaglar.budgetcontrol.R
import com.bekircaglar.budgetcontrol.databinding.FragmentLoginBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class LoginFragment : Fragment() {
private lateinit var binding: FragmentLoginBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        requireActivity() as AppCompatActivity
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.GONE

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSignin.setOnClickListener{

            val actionlogintoHome = LoginFragmentDirections.actionLoginFragmentToBudgetFragment()
            Navigation.findNavController(view).navigate(actionlogintoHome)


        }
        binding.signupTextButton.setOnClickListener {


            val actionlogintosingup = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
            Navigation.findNavController(view).navigate(actionlogintosingup)

        }


    }



}