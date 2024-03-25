package com.inkapplications.data.validator

import com.inkapplications.data.validator.NotEmptyValidator.NotEmptyFailed
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertTrue

class NotEmptyValidatorTest {
    @Test
    fun empty() = runTest {
        val validator = NotEmptyValidator()
        val result = validator.validate("")
        assertTrue(result is ValidationResult.Failed)
        assertTrue(result.reasons.single() is NotEmptyFailed)
    }

    @Test
    fun notEmpty() = runTest {
        val validator = NotEmptyValidator()
        val result = validator.validate("test")
        assertTrue(result is ValidationResult.Success)
    }
}
