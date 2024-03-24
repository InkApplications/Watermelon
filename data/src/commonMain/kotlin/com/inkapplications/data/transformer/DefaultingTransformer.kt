package com.inkapplications.data.transformer

/**
 * Removes nulls by converting to a default value.
 *
 * Note that this will not reverse the transformation from the default value.
 * For a bijective version of this transformer, use [SentinelAsNullTransformer].
 */
class DefaultingTransformer<T: Any>(
    private val default: T,
): DataTransformer<T?, T> {
    override fun transform(data: T?): T {
        return data ?: default
    }

    override fun reverseTransform(data: T): T {
        return data
    }
}
