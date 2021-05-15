package com.emrhmrc.data.local.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.emrhmrc.getOrAwaitValueTest
import com.emrhmrc.mvvmcore.data.local.AppDatabase
import com.emrhmrc.mvvmcore.data.local.dao.UserDao
import com.emrhmrc.mvvmcore.data.local.entity.UserEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 *  Rev           1.0
 *  Author        hamurcuabi
 *  Date          5/15/2021
 *  FileName     UserDaoTest
 */
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4ClassRunner::class)
@SmallTest
class UserDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: AppDatabase
    private lateinit var dao: UserDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.userDao()
    }

    @After
    fun after() {
        db.close()
    }

    @Test
    fun test_insert() = runBlockingTest {
        val user = UserEntity(id = 1, name = "emre", email = "email", avatar = "avatar")
        dao.insert(user)
        val data = dao.getAll().asLiveData().getOrAwaitValueTest()
        assertThat(data).contains(user)
    }

    @Test
    fun test_delete() = runBlockingTest {
        val user = UserEntity(id = 1, name = "emre", email = "email", avatar = "avatar")
        dao.insert(user)
        dao.delete(user)
        val data = dao.getAll().asLiveData().getOrAwaitValueTest()
        assertThat(data).doesNotContain(user)
    }

    @Test
    fun test_insert_list() = runBlockingTest {
        val user1 = UserEntity(id = 1, name = "emre", email = "email", avatar = "avatar")
        val user2 = UserEntity(id = 2, name = "emre", email = "email", avatar = "avatar")
        val user3 = UserEntity(id = 3, name = "emre", email = "email", avatar = "avatar")
        val list = arrayListOf(user1, user2, user3)
        dao.insertAll(list)
        val data = dao.getAll().asLiveData().getOrAwaitValueTest()
        assertThat(data).contains(user1)
        assertThat(data).contains(user2)
        assertThat(data).contains(user3)
    }

}