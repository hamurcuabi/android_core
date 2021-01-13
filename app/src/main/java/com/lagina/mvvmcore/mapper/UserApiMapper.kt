package com.lagina.mvvmcore.mapper

import com.lagina.mvvmcore.data.domainmodel.User
import com.lagina.mvvmcore.data.local.entity.UserEntity
import com.lagina.mvvmcore.data.network.model.ApiUser
import com.lagina.mvvmcore.utils.EntityMapper

class UserApiMapper : EntityMapper<UserEntity, ApiUser> {
    override fun mapFromEntity(entity: UserEntity): ApiUser {
        return ApiUser(
            email = entity.email,
            id = entity.id,
            avatar = entity.avatar,
            name = entity.name
        )
    }

    override fun mapToEntity(domainModel: ApiUser): UserEntity {
        return UserEntity(
            email = domainModel.email,
            id = domainModel.id,
            avatar = domainModel.avatar,
            name = domainModel.name
        )
    }

    fun fromEntityList(initial: List<UserEntity>): List<ApiUser> {
        return initial.map { mapFromEntity(it) }
    }

    fun toEntityList(initial: List<ApiUser>): List<UserEntity> {
        return initial.map { mapToEntity(it) }
    }

}



