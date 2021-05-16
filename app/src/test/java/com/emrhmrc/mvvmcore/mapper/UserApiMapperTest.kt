package com.emrhmrc.mvvmcore.mapper

import com.emrhmrc.mvvmcore.data.local.entity.UserEntity
import com.emrhmrc.mvvmcore.data.network.model.ApiUser
import com.google.common.truth.Truth.assertThat
import org.junit.Test

/**
 * Rev           1.0
 * Author        hamurcuabi
 * Date          5/16/2021
 * FileName
 */
class UserApiMapperTest {

    private val userApiMapper = UserApiMapper()
    private val userEntity = UserEntity(id = 1, name = "emre", email = "email", avatar = "avatar")
    private val apiUser = ApiUser(id = 1, name = "emre", email = "email", avatar = "avatar")

    @Test
    fun mapFromEntity() {
        assertThat(userApiMapper.mapFromEntity(userEntity)).isEqualTo(apiUser)
    }

    @Test
    fun mapToEntity() {
        assertThat(userApiMapper.mapToEntity(apiUser)).isEqualTo(userEntity)
    }

    @Test
    fun fromEntityList() {
        val listUserEntity = arrayListOf(userEntity, userEntity, userEntity)
        val listApiUser = arrayListOf(apiUser, apiUser, apiUser)

        assertThat(userApiMapper.fromEntityList(listUserEntity)).isEqualTo(listApiUser)
    }

    @Test
    fun toEntityList() {
        val listUserEntity = arrayListOf(userEntity, userEntity, userEntity)
        val listApiUser = arrayListOf(apiUser, apiUser, apiUser)

        assertThat(userApiMapper.toEntityList(listApiUser)).isEqualTo(listUserEntity)
    }
}