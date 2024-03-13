package com.bekircaglar.budgetcontrol.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.bekircaglar.budgetcontrol.model.AccountsMoney
import com.bekircaglar.budgetcontrol.model.UserData

@Dao

interface UserDataDao {
    @Query("SELECT * FROM Userdata WHERE user_mail = :mail")
    suspend fun  getUserByMail(mail:String):List<UserData>

    @Query("UPDATE Userdata SET user_img = :userImage,user_name = :userName,user_phone = :userPhone, user_dob = :userDob WHERE user_mail = :userMail")
    suspend fun updateUserByMail(userImage:String,userName:String,userPhone:String,userMail: String,userDob:String)

    @Insert
    suspend fun insertUser(insertUserData: UserData)


}