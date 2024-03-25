package com.inkapplications.data.validator

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertSame
import kotlin.test.assertTrue

class FailingValidatorTest {
    @Test
    fun fails() = runTest {
        val nullResult = FailingValidator.validate(null)
        assertTrue(nullResult is ValidationResult.Failed)
        assertSame(FailingValidator.ExpectedFailure, nullResult.reasons.single())

        val result = FailingValidator.validate("test")
        assertTrue(result is ValidationResult.Failed)
        assertSame(FailingValidator.ExpectedFailure, result.reasons.single())
    }
}
