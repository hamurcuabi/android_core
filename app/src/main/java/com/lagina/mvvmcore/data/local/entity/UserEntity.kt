package com.lagina.mvvmcore.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class UserEntity(
    @PrimaryKey(autoGenerate = true) @SerializedName("id")
    val id: Int = 0,
    @ColumnInfo(name = "name") @SerializedName("name")
    var name: String = "",
    @ColumnInfo(name = "email") @SerializedName("email")
    var email: String = "",
    @ColumnInfo(name = "avatar") @SerializedName("avatar")
    var avatar: String = ""
)