package com.inkapplications.coroutines.ongoing

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest


/**
 * Collects a flow that never returns.
 *
 * This is identical to [collect], but throws an exception if the flow ends.
 */
suspend inline fun <T> OngoingFlow<T>.collect(
    crossinline observer: suspend (T) -> Unit
): Nothing {
    asFlow().collect { observer(it) }
    throw UnexpectedEndOfFlow()
}

/**
 * Collects a flow that never returns.
 *
 * This is identical to a Flow's [collectLatest], but throws an exception if the flow ends.
 */
suspend inline fun <T> OngoingFlow<T>.collectLatest(
    crossinline observer: suspend (T) -> Unit
): Nothing {
    asFlow().collectLatest { observer(it) }
    throw UnexpectedEndOfFlow()
}
