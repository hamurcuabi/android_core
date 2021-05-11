package com.emrhmrc.mvvmcore.mapper

import com.emrhmrc.mvvmcore.data.local.entity.UserEntity
import com.emrhmrc.mvvmcore.data.network.model.ApiUser

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



