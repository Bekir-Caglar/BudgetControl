package com.bekircaglar.budgetcontrol.di

import android.content.Context
import androidx.room.Room
import com.bekircaglar.budgetcontrol.database.repo.BudgetDaoRepo
import com.bekircaglar.budgetcontrol.model.UserData
import com.bekircaglar.budgetcontrol.room.AccountsMoneyDao
import com.bekircaglar.budgetcontrol.room.BankexpenseDao
import com.bekircaglar.budgetcontrol.room.BankincomeDao
import com.bekircaglar.budgetcontrol.room.CashexpenseDao
import com.bekircaglar.budgetcontrol.room.CashincomeDao
import com.bekircaglar.budgetcontrol.room.Database
import com.bekircaglar.budgetcontrol.room.UserDataDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

 class AppModule {
    @Provides
    @Singleton
    fun provideCashexpenseDao(@ApplicationContext context: Context): CashexpenseDao{
        val db =Room.databaseBuilder(context,Database::class.java,"budgetcontrol.sqlite")
            .createFromAsset("budgetcontrol.sqlite").build()
        return db.getCashexpensedao()
    }

    @Provides
    @Singleton
    fun provideCashincomeDao(@ApplicationContext context: Context): CashincomeDao{
        val db =Room.databaseBuilder(context,Database::class.java,"budgetcontrol.sqlite")
            .createFromAsset("budgetcontrol.sqlite").build()
        return db.getCashincomedao()
    }

    @Provides
    @Singleton
    fun provideBankincomeDao(@ApplicationContext context: Context): BankincomeDao{
        val db =Room.databaseBuilder(context,Database::class.java,"budgetcontrol.sqlite")
            .createFromAsset("budgetcontrol.sqlite").build()
        return db.getBankincomedao()
    }

    @Provides
    @Singleton
    fun provideBankexpenseDao(@ApplicationContext context: Context): BankexpenseDao{

        val db =Room.databaseBuilder(context,Database::class.java,"budgetcontrol.sqlite")
            .createFromAsset("budgetcontrol.sqlite").build()

        return db.getBankexpensedao()
    }
    @Provides
    @Singleton
    fun provideAccountsMoney(@ApplicationContext context: Context): AccountsMoneyDao{

        val db =Room.databaseBuilder(context,Database::class.java,"budgetcontrol.sqlite")
            .createFromAsset("budgetcontrol.sqlite").build()

        return db.getAccountsMoneyDao()
    }
    @Provides
    @Singleton
    fun provideUserData(@ApplicationContext context: Context): UserDataDao {

        val db =Room.databaseBuilder(context,Database::class.java,"budgetcontrol.sqlite")
            .createFromAsset("budgetcontrol.sqlite").build()

        return db.getUserDataDao()
    }





}