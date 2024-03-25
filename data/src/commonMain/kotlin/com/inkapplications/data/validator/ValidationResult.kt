package com.inkapplications.data.validator

/**
 * Result of a validation check by a [DataValidator].
 */
sealed interface ValidationResult {
    /**
     * Indicates that validation has failed for one or more reasons.
     */
    data class Failed(val reasons: List<ValidationError>): ValidationResult {
        constructor(reason: ValidationError): this(listOf(reason))
    }

    /**
     * Indicates that validation has succeeded.
     */
    data object Success: ValidationResult
}
