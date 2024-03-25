package com.inkapplications.data.validator

/**
 * Validates that a string is not empty.
 */
class NotEmptyValidator: DataValidator<String> {
    override suspend fun validate(input: String): ValidationResult {
        return if (input.isNotEmpty()) {
            ValidationResult.Success
        } else {
            ValidationResult.Failed(NotEmptyFailed)
        }
    }

    /**
     * Error returned for when a value is empty.
     */
    object NotEmptyFailed: ValidationError(
        message = "Value must not be empty.",
    )
}
