package com.com.lagina.mvvmcore.mapper

import com.com.lagina.mvvmcore.data.domainmodel.User
import com.com.lagina.mvvmcore.data.local.entity.UserEntity
import com.com.lagina.mvvmcore.utils.EntityMapper

class UserApiMapper : EntityMapper<UserEntity, User> {
    override fun mapFromEntity(entity: UserEntity): User {
        return User(
            email = entity.email,
            id = entity.id,
            avatar = entity.avatar,
            name = entity.name
        )
    }

    override fun mapToEntity(domainModel: User): UserEntity {
        return UserEntity(
            email = domainModel.email,
            id = domainModel.id,
            avatar = domainModel.avatar,
            name = domainModel.name
        )
    }

    fun fromEntityList(initial: List<UserEntity>): List<User> {
        return initial.map { mapFromEntity(it) }
    }

    fun toEntityList(initial: List<User>): List<UserEntity> {
        return initial.map { mapToEntity(it) }
    }

}



