package com.bekircaglar.budgetcontrol.model

data class DashboardData(
    val bankExpenseModels: List<BankModel>,
    val cashExpenseModels: List<CashExpenseModel>,
    val bankIncomeModels: List<BankIncomeModel>,
    val cashIncomeModels: List<CashIncomeModel>,
)