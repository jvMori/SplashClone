package com.jvmori.myapplication.search.domain.entities

data class UserEntity(
    var userId: String = "",
    var name: String = "",
    var username: String = "",
    var profilePicLink: String = "",
    var totalLikes: Int = 0
)