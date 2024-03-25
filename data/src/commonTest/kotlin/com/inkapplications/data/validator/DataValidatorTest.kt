package com.inkapplications.data.validator

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlin.test.*
import kotlin.time.Duration.Companion.minutes

class DataValidatorTest {
    @Test
    fun runsAsync() = runTest {
        val firstSpy = ValidatorRunSpy()
        val secondSpy = ValidatorRunSpy()

        val combined = firstSpy + secondSpy
        val result = async { combined.validate("test") }
        runCurrent()

        assertFalse(result.isCompleted, "Result should not be completed")
        assertTrue(firstSpy.started, "First Spy should run async")
        assertTrue(secondSpy.started, "First Spy should run async")

        advanceUntilIdle()

        assertTrue(result.isCompleted, "Result should be completed")
        assertEquals(ValidationResult.Success, result.getCompleted())
    }

    @Test
    fun runsLinear() = runTest {
        val firstSpy = ValidatorRunSpy()
        val secondSpy = ValidatorRunSpy()

        val combined = firstSpy.then(secondSpy)
        val result = async { combined.validate("test") }

        runCurrent()

        assertFalse(result.isCompleted, "Result should not be completed")
        assertTrue(firstSpy.started, "First Spy should run first")
        assertFalse(secondSpy.started, "Second Spy should not run yet")

        advanceUntilIdle()

        assertTrue(secondSpy.started, "Second Spy should run after first")
        assertTrue(result.isCompleted, "Result should be completed")
        assertEquals(ValidationResult.Success, result.getCompleted())
    }

    @Test
    fun mixedResult() = runTest {
        val failing = object: DataValidator<String> {
            override suspend fun validate(input: String): ValidationResult {
                return ValidationResult.Failed(ErrorA)
            }
        }
        val passing = object: DataValidator<String> {
            override suspend fun validate(input: String): ValidationResult {
                return ValidationResult.Success
            }
        }

        val combinedAsync = failing + passing
        val asyncResult = combinedAsync.validate("test")
        assertTrue(asyncResult is ValidationResult.Failed, "Async Result should propagate failed error")
        assertEquals(listOf(ErrorA), asyncResult.reasons, "Async Result should contain Error A")

        val linear = failing.then(passing)
        val linearResult = linear.validate("test")
        assertTrue(linearResult is ValidationResult.Failed, "Linear Result should propagate failed error")
        assertEquals(listOf(ErrorA), linearResult.reasons, "Linear Result should contain Error A")
    }

    @Test
    fun multipleFailures() = runTest {
        val failing = object: DataValidator<String> {
            override suspend fun validate(input: String): ValidationResult {
                return ValidationResult.Failed(ErrorA)
            }
        }
        val passing = object: DataValidator<String> {
            override suspend fun validate(input: String): ValidationResult {
                return ValidationResult.Failed(ErrorB)
            }
        }

        val combinedAsync = failing + passing
        val asyncResult = combinedAsync.validate("test")
        assertTrue(asyncResult is ValidationResult.Failed, "Async Result should propagate failed error")
        assertEquals(listOf(ErrorA, ErrorB), asyncResult.reasons, "Async Result should contain Error A and B in order")

        val linear = failing.then(passing)
        val linearResult = linear.validate("test")
        assertTrue(linearResult is ValidationResult.Failed, "Linear Result should propagate failed error")
        assertEquals(listOf(ErrorA, ErrorB), linearResult.reasons, "Linear Result should contain Error A and B in order")
    }

    @Test
    fun structure() {
        val validatorA = TestValidator("A")
        val validatorB = TestValidator("B")
        val validatorC = TestValidator("C")
        val validatorD = TestValidator("D")

        val asyncComposite = validatorA + validatorB
        assertTrue(asyncComposite is AsyncValidator, "Async Composite should be a AsyncValidator")
        assertSame(validatorA, asyncComposite.validators[0], "First async validator should be A")
        assertSame(validatorB, asyncComposite.validators[1], "Second async validator should be B")

        val linearComposite = validatorA.then(validatorB)
        assertTrue(linearComposite is LinearValidator, "Linear Composite should be a LinearValidator")
        assertSame(validatorA, linearComposite.validators[0], "First linear validator should be A")
        assertSame(validatorB, linearComposite.validators[1], "Second linear validator should be B")

        val asyncJoin = asyncComposite + validatorC
        assertTrue(asyncJoin is AsyncValidator, "Async join should be a AsyncValidator")
        assertSame(validatorA, asyncJoin.validators[0], "First async validator should be A")
        assertSame(validatorB, asyncJoin.validators[1], "Second async validator should be B")
        assertSame(validatorC, asyncJoin.validators[2], "Third async validator should be C")

        val linearJoin = linearComposite.then(validatorC)
        assertTrue(linearJoin is LinearValidator, "Linear join should be a LinearValidator")
        assertSame(validatorA, linearJoin.validators[0], "First linear validator should be A")
        assertSame(validatorB, linearJoin.validators[1], "Second linear validator should be B")
        assertSame(validatorC, linearJoin.validators[2], "Third linear validator should be C")

        val asyncLeftJoin = validatorC + asyncComposite
        assertTrue(asyncLeftJoin is AsyncValidator, "Async Left join should be a AsyncValidator")
        assertSame(validatorC, asyncLeftJoin.validators[0], "First async validator should be C")
        assertSame(validatorA, asyncLeftJoin.validators[1], "Second async validator should be A")
        assertSame(validatorB, asyncLeftJoin.validators[2], "Third async validator should be B")

        val linearLeftJoin = validatorC.then(linearComposite)
        assertTrue(linearLeftJoin is LinearValidator, "Linear Left join should be a LinearValidator")
        assertSame(validatorC, linearLeftJoin.validators[0], "First linear validator should be C")
        assertSame(validatorA, linearLeftJoin.validators[1], "Second linear validator should be A")
        assertSame(validatorB, linearLeftJoin.validators[2], "Third linear validator should be B")

        val asyncMerge = asyncComposite + (validatorC + validatorD)
        assertTrue(asyncMerge is AsyncValidator, "Async Merge should be a AsyncValidator")
        assertSame(validatorA, asyncMerge.validators[0], "First async validator should be A")
        assertSame(validatorB, asyncMerge.validators[1], "Second async validator should be B")
        assertSame(validatorC, asyncMerge.validators[2], "Third async validator should be C")
        assertSame(validatorD, asyncMerge.validators[3], "Fourth async validator should be D")

        val linearMerge = linearComposite.then(validatorC.then(validatorD))
        assertTrue(linearMerge is LinearValidator, "Linear Merge should be a LinearValidator")
        assertSame(validatorA, linearMerge.validators[0], "First linear validator should be A")
        assertSame(validatorB, linearMerge.validators[1], "Second linear validator should be B")
        assertSame(validatorC, linearMerge.validators[2], "Third linear validator should be C")
        assertSame(validatorD, linearMerge.validators[3], "Fourth linear validator should be D")
    }

    private class ValidatorRunSpy(): DataValidator<Any?> {
        var started = false
        override suspend fun validate(input: Any?): ValidationResult {
            started = true
            delay(10.minutes) // Tests should not be waiting for this to complete
            return ValidationResult.Success
        }
    }
    private data class TestValidator(
        private val name: String = "Test Validator"
    ): DataValidator<String> {
        override suspend fun validate(input: String): ValidationResult {
            return ValidationResult.Success
        }

        override fun toString(): String {
            return name
        }
    }
    private object ErrorA: ValidationError("Test Error A")
    private object ErrorB: ValidationError("Test Error B")
}
