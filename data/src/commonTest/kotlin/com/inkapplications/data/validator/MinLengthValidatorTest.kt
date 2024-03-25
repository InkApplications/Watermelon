package com.inkapplications.data.validator

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertTrue

class MinLengthValidatorTest {
    @Test
    fun tooShort() = runTest {
        val validator = MinLengthValidator(5)
        val result = validator.validate("1234")
        assertTrue(result is ValidationResult.Failed)
        assertTrue(result.reasons.single() is MinLengthValidator.MinLengthFailed, "Error should be MinLengthFailed")
    }

    @Test
    fun exact() = runTest {
        val validator = MinLengthValidator(5)
        val result = validator.validate("12345")
        assertTrue(result is ValidationResult.Success)
    }

    @Test
    fun long() = runTest {
        val validator = MinLengthValidator(5)
        val result = validator.validate("123456")
        assertTrue(result is ValidationResult.Success)
    }
}
