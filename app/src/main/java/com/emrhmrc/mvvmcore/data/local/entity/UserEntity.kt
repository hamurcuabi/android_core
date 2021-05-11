package com.emrhmrc.mvvmcore.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    var name: String = "",
    @ColumnInfo(name = "email")
    var email: String = "",
    @ColumnInfo(name = "avatar")
    var avatar: String = ""
)