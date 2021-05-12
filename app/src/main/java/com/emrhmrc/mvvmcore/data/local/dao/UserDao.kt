package com.emrhmrc.mvvmcore.data.local.dao

import androidx.room.*
import com.emrhmrc.mvvmcore.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user_entity")
    fun getAll(): Flow<List<UserEntity>>

    @Insert
    suspend fun insert(userEntities: UserEntity)

    @Insert
    suspend fun insertAll(userEntities: List<UserEntity>)

    @Delete
    suspend fun delete(userEntity: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(userEntity: UserEntity)

}