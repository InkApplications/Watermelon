package com.inkapplications.data.transformer

/**
 * Transformer that does nothing, useful as a default value.
 */
class NoTransformation<T>: DataTransformer<T, T> {
    override fun transform(data: T): T = data
    override fun reverseTransform(data: T): T = data
}
