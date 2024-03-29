package com.bekircaglar.budgetcontrol.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.bekircaglar.budgetcontrol.model.CashIncomeModel

@Dao

interface CashincomeDao {

    @Query("SELECT * FROM Cashincomelist")
    suspend fun Cashincomelistdao():List<CashIncomeModel>

    @Query("SELECT * FROM Cashincomelist WHERE cashincome_user = :userMail")
    suspend fun Cashincomelistdaobyuser(userMail:String):List<CashIncomeModel>

    @Insert
    suspend fun addCashincomelist(cashincomelist: CashIncomeModel)

    @Delete
    suspend fun deleteCashincomelist(cashincomelist: CashIncomeModel)
}
