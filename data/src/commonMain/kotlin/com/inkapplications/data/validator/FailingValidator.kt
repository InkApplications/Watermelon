package com.inkapplications.data.validator

/**
 * Validator that always fails.
 *
 * This is primarily useful for testing.
 */
object FailingValidator: DataValidator<Any?> {
    override suspend fun validate(input: Any?): ValidationResult = ValidationResult.Failed(ExpectedFailure)

    object ExpectedFailure: ValidationError(
        message = "This validator always fails.",
    )
}
