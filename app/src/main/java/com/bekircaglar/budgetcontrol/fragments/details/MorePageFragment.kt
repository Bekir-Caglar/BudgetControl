package com.bekircaglar.budgetcontrol.fragments.details

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.bekircaglar.budgetcontrol.R
import com.bekircaglar.budgetcontrol.databinding.FragmentMorePageBinding
import com.bekircaglar.budgetcontrol.fragments.details.MorePageFragmentDirections
import com.bekircaglar.budgetcontrol.model.UserData
import com.bekircaglar.budgetcontrol.viewmodel.BudgetFragmentViewModel
import com.bekircaglar.budgetcontrol.viewmodel.FragmentMorePageViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Base64

@AndroidEntryPoint

class MorePageFragment : Fragment() {
    private lateinit var binding : FragmentMorePageBinding
    private lateinit var viewModel: FragmentMorePageViewModel
    private lateinit var encodedImage: String



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_more_page,container,false)
        binding.morePageFragment = this

        viewModel.userDataList.observe(viewLifecycleOwner){

            if (it.isEmpty()){
                binding.imageViewpp.setImageResource(R.drawable.user)
                binding.yourEmailText.text = "Your Email"
                binding.yourNameText.text = "Your Name"
            }
            else{
                encodedImage = it[0].user_image
                binding.yourEmailText.text = it[0].user_email
                binding.yourNameText.text = it[0].user_name
                try {
                    val decodedString: ByteArray = Base64.getDecoder().decode(encodedImage)
                    val decodedByte: Bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                    binding.imageViewpp.setImageBitmap(decodedByte)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }









            viewModel.getUserData()
        }

        binding.creditCards.setOnClickListener {
            creditCards()
        }
        binding.help.setOnClickListener {
            goHelpPageButton()
        }





        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : FragmentMorePageViewModel by viewModels()
        viewModel = tempViewModel

    }


    fun goProfilePageButton(){
        viewModel.goProfilePage(binding.root)

    }
    fun goHelpPageButton(){
        viewModel.goHelpPage(binding.root)

    }
    fun creditCards(){
        viewModel.goAccount(binding.root)

    }

}