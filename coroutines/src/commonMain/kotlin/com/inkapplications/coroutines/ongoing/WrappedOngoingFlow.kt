package com.inkapplications.coroutines.ongoing

import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlin.jvm.JvmInline

/**
 * Wrapped implementation of an ongoing flow.
 */
@JvmInline
private value class WrappedOngoingFlow<T>(
    private val backing: Flow<T>
): OngoingFlow<T> {
    override fun asFlow(): Flow<T> = backing
}

/**
 * Convert an existing flow to an ongoing flow.
 */
fun <T> Flow<T>.asOngoing(): OngoingFlow<T> = WrappedOngoingFlow(this)

/**
 * Create an ongoing flow.
 */
inline fun <T> ongoingFlow(
    crossinline builder: suspend FlowCollector<T>.() -> Unit,
): OngoingFlow<T> {
    return flow {
        builder(this)
        awaitCancellation()
    }.asOngoing()
}

/**
 * Create an ongoing flow from a set of items.
 */
fun <T> ongoingFlowOf(vararg items: T): OngoingFlow<T>
{
    return ongoingFlow {
        items.forEach { emit(it) }
    }
}
