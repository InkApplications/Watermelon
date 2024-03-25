package com.inkapplications.data.validator

import com.inkapplications.data.validator.MaxValueFailed.*


/**
 * Verifies whether a value is at least a certain value.
 */
class MaxIntValueValidator(
    private val maxValue: Int,
    private val inclusive: Boolean,
): DataValidator<Int> {
    override suspend fun validate(input: Int): ValidationResult {
        return when {
            inclusive && input <= maxValue -> ValidationResult.Success
            input < maxValue -> ValidationResult.Success
            else -> ValidationResult.Failed(
                MaxIntValueFailed(
                    required = maxValue,
                    actual = input,
                    inclusive = inclusive,
                )
            )
        }
    }
}

/**
 * Verifies whether a value is at least a certain value.
 */
class MaxLongValueValidator(
    private val maxValue: Long,
    private val inclusive: Boolean,
): DataValidator<Long> {
    override suspend fun validate(input: Long): ValidationResult {
        return when {
            inclusive && input <= maxValue -> ValidationResult.Success
            input < maxValue -> ValidationResult.Success
            else -> ValidationResult.Failed(
                MaxLongValueFailed(
                    required = maxValue,
                    actual = input,
                    inclusive = inclusive,
                )
            )
        }
    }

}

/**
 * Verifies whether a value is at least a certain value.
 */
class MaxFloatValueValidator(
    private val maxValue: Float,
    private val delta: Float,
    private val inclusive: Boolean,
): DataValidator<Float> {
    override suspend fun validate(input: Float): ValidationResult {
        return when {
            inclusive && input <= maxValue + delta -> ValidationResult.Success
            input < maxValue + delta -> ValidationResult.Success
            else -> ValidationResult.Failed(
                MaxFloatValueFailed(
                    required = maxValue,
                    actual = input,
                    inclusive = inclusive,
                )
            )
        }
    }
}

/**
 * Verifies whether a value is at least a certain value.
 */
class MaxDoubleValueValidator(
    private val maxValue: Double,
    private val delta: Double,
    private val inclusive: Boolean,
): DataValidator<Double> {
    override suspend fun validate(input: Double): ValidationResult {
        return when {
            inclusive && input <= maxValue + delta -> ValidationResult.Success
            input < maxValue + delta -> ValidationResult.Success
            else -> ValidationResult.Failed(
                MaxDoubleValueFailed(
                    required = maxValue,
                    actual = input,
                    inclusive = inclusive,
                )
            )
        }
    }
}

/**
 * Verifies whether a value is at least a certain value.
 */
fun MaxValueValidator(maxValue: Int, inclusive: Boolean = true) = MaxIntValueValidator(maxValue, inclusive)

/**
 * Verifies whether a value is at least a certain value.
 */
fun MaxValueValidator(maxValue: Long, inclusive: Boolean = true) = MaxLongValueValidator(maxValue, inclusive)

/**
 * Verifies whether a value is at least a certain value.
 */
fun MaxValueValidator(maxValue: Float, delta: Float = 0f, inclusive: Boolean = true) = MaxFloatValueValidator(maxValue, delta, inclusive)

/**
 * Verifies whether a value is at least a certain value.
 */
fun MaxValueValidator(maxValue: Double, delta: Double = 0.0, inclusive: Boolean = true) = MaxDoubleValueValidator(maxValue, delta, inclusive)

/**
 * Error thrown when a value is greater than the maximum allowed value.
 */
abstract class MaxValueFailed(
    open val required: Number,
    open val actual: Number,
    open val inclusive: Boolean,
): ValidationError(
    message = if (inclusive) "Value must be at most $required, but was $actual."
        else "Value must be less than $required, but was $actual.",
) {
    /**
     * Error thrown when a value is greater than the maximum allowed value.
     */
    data class MaxIntValueFailed(
        override val required: Int,
        override val actual: Int,
        override val inclusive: Boolean,
    ): MaxValueFailed(required, actual, inclusive)

    /**
     * Error thrown when a value is greater than the maximum allowed value.
     */
    data class MaxLongValueFailed(
        override val required: Long,
        override val actual: Long,
        override val inclusive: Boolean,
    ): MaxValueFailed(required, actual, inclusive)

    /**
     * Error thrown when a value is greater than the maximum allowed value.
     */
    data class MaxFloatValueFailed(
        override val required: Float,
        override val actual: Float,
        override val inclusive: Boolean,
    ): MaxValueFailed(required, actual, inclusive)

    /**
     * Error thrown when a value is greater than the maximum allowed value.
     */
    data class MaxDoubleValueFailed(
        override val required: Double,
        override val actual: Double,
        override val inclusive: Boolean,
    ): MaxValueFailed(required, actual, inclusive)
}
