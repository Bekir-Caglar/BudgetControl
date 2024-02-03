package com.bekircaglar.budgetcontrol.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bekircaglar.budgetcontrol.model.AccountsMoney
import com.bekircaglar.budgetcontrol.model.BankIncomeModel
import com.bekircaglar.budgetcontrol.model.BankModel
import com.bekircaglar.budgetcontrol.model.CashIncomeModel
import com.bekircaglar.budgetcontrol.model.CashExpenseModel
import com.bekircaglar.budgetcontrol.model.UserData

@Database(entities = [CashExpenseModel::class, BankModel::class, CashIncomeModel::class, BankIncomeModel::class,AccountsMoney::class,UserData::class], version = 1)
abstract class Database:RoomDatabase() {


    abstract fun getCashexpensedao():CashexpenseDao
    abstract fun getBankexpensedao():BankexpenseDao
    abstract fun getCashincomedao():CashincomeDao
    abstract fun getBankincomedao():BankincomeDao
    abstract fun getAccountsMoneyDao():AccountsMoneyDao
    abstract fun getUserDataDao():UserDataDao


}