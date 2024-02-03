package com.bekircaglar.budgetcontrol.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity("Bankexpenselist")

data class BankModel(@PrimaryKey(autoGenerate = true) @ColumnInfo("bankexpense_id")@NotNull val expenseid:Int,
                     @ColumnInfo("bankexpense_img") @NotNull val expenseImg:Int,
                     @ColumnInfo("bankexpense_price") @NotNull override val expensePrice :Int,
                     @ColumnInfo("bankexpense_category") @NotNull override val expenseCatagory:String,
                     @ColumnInfo("bankexpense_date") @NotNull val expenseDate:String) :Serializable,ExpenseModel{


}




