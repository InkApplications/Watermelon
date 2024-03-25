package com.inkapplications.data.validator

/**
 * Validator that always passes.
 *
 * This is useful as a default argument or for tests.
 */
object PassingValidator: DataValidator<Any?> {
    override suspend fun validate(input: Any?): ValidationResult = ValidationResult.Success
}

