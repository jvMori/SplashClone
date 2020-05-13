package com.jvmori.myapplication.users.domain

data class UserEntity(
    var userId: String = "",
    var name: String = "",
    var username: String = "",
    var profilePicLink: String = "",
    var totalPhotos: Int = 0
)