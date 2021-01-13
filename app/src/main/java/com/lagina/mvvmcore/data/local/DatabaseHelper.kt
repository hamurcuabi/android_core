package com.lagina.mvvmcore.data.local

import com.lagina.mvvmcore.data.local.entity.UserEntity


interface DatabaseHelper {

    suspend fun getUsers(): List<UserEntity>

    suspend fun insertAll(userEntities: List<UserEntity>)

    suspend fun insert(users: UserEntity)

}