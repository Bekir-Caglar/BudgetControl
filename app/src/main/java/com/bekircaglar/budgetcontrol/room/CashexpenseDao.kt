package com.bekircaglar.budgetcontrol.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.bekircaglar.budgetcontrol.model.CashExpenseModel

@Dao
interface CashexpenseDao {

    @Query("SELECT * FROM Cashexpenselist")
    suspend fun Cashexpenselistdao():List<CashExpenseModel>

    @Query("SELECT * FROM Cashexpenselist WHERE cashexpense_user = :userMail")
    suspend fun Cashexpenselistdaobyuser(userMail:String):List<CashExpenseModel>
    @Insert
    suspend fun addCashexpenselist(cashexpenselist:CashExpenseModel)

    @Delete
    suspend fun deleteCashexpenselist(cashexpenselist: CashExpenseModel)
}