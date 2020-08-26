package com.inkapplications.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

/**
 * Collect a flow after ensuring that the context is currently active.
 *
 * Fun-Fact: Flows can be resumed in a scope that's not currently active.
 * This prevents that by throwing a cancellation exception if the parent
 * scope isn't active before invoking the [action].
 */
suspend inline fun <T> Flow<T>.safeCollect(crossinline action: suspend (T) -> Unit) {
    collect {
        coroutineContext.ensureActive()
        action(it)
    }
}

/**
 * Collect a flow on a specific coroutine scope.
 *
 * This can be used to clean up some nesting with multiple launched
 * coroutine flow collections.
 * This uses [safeCollect] to ensure the scope is active when collecting.
 */
suspend fun <T> Flow<T>.collectOn(scope: CoroutineScope, action: suspend (T) -> Unit) {
    scope.launch {
        safeCollect(action)
    }
}

/**
 * Map each item in the emitted lists for the flow.
 */
inline fun <T, R> Flow<Collection<T>>.mapEach(crossinline mapping: suspend (T) -> R): Flow<List<R>> {
    return map { it.map { mapping(it) } }
}
