package com.inkapplications.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.*
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
 *
 * @return The job launched and running the collection action.
 */
fun <T> Flow<T>.collectOn(scope: CoroutineScope, action: suspend (T) -> Unit): Job {
    return scope.launch {
        safeCollect(action)
    }
}

/**
 * Map each item in the emitted lists for the flow.
 */
inline fun <T, R> Flow<Iterable<T>>.mapEach(crossinline mapping: suspend (T) -> R): Flow<List<R>> {
    return map { it.map { mapping(it) } }
}

/**
 * Filter each list emitted by a flow.
 */
inline fun <T> Flow<Iterable<T>>.filterEach(crossinline predicate: suspend (T) -> Boolean): Flow<List<T>> {
    return map { it.filter { predicate(it) } }
}

/**
 * Filter each list emitted by a flow to only items that are an instance of [R]
 */
inline fun <reified R> Flow<Iterable<*>>.filterEachIsInstance(): Flow<List<R>> {
    return map { it.filterIsInstance<R>() }
}

/**
 * Filter a list of nullable items into a non-nullable list.
 *
 * This filters out / removes any null items from each list emitted by the flow.
 */
fun <T: Any> Flow<Iterable<T?>>.filterEachNotNull(): Flow<List<T>> {
    return map { it.filterNotNull() }
}

/**
 * Combine two flows into a flow of paired data.
 */
fun <A, B> Flow<A>.combinePair(other: Flow<B>): Flow<Pair<A, B>> {
    return combine(other) { a, b -> a to b }
}
/**
 * Combine a third flow into a flow of paired data to create a triple.
 */
fun <A, B, C> Flow<Pair<A, B>>.combineTriple(other: Flow<C>): Flow<Triple<A, B, C>> {
    return combine(other) { (a, b), c -> Triple(a, b, c) }
}

/**
 * Combine two flows of iterable data, flattening the data into a single list.
 */
fun <T> Flow<Iterable<T>>.combineFlatten(other: Flow<Iterable<T>>): Flow<List<T>> {
    return combine(other) { a, b -> a + b }
}
