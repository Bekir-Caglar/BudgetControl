package com.bekircaglar.budgetcontrol.fragments.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.bekircaglar.budgetcontrol.R
import com.bekircaglar.budgetcontrol.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth


class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var auth: FirebaseAuth




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding.createAccountButton.setOnClickListener {


            val firstname = binding.firstnameEditText.text.toString()
            val lastname = binding.lastNameEditText.text.toString()
            val userMail = binding.eMailEditText.text.toString()
            val userPassword = binding.passwordEditText.text.toString()

            if (userMail.equals("")  ||  userPassword.equals("")){
                Toast.makeText(requireContext(), "Enter Password And Email", Toast.LENGTH_LONG).show()



            }
            else{
                auth.createUserWithEmailAndPassword(userMail,userPassword).addOnSuccessListener {

                    val user = auth.currentUser

                    val username = firstname + " " + lastname
                    val actionSignUpToHome = SignUpFragmentDirections.actionSignUpFragmentToNewUserProfile(username, userMail)
                    Navigation.findNavController(view).navigate(actionSignUpToHome)


                }.addOnFailureListener {

                    Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_LONG).show()
                }

            }

        }

        binding.signinTextButton.setOnClickListener {
            val actionSignUpToLogin = SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
            Navigation.findNavController(view).navigate(actionSignUpToLogin)
        }

    }


}