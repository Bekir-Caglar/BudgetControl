package com.bekircaglar.budgetcontrol.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "Userdata")
data class UserData(@PrimaryKey(autoGenerate = true) @ColumnInfo("user_id") @NotNull val user_id:Int,
                    @ColumnInfo("user_name") @NotNull val user_name:String,
                    @ColumnInfo("user_mail") @NotNull val user_email:String,
                    @ColumnInfo("user_phone") @NotNull val user_phone:String,
                    @ColumnInfo("user_img") @NotNull val user_image:String,
                    @ColumnInfo("user_dob") @NotNull val user_dob:String) {



}