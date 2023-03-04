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

@Deprecated("Renamed to Items", ReplaceWith("mapItems(mapping)"))
inline fun <T, R> Flow<Iterable<T>>.mapEach(crossinline mapping: suspend (T) -> R): Flow<List<R>> = mapItems(mapping)

/**
 * Map each item in the emitted lists for the flow.
 */
inline fun <T, R> Flow<Iterable<T>>.mapItems(crossinline mapping: suspend (T) -> R): Flow<List<R>> {
    return map { it.map { mapping(it) } }
}

@Deprecated("Renamed to Items", ReplaceWith("filterItems(predicate)"))
inline fun <T> Flow<Iterable<T>>.filterEach(crossinline predicate: suspend (T) -> Boolean): Flow<List<T>> = filterItems(predicate)

/**
 * Filter each list emitted by a flow.
 */
inline fun <T> Flow<Iterable<T>>.filterItems(crossinline predicate: suspend (T) -> Boolean): Flow<List<T>> {
    return map { it.filter { predicate(it) } }
}

@Deprecated("Renamed to Items", ReplaceWith("filterItemIsInstance<R>()"))
inline fun <reified R> Flow<Iterable<*>>.filterEachIsInstance(): Flow<List<R>> = filterItemIsInstance()

/**
 * Filter each list emitted by a flow to only items that are an instance of [R]
 */
inline fun <reified R> Flow<Iterable<*>>.filterItemIsInstance(): Flow<List<R>> {
    return map { it.filterIsInstance<R>() }
}

@Deprecated("Renamed to Items", ReplaceWith("filterItemNotNull()"))
fun <T: Any> Flow<Iterable<T?>>.filterEachNotNull(): Flow<List<T>> = filterItemNotNull()

/**
 * Filter a list of nullable items into a non-nullable list.
 *
 * This filters out / removes any null items from each list emitted by the flow.
 */
fun <T: Any> Flow<Iterable<T?>>.filterItemNotNull(): Flow<List<T>> {
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

/**
 * Map a collection flow's items to another type, catching any errors thrown.
 *
 * @receiver A flow that emits collections whose items are to be transformed
 * @param transformer Transformation action to perform on each item in the collections emitted by the flow.
 * @return A flow of collections whose items are a result of the transformation action.
 */
inline fun <T, R> Flow<Iterable<T>>.mapItemsCatching(crossinline transformer: (T) -> R): Flow<List<Result<R>>> {
    return map { items ->
        items.map { item ->
            runCatching { transformer(item) }
        }
    }
}

/**
 * Run an action for each result that is a failure in an emitted collection.
 *
 * @receiver A flow that emits collections of results
 * @param action An action to run for every failed result in each emitted collection
 * @return The unmodified receiver flow
 */
inline fun <T> Flow<Iterable<Result<T>>>.onItemFailure(crossinline action: (Throwable) -> Unit): Flow<Iterable<Result<T>>> {
    return onEach {
        it.forEach {
            it.onFailure { action(it) }
        }
    }
}

/**
 * Filter the items in a collection of results to only successful results.
 *
 * @receiver A flow that emits collections of results
 * @return A flow of collections that contain the results of only successful items emitted by the receiver.
 */
fun <T> Flow<Iterable<Result<T>>>.filterItemSuccess(): Flow<List<T>> {
    return map { it.filter { it.isSuccess }.map { it.getOrThrow() } }
}

/**
 * Filter the items in a collection of results to only failed results.
 *
 * @receiver A flow that emits collections of results
 * @return A flow of collections that contain the errors of only failed items emitted by the receiver.
 */
fun <T> Flow<Iterable<Result<T>>>.filterItemFailure(): Flow<List<Throwable>> {
    return map { it.filter { it.isFailure }.map { it.exceptionOrNull()!! } }
}
