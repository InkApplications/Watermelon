package com.inkapplications.coroutines

import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
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
