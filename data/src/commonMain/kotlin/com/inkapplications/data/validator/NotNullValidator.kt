package com.inkapplications.data.validator

/**
 * Validates that a value is not null.
 */
class NotNullValidator: DataValidator<Any?> {
    override suspend fun validate(input: Any?): ValidationResult {
        return if (input != null) {
            ValidationResult.Success
        } else {
            ValidationResult.Failed(NotNullFailed)
        }
    }

    /**
     * Error returned for when a value is null.
     */
    object NotNullFailed: ValidationError(
        message = "Value must not be null.",
    )
}
