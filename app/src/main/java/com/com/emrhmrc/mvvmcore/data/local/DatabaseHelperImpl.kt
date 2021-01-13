package com.com.emrhmrc.mvvmcore.data.local

import com.com.emrhmrc.mvvmcore.data.local.entity.UserEntity
import javax.inject.Inject

class DatabaseHelperImpl @Inject constructor(private val appDatabase: AppDatabase) :
    DatabaseHelper {

    override suspend fun getUsers(): List<UserEntity> = appDatabase.userDao().getAll()

    override suspend fun insertAll(userEntities: List<UserEntity>) = appDatabase.userDao().insertAll(userEntities)

    override suspend fun insert(users: UserEntity) {
        appDatabase.userDao().add(users)
    }

}