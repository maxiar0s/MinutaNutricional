package com.exp1_s1.minutanutricional.data

import com.exp1_s1.minutanutricional.model.RegisteredUser

class UserRepository {
    private val users = mutableListOf<RegisteredUser>()

    fun register(name: String, email: String, password: String): RegistrationResult {
        val cleanName = name.trim()
        val cleanEmail = email.trim()

        if (cleanName.isEmpty() || cleanEmail.isEmpty() || password.isEmpty()) {
            return RegistrationResult.EmptyFields
        }
        if (!isValidEmail(cleanEmail)) {
            return RegistrationResult.InvalidEmail
        }
        if (password.length < MIN_PASSWORD_LENGTH) {
            return RegistrationResult.WeakPassword
        }
        if (users.any { it.email.equals(cleanEmail, ignoreCase = true) }) {
            return RegistrationResult.DuplicateEmail
        }
        if (users.size >= MAX_USERS) {
            return RegistrationResult.CapacityReached
        }

        users.add(RegisteredUser(name = cleanName, email = cleanEmail, password = password))
        return RegistrationResult.Success
    }

    fun authenticate(email: String, password: String): LoginResult {
        if (email.isBlank() || password.isEmpty()) return LoginResult.EmptyFields

        return if (users.any { user ->
            user.email.equals(email.trim(), ignoreCase = true) && user.password == password
        }) LoginResult.Success else LoginResult.InvalidCredentials
    }

    fun hasRegisteredEmail(email: String): Boolean =
        users.any { it.email.equals(email.trim(), ignoreCase = true) }

    fun recoverPassword(email: String, newPassword: String): RecoveryResult = when {
        email.isBlank() -> RecoveryResult.EmptyEmail
        !isValidEmail(email.trim()) -> RecoveryResult.InvalidEmail
        newPassword.length < MIN_PASSWORD_LENGTH -> RecoveryResult.WeakPassword
        hasRegisteredEmail(email) -> {
            val userIndex = users.indexOfFirst { it.email.equals(email.trim(), ignoreCase = true) }
            users[userIndex] = users[userIndex].copy(password = newPassword)
            RecoveryResult.PasswordUpdated
        }
        else -> RecoveryResult.UnregisteredEmail
    }

    fun registeredUsers(): List<RegisteredUser> = users.toList()

    companion object {
        const val MAX_USERS = 5
        const val MIN_PASSWORD_LENGTH = 6

        fun isValidEmail(email: String): Boolean =
            email.contains('@') && email.substringAfter('@').contains('.')
    }
}

enum class RegistrationResult {
    Success,
    EmptyFields,
    InvalidEmail,
    WeakPassword,
    DuplicateEmail,
    CapacityReached
}

enum class LoginResult {
    Success,
    EmptyFields,
    InvalidCredentials
}

enum class RecoveryResult {
    PasswordUpdated,
    EmptyEmail,
    InvalidEmail,
    WeakPassword,
    UnregisteredEmail
}
