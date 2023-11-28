package com.bekircaglar.budgetcontrol.util

import android.view.View
import androidx.navigation.NavDirections
import androidx.navigation.Navigation


fun Navigation.switch(it: View,id:Int){
    Navigation.findNavController(it).navigate(id)

}
fun Navigation.switch(it: View,id:NavDirections ){
    Navigation.findNavController(it).navigate(id)

}
