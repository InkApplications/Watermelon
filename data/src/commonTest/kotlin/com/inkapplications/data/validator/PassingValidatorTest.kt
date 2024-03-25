package com.inkapplications.data.validator

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertTrue

class PassingValidatorTest {
    @Test
    fun passes() = runTest {
        val nullResult = PassingValidator.validate(null)
        assertTrue(nullResult is ValidationResult.Success)

        val result = PassingValidator.validate("test")
        assertTrue(result is ValidationResult.Success)
    }
}
