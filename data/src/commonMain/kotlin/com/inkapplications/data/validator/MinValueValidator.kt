package com.inkapplications.data.validator

import com.inkapplications.data.validator.MinValueFailed.*

/**
 * Verifies whether a value is at least a certain value.
 */
class MinIntValueValidator(
    private val minValue: Int,
    private val inclusive: Boolean,
): DataValidator<Int> {
    override suspend fun validate(input: Int): ValidationResult {
        return when {
            inclusive && input >= minValue -> ValidationResult.Success
            input > minValue -> ValidationResult.Success
            else -> ValidationResult.Failed(
                MinIntValueFailed(
                    required = minValue,
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
class MinLongValueValidator(
    private val minValue: Long,
    private val inclusive: Boolean,
): DataValidator<Long> {
    override suspend fun validate(input: Long): ValidationResult {
        return when {
            inclusive && input >= minValue -> ValidationResult.Success
            input > minValue -> ValidationResult.Success
            else -> ValidationResult.Failed(
                MinLongValueFailed(
                    required = minValue,
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
class MinFloatValueValidator(
    private val minValue: Float,
    private val delta: Float,
    private val inclusive: Boolean,
): DataValidator<Float> {
    override suspend fun validate(input: Float): ValidationResult {
        return when {
            inclusive && input >= minValue - delta -> ValidationResult.Success
            input > minValue - delta -> ValidationResult.Success
            else -> ValidationResult.Failed(
                MinFloatValueFailed(
                    required = minValue,
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
class MinDoubleValueValidator(
    private val minValue: Double,
    private val delta: Double,
    private val inclusive: Boolean,
): DataValidator<Double> {
    override suspend fun validate(input: Double): ValidationResult {
        return when {
            inclusive && input >= minValue - delta -> ValidationResult.Success
            input > minValue - delta -> ValidationResult.Success
            else -> ValidationResult.Failed(
                MinDoubleValueFailed(
                    required = minValue,
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
fun MinValueValidator(minValue: Int, inclusive: Boolean = true) = MinIntValueValidator(minValue, inclusive)

/**
 * Verifies whether a value is at least a certain value.
 */
fun MinValueValidator(minValue: Long, inclusive: Boolean = true) = MinLongValueValidator(minValue, inclusive)

/**
 * Verifies whether a value is at least a certain value.
 */
fun MinValueValidator(minValue: Float, delta: Float = 0f, inclusive: Boolean = true) = MinFloatValueValidator(minValue, delta, inclusive)

/**
 * Verifies whether a value is at least a certain value.
 */
fun MinValueValidator(minValue: Double, delta: Double = 0.0, inclusive: Boolean = true) = MinDoubleValueValidator(minValue, delta, inclusive)

/**
 * Error thrown when a value is less than the required minimum.
 */
abstract class MinValueFailed(
    open val required: Number,
    open val actual: Number,
    open val inclusive: Boolean,
): ValidationError(
    message = if (inclusive) "Value must be at least $required, but was $actual."
        else "Value must be greater than $required, but was $actual."
) {
    /**
     * Error thrown when a value is less than the required minimum.
     */
    data class MinIntValueFailed(
        override val required: Int,
        override val actual: Int,
        override val inclusive: Boolean,
    ): MinValueFailed(required, actual, inclusive)

    /**
     * Error thrown when a value is less than the required minimum.
     */
    data class MinLongValueFailed(
        override val required: Long,
        override val actual: Long,
        override val inclusive: Boolean,
    ): MinValueFailed(required, actual, inclusive)

    /**
     * Error thrown when a value is less than the required minimum.
     */
    data class MinFloatValueFailed(
        override val required: Float,
        override val actual: Float,
        override val inclusive: Boolean,
    ): MinValueFailed(required, actual, inclusive)

    /**
     * Error thrown when a value is less than the required minimum.
     */
    data class MinDoubleValueFailed(
        override val required: Double,
        override val actual: Double,
        override val inclusive: Boolean,
    ): MinValueFailed(required, actual, inclusive)
}
