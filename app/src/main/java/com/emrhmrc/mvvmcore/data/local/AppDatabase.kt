package com.emrhmrc.mvvmcore.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.emrhmrc.mvvmcore.data.local.dao.UserDao
import com.emrhmrc.mvvmcore.data.local.entity.UserEntity
import com.emrhmrc.mvvmcore.data.local.typeconverter.DateConverter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider


@Database(entities = [UserEntity::class], version = 2, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    class DbCallback @Inject constructor(
        private val database: Provider<AppDatabase>,
        private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val dao = database.get().userDao()
            applicationScope.launch {
                dao.insert(UserEntity(name = "Emre"))
                dao.insert(UserEntity(name = "Emre2"))
                dao.insert(UserEntity(name = "Emre3"))
            }
        }
    }
}