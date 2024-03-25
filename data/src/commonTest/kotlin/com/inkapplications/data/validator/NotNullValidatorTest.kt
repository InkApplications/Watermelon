package com.inkapplications.data.validator

import com.inkapplications.data.validator.NotNullValidator.NotNullFailed
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertTrue

class NotNullValidatorTest {
    @Test
    fun nullValue() = runTest {
        val validator = NotNullValidator()
        val result = validator.validate(null)
        assertTrue(result is ValidationResult.Failed)
        assertTrue(result.reasons.single() is NotNullFailed)
    }

    @Test
    fun notNull() = runTest {
        val validator = NotNullValidator()
        val result = validator.validate("test")
        assertTrue(result is ValidationResult.Success)
    }
}
