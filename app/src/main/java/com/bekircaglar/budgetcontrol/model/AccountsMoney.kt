package com.bekircaglar.budgetcontrol.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity (tableName = "Accounts")
data class AccountsMoney(
    @PrimaryKey(autoGenerate = true) @ColumnInfo("Accounts_id") @NotNull val accountsId: Int,
    @ColumnInfo("BankAccountMoney") @NotNull val bankMoney: String,
    @ColumnInfo("CashAccountMoney") @NotNull val cashMoney: String,
    @ColumnInfo("UserAccount") @NotNull val userAccount: String
) {
}