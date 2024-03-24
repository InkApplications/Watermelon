package com.inkapplications.data.transformer

import kotlin.jvm.JvmInline

/**
 * A reversible transformation operation between two types of data.
 *
 * Note that these operations are not necessarily bijective; the order of
 * the transformation may matter.
 */
interface DataTransformer<START, END> {
    fun transform(data: START): END
    fun reverseTransform(data: END): START
}

/**
 * Reverse the current transformer.
 */
fun <START, END> DataTransformer<START, END>.reverse(): DataTransformer<END, START> {
    return ReversedTransformer(this)
}

/**
 * Allow a transformer to be used in both directions by providing a reverse function.
 */
fun <START, END> ((START) -> END).withReverse(reverse: (END) -> START): DataTransformer<START, END> {
    return SimpleTransformer(
        transformer = this,
        reverseTransformer = reverse
    )
}

/**
 * Allow nulls to be passed through a transformer.
 */
fun <START: Any, END: Any> DataTransformer<START, END>.nullable(): DataTransformer<START?, END?> {
    return SimpleTransformer(
        transformer = { it?.let(this::transform) },
        reverseTransformer = { it?.let(this::reverseTransform) }
    )
}

/**
 * Chain two transformers together.
 */
fun <START, MIDDLE, END> DataTransformer<START, MIDDLE>.then(next: DataTransformer<MIDDLE, END>): DataTransformer<START, END> {
    return CompositeTransformer(
        starting = this,
        ending = next
    )
}

/**
 * Reverses the direction of a transformer.
 */
@JvmInline
private value class ReversedTransformer<A, B>(
    private val delegate: DataTransformer<A, B>,
): DataTransformer<B, A> {
    override fun transform(data: B): A {
        return delegate.reverseTransform(data)
    }
    override fun reverseTransform(data: A): B {
        return delegate.transform(data)
    }
}

/**
 * Combines multiple transformers into a single transformer, applying in order.
 */
private class CompositeTransformer<START, MIDDLE, END>(
    private val starting: DataTransformer<START, MIDDLE>,
    private val ending: DataTransformer<MIDDLE, END>,
): DataTransformer<START, END> {
    override fun reverseTransform(data: END): START {
        return ending.reverseTransform(data).let { starting.reverseTransform(it) }
    }

    override fun transform(data: START): END {
        return starting.transform(data).let { ending.transform(it) }
    }
}

