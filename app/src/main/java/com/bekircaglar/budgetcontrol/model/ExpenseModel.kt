package com.bekircaglar.budgetcontrol.model

interface ExpenseModel {
    val expenseUser: String
    val expenseDate: String
    val expensePrice: Int
    val expenseCatagory: String
}