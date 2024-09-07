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


    @Insert
    suspend fun insertAccountMoney(accountsMoney: AccountsMoney)

    @Query("SELECT * FROM Accounts WHERE UserAccount = :userMail")
    suspend fun getAccountsMoneyByUser(userMail:String):List<AccountsMoney>

    @Query("UPDATE Accounts SET CashAccountMoney = :updatedCashMoney  , BankAccountMoney = :updatedBankMoney WHERE UserAccount = :userMail")
    suspend fun updateAccountMoneyByUser(userMail:String,updatedBankMoney:String,updatedCashMoney:String)

}