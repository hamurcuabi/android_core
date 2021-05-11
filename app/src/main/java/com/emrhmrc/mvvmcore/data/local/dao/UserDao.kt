package com.emrhmrc.mvvmcore.data.local.dao

import androidx.room.*
import com.emrhmrc.mvvmcore.data.local.entity.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM UserEntity")
    suspend fun getAll(): List<UserEntity>

    @Insert
    suspend fun insertAll(userEntities: List<UserEntity>)

    @Delete
    suspend fun delete(userEntity: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(userEntity: UserEntity)

}