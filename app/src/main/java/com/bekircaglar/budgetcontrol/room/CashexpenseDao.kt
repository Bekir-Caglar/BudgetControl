package com.bekircaglar.budgetcontrol.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.bekircaglar.budgetcontrol.model.BankIncomeModel
import com.bekircaglar.budgetcontrol.model.BankModel
import com.bekircaglar.budgetcontrol.model.CashModel

@Dao
interface CashexpenseDao {

    @Query("SELECT * FROM Cashexpenselist")
    suspend fun Cashexpenselistdao():List<CashModel>

    @Insert
    suspend fun addCashexpenselist(cashexpenselist:CashModel)

    @Delete
    suspend fun deleteCashexpenselist(cashexpenselist: CashModel)
}