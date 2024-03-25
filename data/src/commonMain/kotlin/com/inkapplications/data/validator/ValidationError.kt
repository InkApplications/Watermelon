package com.inkapplications.data.validator

/**
 * Cause of a validation failure.
 *
 * Validation errors should be class instances specific or identifiable
 * enough to be handled appropriately by the caller and thus this class
 * is abstract. It is not advised to create a "generic" error type for
 * a validator, as this may make UI handling and/or localization difficult.
 */
abstract class ValidationError(
    /**
     * A technical description of the error.
     *
     * Note that this may not be localized or user-readable. For a user-facing
     * error message, the specific instance type of error should be identified.
     */
    override val message: String,

    /**
     * The technical cause of the error, if any.
     */
    cause: Throwable? = null,
): Error(message, cause)
