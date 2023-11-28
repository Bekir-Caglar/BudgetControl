package com.bekircaglar.budgetcontrol.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.bekircaglar.budgetcontrol.model.AccountsMoney
import com.bekircaglar.budgetcontrol.model.BankModel

@Dao

interface AccountsMoneyDao {

    @Query("SELECT * FROM Accounts")
    suspend fun  getAccountsMoney():List<AccountsMoney>

    @Update
    suspend fun updateAccountsMoney(updatedMoney:AccountsMoney)

}