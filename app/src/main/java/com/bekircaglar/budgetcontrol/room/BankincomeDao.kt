package com.bekircaglar.budgetcontrol.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.bekircaglar.budgetcontrol.model.BankIncomeModel
import com.bekircaglar.budgetcontrol.model.BankModel

@Dao

interface BankincomeDao {

    @Query("SELECT * FROM Bankincomelist")
    suspend fun Bankincomelistdao():List<BankIncomeModel>

    @Query("SELECT * FROM Bankincomelist WHERE bankincome_user = :userMail")
    suspend fun getBankincomelistbyuser(userMail:String):List<BankIncomeModel>

    @Insert
    suspend fun addBankincomelist(bankincomelist:BankIncomeModel)

    @Delete
    suspend fun deleteBankincomelist(bankincomelist: BankIncomeModel)
}