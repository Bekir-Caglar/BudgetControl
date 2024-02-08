package com.bekircaglar.budgetcontrol.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity("Bankincomelist")
data class BankIncomeModel(
    @PrimaryKey(autoGenerate = true) @ColumnInfo("bankincome_id") @NotNull val income_id: Int,
    @ColumnInfo("bankincome_img") @NotNull  val incomeImg: Int,
    @ColumnInfo("bankincome_by") @NotNull override val incomeBy: String,
    @ColumnInfo("bankincome_date") @NotNull  override val incomeDate: String,
    @ColumnInfo("bankincome_price") @NotNull override val incomePrice: Int
) : Serializable,IncomeModel
{



}



