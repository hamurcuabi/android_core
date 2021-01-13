package com.com.emrhmrc.mvvmcore.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.com.emrhmrc.mvvmcore.data.local.entity.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM userentity")
    suspend fun getAll(): List<UserEntity>

    @Insert
    suspend fun insertAll(userEntities: List<UserEntity>)

    @Delete
    suspend fun delete(userEntity: UserEntity)

    @Insert
    suspend fun add(userEntity: UserEntity)

}