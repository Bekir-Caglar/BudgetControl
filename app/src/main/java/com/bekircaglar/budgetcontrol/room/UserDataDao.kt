package com.bekircaglar.budgetcontrol.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.bekircaglar.budgetcontrol.model.AccountsMoney
import com.bekircaglar.budgetcontrol.model.UserData

@Dao

interface UserDataDao {

    @Query("SELECT * FROM Userdata")
    suspend fun  getUser():List<UserData>

    @Update
    suspend fun updateUser(updatedUserData: UserData)


}