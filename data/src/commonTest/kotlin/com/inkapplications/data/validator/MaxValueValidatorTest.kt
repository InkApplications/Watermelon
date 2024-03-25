package com.inkapplications.data.validator

import com.inkapplications.data.validator.MaxValueFailed.*
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MaxValueValidatorTest {
    @Test
    fun tooLarge() = runTest {
        val intValidator = MaxValueValidator(5)
        val intResult = intValidator.validate(6)
        assertTrue(intResult is ValidationResult.Failed)
        intResult.reasons.single().run {
            assertTrue(this is MaxIntValueFailed)
            assertEquals(5, required)
            assertEquals(6, actual)
        }

        val longValidator = MaxValueValidator(5L)
        val longResult = longValidator.validate(6L)
        assertTrue(longResult is ValidationResult.Failed)
        longResult.reasons.single().run {
            assertTrue(this is MaxLongValueFailed)
            assertEquals(5L, required)
            assertEquals(6L, actual)
        }

        val floatValidator = MaxValueValidator(5f)
        val floatResult = floatValidator.validate(6f)
        assertTrue(floatResult is ValidationResult.Failed)
        floatResult.reasons.single().run {
            assertTrue(this is MaxFloatValueFailed)
            assertEquals(5f, required, 1e-6f)
            assertEquals(6f, actual, 1e-6f)
        }

        val doubleValidator = MaxValueValidator(5.0)
        val doubleResult = doubleValidator.validate(6.0)
        assertTrue(doubleResult is ValidationResult.Failed)
        doubleResult.reasons.single().run {
            assertTrue(this is MaxDoubleValueFailed)
            assertEquals(5.0, required, 1e-16)
            assertEquals(6.0, actual, 1e-16)
        }
    }

    @Test
    fun passing() = runTest {
        val intValidator = MaxValueValidator(5)
        val intResult = intValidator.validate(4)
        assertTrue(intResult is ValidationResult.Success)

        val longValidator = MaxValueValidator(5L)
        val longResult = longValidator.validate(4L)
        assertTrue(longResult is ValidationResult.Success)

        val floatValidator = MaxValueValidator(5f)
        val floatResult = floatValidator.validate(4f)
        assertTrue(floatResult is ValidationResult.Success)

        val doubleValidator = MaxValueValidator(5.0)
        val doubleResult = doubleValidator.validate(4.0)
        assertTrue(doubleResult is ValidationResult.Success)
    }

    @Test
    fun nonInclusive() = runTest {
        val intValidator = MaxValueValidator(5, inclusive = false)
        val intResult = intValidator.validate(5)
        assertTrue(intResult is ValidationResult.Failed)
        intResult.reasons.single().run {
            assertTrue(this is MaxIntValueFailed)
            assertEquals(5, required)
            assertEquals(5, actual)
        }

        val longValidator = MaxValueValidator(5L, inclusive = false)
        val longResult = longValidator.validate(5L)
        assertTrue(longResult is ValidationResult.Failed)
        longResult.reasons.single().run {
            assertTrue(this is MaxLongValueFailed)
            assertEquals(5L, required)
            assertEquals(5L, actual)
        }

        val floatValidator = MaxValueValidator(5f, inclusive = false)
        val floatResult = floatValidator.validate(5f)
        assertTrue(floatResult is ValidationResult.Failed)
        floatResult.reasons.single().run {
            assertTrue(this is MaxFloatValueFailed)
            assertEquals(5f, required, 1e-6f)
            assertEquals(5f, actual, 1e-6f)
        }

        val doubleValidator = MaxValueValidator(5.0, inclusive = false)
        val doubleResult = doubleValidator.validate(5.0)
        assertTrue(doubleResult is ValidationResult.Failed)
        doubleResult.reasons.single().run {
            assertTrue(this is MaxDoubleValueFailed)
            assertEquals(5.0, required, 1e-16)
            assertEquals(5.0, actual, 1e-16)
        }
    }

    @Test
    fun threshold() = runTest {
        val floatValidator = MaxValueValidator(5f, 1e-2f)
        val floatResult = floatValidator.validate(5.01f)
        assertTrue(floatResult is ValidationResult.Success)

        val doubleValidator = MaxValueValidator(5.0, 1e-2)
        val doubleResult = doubleValidator.validate(5.01)
        assertTrue(doubleResult is ValidationResult.Success)
    }
}
