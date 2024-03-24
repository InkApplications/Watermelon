package com.inkapplications.data.transformer

/**
 * Uses two functions to transform data.
 *
 * This is useful to create a transformer from existing functions on an object.
 */
class SimpleTransformer<START, END>(
    private val transformer: (START) -> END,
    private val reverseTransformer: (END) -> START,
): DataTransformer<START, END> {
    override fun transform(data: START): END {
        return transformer(data)
    }

    override fun reverseTransform(data: END): START {
        return reverseTransformer(data)
    }
}
