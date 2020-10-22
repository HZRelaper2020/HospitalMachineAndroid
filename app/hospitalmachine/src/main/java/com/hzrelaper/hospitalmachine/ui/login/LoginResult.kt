package com.hzrelaper.hospitalmachine.ui.login

import com.hzrelaper.hospitalmachine.data.entity.UserEntity

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val success: UserEntity? = null,
    val error: String? = null
)