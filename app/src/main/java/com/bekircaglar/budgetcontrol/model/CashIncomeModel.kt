package com.bekircaglar.budgetcontrol.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity("Cashincomelist")
data class CashIncomeModel(@PrimaryKey(autoGenerate = true) @ColumnInfo("cashincome_id") @NotNull val income_id:Int,
                           @ColumnInfo("cashincome_img") @NotNull val incomeImg:Int,
                           @ColumnInfo("cashincome_by") @NotNull override val incomeBy:String,
                           @ColumnInfo("cashincome_date") @NotNull val incomeDate:String,
                           @ColumnInfo("cashincome_price") @NotNull override val incomePrice:Int):Serializable,IncomeModel
{

}