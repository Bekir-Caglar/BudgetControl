package com.bekircaglar.budgetcontrol.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.bekircaglar.budgetcontrol.model.BankModel

@Dao

interface BankexpenseDao {
    @Query("SELECT * FROM Bankexpenselist")
    suspend fun Bankexpenselistdao():List<BankModel>

    @Insert
    suspend fun addBankexpenselist(bankexpenselist:BankModel)

    @Delete
    suspend fun deleteBankexpenselist(bankexpenselist: BankModel)


}