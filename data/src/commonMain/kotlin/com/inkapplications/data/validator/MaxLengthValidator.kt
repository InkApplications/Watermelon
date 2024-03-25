package com.inkapplications.data.validator

/**
 * Validator that checks if a string is at most a certain length.
 */
class MaxLengthValidator(
    private val maxLength: Int,
): DataValidator<String> {
    override suspend fun validate(input: String): ValidationResult {
        return if (input.length <= maxLength) {
            ValidationResult.Success
        } else {
            ValidationResult.Failed(
                MaxLengthFailed(
                    required = maxLength,
                    actual = input.length
                )
            )
        }
    }

    data class MaxLengthFailed(
        val required: Int,
        val actual: Int,
    ): ValidationError(
        message = "Input must be at most $required characters long, but was $actual.",
    )
}
