package com.inkapplications.data.validator

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertTrue

class MaxLengthValidatorTest {
    @Test
    fun tooLong() = runTest {
        val validator = MaxLengthValidator(5)
        val result = validator.validate("123456")
        assertTrue(result is ValidationResult.Failed)
        assertTrue(result.reasons.single() is MaxLengthValidator.MaxLengthFailed, "Error should be MaxLengthFailed")
    }

    @Test
    fun exact() = runTest {
        val validator = MaxLengthValidator(5)
        val result = validator.validate("12345")
        assertTrue(result is ValidationResult.Success)
    }

    @Test
    fun empty() = runTest {
        val validator = MaxLengthValidator(5)
        val result = validator.validate("")
        assertTrue(result is ValidationResult.Success)
    }
}
