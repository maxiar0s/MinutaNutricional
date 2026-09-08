package com.exp1_s1.minutanutricional

import com.exp1_s1.minutanutricional.data.RegistrationResult
import com.exp1_s1.minutanutricional.data.LoginResult
import com.exp1_s1.minutanutricional.data.RecoveryResult
import com.exp1_s1.minutanutricional.data.UserRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class UserRepositoryTest {
    @Test
    fun registerStoresUserAndAuthenticationRequiresMatchingCredentials() {
        val repository = UserRepository()

        assertEquals(RegistrationResult.Success, repository.register("Ana", "ana@example.com", "clave123"))
        assertEquals(LoginResult.Success, repository.authenticate("ana@example.com", "clave123"))
        assertEquals(LoginResult.InvalidCredentials, repository.authenticate("ana@example.com", "otra-clave"))
        assertTrue(repository.hasRegisteredEmail("ANA@EXAMPLE.COM"))
    }

    @Test
    fun registerRejectsEmptyFieldsAndDuplicateEmail() {
        val repository = UserRepository()

        assertEquals(RegistrationResult.EmptyFields, repository.register("", "ana@example.com", "clave123"))
        assertEquals(RegistrationResult.Success, repository.register("Ana", "ana@example.com", "clave123"))
        assertEquals(RegistrationResult.DuplicateEmail, repository.register("Otra Ana", "ANA@example.com", "clave456"))
    }

    @Test
    fun registerValidatesEmailAndMinimumPasswordLength() {
        val repository = UserRepository()

        assertEquals(RegistrationResult.InvalidEmail, repository.register("Ana", "correo-invalido", "clave123"))
        assertEquals(RegistrationResult.WeakPassword, repository.register("Ana", "ana@example.com", "12345"))
        assertEquals(LoginResult.EmptyFields, repository.authenticate("", ""))
    }

    @Test
    fun recoveryValidatesAndUpdatesRegisteredLocalPassword() {
        val repository = UserRepository()

        assertEquals(RecoveryResult.EmptyEmail, repository.recoverPassword("", "nueva123"))
        assertEquals(RecoveryResult.InvalidEmail, repository.recoverPassword("correo", "nueva123"))
        assertEquals(RecoveryResult.UnregisteredEmail, repository.recoverPassword("ana@example.com", "nueva123"))
        repository.register("Ana", "ana@example.com", "clave123")
        assertEquals(RecoveryResult.PasswordUpdated, repository.recoverPassword("ANA@example.com", "nueva123"))
        assertEquals(LoginResult.InvalidCredentials, repository.authenticate("ana@example.com", "clave123"))
        assertEquals(LoginResult.Success, repository.authenticate("ana@example.com", "nueva123"))
    }

    @Test
    fun registerRejectsTheSixthUser() {
        val repository = UserRepository()

        repeat(UserRepository.MAX_USERS) { index ->
            assertEquals(
                RegistrationResult.Success,
                repository.register("Usuario $index", "usuario$index@example.com", "clave$index")
            )
        }

        assertEquals(
            RegistrationResult.CapacityReached,
            repository.register("Sexto usuario", "sexto@example.com", "clave6")
        )
        assertEquals(UserRepository.MAX_USERS, repository.registeredUsers().size)
    }
}
