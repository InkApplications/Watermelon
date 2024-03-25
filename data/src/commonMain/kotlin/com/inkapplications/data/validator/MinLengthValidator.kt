package com.inkapplications.data.validator

/**
 * Validator that checks if a string is at least a certain length.
 */
class MinLengthValidator(
    private val minLength: Int,
): DataValidator<String> {
    override suspend fun validate(input: String): ValidationResult {
        return if (input.length >= minLength) {
            ValidationResult.Success
        } else {
            ValidationResult.Failed(
                MinLengthFailed(
                    required = minLength,
                    actual = input.length
                )
            )
        }
    }

    data class MinLengthFailed(
        val required: Int,
        val actual: Int,
    ): ValidationError(
        message = "Input must be at least $required characters long, but was $actual.",
    )
}
