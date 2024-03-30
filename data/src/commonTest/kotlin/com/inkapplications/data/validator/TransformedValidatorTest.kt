package com.inkapplications.data.validator

import com.inkapplications.data.transformer.DataTransformer
import kotlinx.coroutines.test.runTest
import kotlin.test.*

class TransformedValidatorTest {
    @Test
    fun transforms() = runTest {
        val validationError = object: ValidationError(
            message = "test-message",
        ) {}
        var validationInput: String? = null
        val spyValidator = object: DataValidator<String> {
            override suspend fun validate(input: String): ValidationResult {
                validationInput = input
                return ValidationResult.Failed(validationError)
            }
        }
        val transformer = object: DataTransformer<Int, String> {
            override fun transform(data: Int): String = "test-transformation"
            override fun reverseTransform(data: String): Int = TODO()
        }

        val testValidator = spyValidator.transformedWith(transformer)
        val result = testValidator.validate(5)

        assertTrue(result is ValidationResult.Failed, "Result from validator is used")
        assertSame(validationError, result.reasons.single(), "Exact result from validator is used")
        assertEquals("test-transformation", validationInput, "Input is transformed")
    }
}
