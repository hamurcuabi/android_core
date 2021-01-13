package com.com.emrhmrc.mvvmcore.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.com.emrhmrc.mvvmcore.data.local.dao.UserDao
import com.com.emrhmrc.mvvmcore.data.local.entity.UserEntity
import com.com.emrhmrc.mvvmcore.data.local.typeconverter.DateConverter


@Database(entities = [UserEntity::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

}