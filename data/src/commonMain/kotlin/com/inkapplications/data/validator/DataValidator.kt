package com.inkapplications.data.validator

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

/**
 * Validates a piece of data and returns a result.
 */
interface DataValidator<INPUT> {
    /**
     * Run a validation check on the given input.
     */
    suspend fun validate(input: INPUT): ValidationResult
}

/**
 * Combines multiple validators into a single validator.
 *
 * This preserves all validation results and combines any errors into
 * a single failure result. Errors are in order and not de-duplicated
 * from each validator applied.
 */
internal class LinearValidator<INPUT>(
    val validators: List<DataValidator<INPUT>>,
): DataValidator<INPUT> {
    override suspend fun validate(input: INPUT): ValidationResult {
        val results = validators.map { it.validate(input) }

        if (results.none { it !is ValidationResult.Success }) {
            return ValidationResult.Success
        }

        return ValidationResult.Failed(
            results.filterIsInstance<ValidationResult.Failed>().flatMap { it.reasons }
        )
    }
}

/**
 * Combines multiple validators into a single validator.
 *
 * This preserves all validation results and combines errors into a single
 * failure result. Validators are run in parallel and not de-duplicated.
 */
internal class AsyncValidator<INPUT>(
    val validators: List<DataValidator<INPUT>>,
): DataValidator<INPUT> {
    override suspend fun validate(input: INPUT): ValidationResult {
        return coroutineScope {
            val results = validators.map { async { it.validate(input) } }.awaitAll()

            if (results.none { it !is ValidationResult.Success }) {
                return@coroutineScope ValidationResult.Success
            }

            return@coroutineScope ValidationResult.Failed(
                results.filterIsInstance<ValidationResult.Failed>().flatMap { it.reasons }
            )
        }
    }
}

/**
 * Combine two validators, running the results in parallel.
 */
operator fun <INPUT> DataValidator<INPUT>.plus(other: DataValidator<INPUT>): DataValidator<INPUT> {
    return when {
        this is AsyncValidator && other is AsyncValidator -> {
            AsyncValidator(
                validators = this.validators + other.validators
            )
        }
        this is AsyncValidator -> {
            AsyncValidator(
                validators = this.validators + other
            )
        }
        other is AsyncValidator -> {
            AsyncValidator(
                validators = listOf(this) + other.validators
            )
        }
        else -> {
            AsyncValidator(
                validators = listOf(this, other)
            )
        }
    }
}

/**
 * Combine two input validators, running the results in sequence.
 */
fun <INPUT> DataValidator<INPUT>.then(other: DataValidator<INPUT>): DataValidator<INPUT> {
    return when {
        this is LinearValidator && other is LinearValidator -> {
            LinearValidator(
                validators = this.validators + other.validators
            )
        }
        this is LinearValidator -> {
            LinearValidator(
                validators = this.validators + other
            )
        }
        other is LinearValidator -> {
            LinearValidator(
                validators = listOf(this) + other.validators
            )
        }
        else -> {
            LinearValidator(
                validators = listOf(this, other)
            )
        }
    }
}
