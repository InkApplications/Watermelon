package com.inkapplications.data.validator

import com.inkapplications.data.transformer.DataTransformer

/**
 * Runs a validation check after first transforming the input to a different type.
 */
private class TransformedValidator<INPUT, VALIDATION>(
    private val transformer: DataTransformer<INPUT, VALIDATION>,
    private val validator: DataValidator<VALIDATION>,
): DataValidator<INPUT> {
    override suspend fun validate(input: INPUT): ValidationResult {
        return validator.validate(transformer.transform(input))
    }
}

/**
 * Run this validator after first transforming the data.
 *
 * This is useful when needing to use a validator for a different type than
 * the necessary input type.
 */
fun <INPUT, VALIDATION> DataValidator<VALIDATION>.transformedWith(
    transformer: DataTransformer<INPUT, VALIDATION>
): DataValidator<INPUT> {
    return TransformedValidator(transformer, this)
}
