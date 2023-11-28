package com.bekircaglar.budgetcontrol.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity("Cashexpenselist")

data class CashModel(@PrimaryKey(autoGenerate = true) @ColumnInfo("cashexpense_id") @NotNull val expenseid:Int,
                     @ColumnInfo("cashexpense_img") @NotNull val expenseImg:Int,
                     @ColumnInfo("cashexpense_price") @NotNull val expensePrice :Int,
                     @ColumnInfo("cashexpense_category") @NotNull val expenseCatagory:String,
                     @ColumnInfo("cashexpense_date") @NotNull val expenseDate:String) :Serializable{


}




