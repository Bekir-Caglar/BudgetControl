package com.bekircaglar.budgetcontrol.model

import com.github.mikephil.charting.formatter.ValueFormatter


class MonthAxisValueFormatter : ValueFormatter() {

    private val months = arrayOf(
        "Jan", "Feb", "Mar", "Apr", "May", "Jun",
        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    )

    override fun getFormattedValue(value: Float): String {
        return months[value.toInt() - 1]
    }
}