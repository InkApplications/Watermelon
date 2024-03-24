package com.inkapplications.data.transformer

/**
 * Uses a fixed sentinel value to determine whether to treat a value as null.
 */
class SentinelAsNullTransformer<T>(private val sentinel: T): DataTransformer<T, T?> {
    override fun transform(data: T): T? = data.takeIf { it != sentinel }
    override fun reverseTransform(data: T?): T = data ?: sentinel
}
