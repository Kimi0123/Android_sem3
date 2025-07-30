package com.example.ai36.model

data class UserModel(
    var userId: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var address: String = "",
    var email: String = "",
    var country: String = "",
    var image: String? = null // Important for profile picture!
)
