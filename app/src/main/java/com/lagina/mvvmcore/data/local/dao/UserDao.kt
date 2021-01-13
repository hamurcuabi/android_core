package com.lagina.mvvmcore.data.local.dao

import androidx.room.*
import com.lagina.mvvmcore.data.local.entity.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM userentity")
    suspend fun getAll(): List<UserEntity>

    @Insert
    suspend fun insertAll(userEntities: List<UserEntity>)

    @Delete
    suspend fun delete(userEntity: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(userEntity: UserEntity)

}