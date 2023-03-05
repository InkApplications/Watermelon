package com.inkapplications.standard

import kotlin.coroutines.cancellation.CancellationException

/**
 * Throw a result's exception if it failed because of a coroutine cancellation.
 *
 * This is generally required for cooperative cancellation in coroutines and
 * this provides a simple way to re-throw cancellations.
 *
 *     runCatching { fetchWidgets() }
 *         .throwCancels()
 *         .onSuccess { /* ... */ }
 *
 * @receiver A result that may fail due to a coroutine cancellation.
 * @param onCancel Optional action to invoke if a cancellation occurs.
 * @return A result that is either successful or failed for any other reason.
 */
inline fun Result<Any?>.throwCancels(
    onCancel: (CancellationException) -> Unit = {},
) = onFailure {
    if (it is CancellationException) {
        onCancel(it)
        throw it
    }
}
