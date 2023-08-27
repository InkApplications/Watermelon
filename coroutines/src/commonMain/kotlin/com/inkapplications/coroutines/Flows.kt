@file:Suppress("UNCHECKED_CAST")

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
inline fun <T, R> Flow<Iterable<T>>.mapItems(crossinline mapping: suspend (T) -> R): Flow<List<R>> {
    return map { it.map { mapping(it) } }
}

/**
 * Filter each list emitted by a flow.
 */
inline fun <T> Flow<Iterable<T>>.filterItems(crossinline predicate: suspend (T) -> Boolean): Flow<List<T>> {
    return map { it.filter { predicate(it) } }
}

/**
 * Filter each list emitted by a flow to only items that are an instance of [R]
 */
inline fun <reified R> Flow<Iterable<*>>.filterItemIsInstance(): Flow<List<R>> {
    return map { it.filterIsInstance<R>() }
}

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

/**
 * Combine a new flow into the current one by running an apply block.
 *
 * @receiver The initial flow containing a state to be applied against.
 * @param STATE The start and end data type for the flow
 * @param ITEM The item type to be used as an argument when running the apply.
 * @param other The 2nd flow to be combined with the initial flow
 * @param applicator A function that is applied against the current state
 *        with the new flow's emitted item as its argument.
 * @return A flow of the original items, with the apply run from [other]
 */
inline fun <STATE, ITEM> Flow<STATE>.combineApply(other: Flow<ITEM>, crossinline applicator: STATE.(ITEM) -> Unit): Flow<STATE> {
    return combine(other) { state, newItem ->
        state.also { applicator.invoke(state, newItem) }
    }
}

/**
 * Returns a [Flow] whose values are generated with [transform] function by combining
 * the most recently emitted values by each flow.
 */
fun <T1, T2, T3, T4, T5, T6, R> combine(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    transform: suspend (T1, T2, T3, T4, T5, T6) -> R
): Flow<R> = combine(flow, flow2, flow3, flow4, flow5, flow6) { args: Array<*> ->
    transform(
        args[0] as T1,
        args[1] as T2,
        args[2] as T3,
        args[3] as T4,
        args[4] as T5,
        args[5] as T6,
    )
}

/**
 * Returns a [Flow] whose values are generated with [transform] function by combining
 * the most recently emitted values by each flow.
 */
fun <T1, T2, T3, T4, T5, T6, T7, R> combine(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    flow7: Flow<T7>,
    transform: suspend (T1, T2, T3, T4, T5, T6, T7) -> R
): Flow<R> = combine(flow, flow2, flow3, flow4, flow5, flow6, flow7) { args: Array<*> ->
    transform(
        args[0] as T1,
        args[1] as T2,
        args[2] as T3,
        args[3] as T4,
        args[4] as T5,
        args[5] as T6,
        args[6] as T7,
    )
}

/**
 * Returns a [Flow] whose values are generated with [transform] function by combining
 * the most recently emitted values by each flow.
 */
fun <T1, T2, T3, T4, T5, T6, T7, T8, R> combine(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    flow7: Flow<T7>,
    flow8: Flow<T8>,
    transform: suspend (T1, T2, T3, T4, T5, T6, T7, T8) -> R
): Flow<R> = combine(flow, flow2, flow3, flow4, flow5, flow6, flow7, flow8) { args: Array<*> ->
    transform(
        args[0] as T1,
        args[1] as T2,
        args[2] as T3,
        args[3] as T4,
        args[4] as T5,
        args[5] as T6,
        args[6] as T7,
        args[7] as T8,
    )
}

/**
 * Returns a [Flow] whose values are generated with [transform] function by combining
 * the most recently emitted values by each flow.
 */
fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> combine(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    flow7: Flow<T7>,
    flow8: Flow<T8>,
    flow9: Flow<T9>,
    transform: suspend (T1, T2, T3, T4, T5, T6, T7, T8, T9) -> R
): Flow<R> = combine(flow, flow2, flow3, flow4, flow5, flow6, flow7, flow8, flow9) { args: Array<*> ->
    transform(
        args[0] as T1,
        args[1] as T2,
        args[2] as T3,
        args[3] as T4,
        args[4] as T5,
        args[5] as T6,
        args[6] as T7,
        args[7] as T8,
        args[8] as T9,
    )
}

/**
 * Returns a [Flow] whose values are generated with [transform] function by combining
 * the most recently emitted values by each flow.
 */
fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R> combine(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    flow7: Flow<T7>,
    flow8: Flow<T8>,
    flow9: Flow<T9>,
    flow10: Flow<T10>,
    transform: suspend (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> R
): Flow<R> = combine(flow, flow2, flow3, flow4, flow5, flow6, flow7, flow8, flow9, flow10) { args: Array<*> ->
    transform(
        args[0] as T1,
        args[1] as T2,
        args[2] as T3,
        args[3] as T4,
        args[4] as T5,
        args[5] as T6,
        args[6] as T7,
        args[7] as T8,
        args[8] as T9,
        args[9] as T10,
    )
}
