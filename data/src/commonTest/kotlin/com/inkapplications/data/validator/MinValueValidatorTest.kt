package com.inkapplications.data.validator

import com.inkapplications.data.validator.MinValueFailed.*
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MinValueValidatorTest {
    @Test
    fun tooSmall() = runTest {
        val intValidator = MinValueValidator(5)
        val intResult = intValidator.validate(4)
        assertTrue(intResult is ValidationResult.Failed)
        intResult.reasons.single().run {
            assertTrue(this is MinIntValueFailed)
            assertEquals(5, required)
            assertEquals(4, actual)
        }

        val longValidator = MinValueValidator(5L)
        val longResult = longValidator.validate(4L)
        assertTrue(longResult is ValidationResult.Failed)
        longResult.reasons.single().run {
            assertTrue(this is MinLongValueFailed)
            assertEquals(5L, required)
            assertEquals(4L, actual)
        }

        val floatValidator = MinValueValidator(5f)
        val floatResult = floatValidator.validate(4f)
        assertTrue(floatResult is ValidationResult.Failed)
        floatResult.reasons.single().run {
            assertTrue(this is MinFloatValueFailed)
            assertEquals(5f, required, 1e-6f)
            assertEquals(4f, actual, 1e-6f)
        }

        val doubleValidator = MinValueValidator(5.0)
        val doubleResult = doubleValidator.validate(4.0)
        assertTrue(doubleResult is ValidationResult.Failed)
        doubleResult.reasons.single().run {
            assertTrue(this is MinDoubleValueFailed)
            assertEquals(5.0, required, 1e-16)
            assertEquals(4.0, actual, 1e-16)
        }
    }

    @Test
    fun nonInclusive() = runTest {
        val intValidator = MinValueValidator(5, inclusive = false)
        val intResult = intValidator.validate(5)
        assertTrue(intResult is ValidationResult.Failed)
        intResult.reasons.single().run {
            assertTrue(this is MinIntValueFailed)
            assertEquals(5, required)
            assertEquals(5, actual)
        }

        val longValidator = MinValueValidator(5L, inclusive = false)
        val longResult = longValidator.validate(5L)
        assertTrue(longResult is ValidationResult.Failed)
        longResult.reasons.single().run {
            assertTrue(this is MinLongValueFailed)
            assertEquals(5L, required)
            assertEquals(5L, actual)
        }

        val floatValidator = MinValueValidator(5f, inclusive = false)
        val floatResult = floatValidator.validate(5f)
        assertTrue(floatResult is ValidationResult.Failed)
        floatResult.reasons.single().run {
            assertTrue(this is MinFloatValueFailed)
            assertEquals(5f, required, 1e-6f)
            assertEquals(5f, actual, 1e-6f)
        }

        val doubleValidator = MinValueValidator(5.0, inclusive = false)
        val doubleResult = doubleValidator.validate(5.0)
        assertTrue(doubleResult is ValidationResult.Failed)
        doubleResult.reasons.single().run {
            assertTrue(this is MinDoubleValueFailed)
            assertEquals(5.0, required, 1e-16)
            assertEquals(5.0, actual, 1e-16)
        }
    }

    @Test
    fun passing() = runTest {
        val intValidator = MinValueValidator(5)
        val intResult = intValidator.validate(6)
        assertTrue(intResult is ValidationResult.Success)

        val longValidator = MinValueValidator(5L)
        val longResult = longValidator.validate(6L)
        assertTrue(longResult is ValidationResult.Success)

        val floatValidator = MinValueValidator(5f)
        val floatResult = floatValidator.validate(6f)
        assertTrue(floatResult is ValidationResult.Success)

        val doubleValidator = MinValueValidator(5.0)
        val doubleResult = doubleValidator.validate(6.0)
        assertTrue(doubleResult is ValidationResult.Success)
    }

    @Test
    fun threshold() = runTest {
        val floatValidator = MinValueValidator(5f, 1e-2f)
        val floatResult = floatValidator.validate(4.99f)
        assertTrue(floatResult is ValidationResult.Success)

        val doubleValidator = MinValueValidator(5.0, 1e-2)
        val doubleResult = doubleValidator.validate(4.99)
        assertTrue(doubleResult is ValidationResult.Success)
    }
}
