package com.inkapplications.coroutines.ongoing

import com.inkapplications.coroutines.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Modify an ongoing flow temporarily as a standard flow.
 *
 * This allows standard flow operators to be applied to the OngoingFlow
 *
 *     ongoingFlow.modify {
 *         filter { it != "foo" }.distinct()
 *     }
 */
inline fun <T, R> OngoingFlow<T>.unsafeTransform(
    modifier: Flow<T>.() -> Flow<R>,
): OngoingFlow<R> {
    return asFlow().let(modifier).asOngoing()
}

/**
 * @see Flow.first
 */
suspend fun <T> OngoingFlow<T>.first(): T
{
    return asFlow().first()
}

/**
 * @see Flow.take
 */
fun <T> OngoingFlow<T>.take(count: Int): OngoingFlow<T>
{
    return unsafeTransform { take(count) }
}

/**
 * @see Flow.drop
 */
fun <T> OngoingFlow<T>.drop(count: Int): OngoingFlow<T>
{
    return unsafeTransform { drop(count) }
}

/**
 * @see Flow.dropWhile
 */
fun <T> OngoingFlow<T>.dropWhile(
    predicate: (T) -> Boolean,
): OngoingFlow<T> {
    return unsafeTransform { dropWhile(predicate) }
}

/**
 * @see Flow.filterIsInstance
 */
inline fun <reified R> OngoingFlow<*>.filterIsInstance(): OngoingFlow<R>
{
    return unsafeTransform { filterIsInstance() }
}

/**
 * @see Flow.filter
 */
inline fun <T> OngoingFlow<T>.filter(
    crossinline predicate: (T) -> Boolean,
): OngoingFlow<T> {
    return unsafeTransform { filter { predicate(it) } }
}

/**
 * @see Flow.map
 */
inline fun <T, R> OngoingFlow<T>.map(
    crossinline mapper: suspend (T) -> R,
): OngoingFlow<R> {
    return unsafeTransform { map { mapper(it) } }
}

/**
 * @see Flow.mapLatest
 */
@OptIn(ExperimentalCoroutinesApi::class)
inline fun <T, R> OngoingFlow<T>.mapLatest(
    crossinline mapper: suspend (T) -> R,
): OngoingFlow<R> {
    return unsafeTransform { mapLatest { mapper(it) } }
}

/**
 * @see Flow.flatMapLatest
 */
@OptIn(ExperimentalCoroutinesApi::class)
inline fun <T, R> OngoingFlow<T>.flatMapLatest(
    crossinline mapper: suspend (T) -> Flow<R>,
): OngoingFlow<R> {
    return unsafeTransform { flatMapLatest { mapper(it) } }
}

/**
 * @see Flow.flatMapConcat
 */
@OptIn(ExperimentalCoroutinesApi::class)
inline fun <T, R> OngoingFlow<T>.flatMapConcat(
    crossinline mapper:  (T) -> Flow<R>,
): OngoingFlow<R> {
    return unsafeTransform { flatMapConcat { mapper(it) } }
}

/**
 * @see Flow.onEach
 */
inline fun <T> OngoingFlow<T>.onEach(
    crossinline action: (T) -> Unit,
): OngoingFlow<T> {
    return unsafeTransform { onEach { action(it) } }
}

/**
 * @see Flow.combine
 */
fun <T1, T2, R> OngoingFlow<T1>.combineWith(
    other: OngoingFlow<T2>,
    transform: (a: T1, b: T2) -> R,
): OngoingFlow<R> {
    return unsafeTransform {
        this.combine(other.asFlow(), transform)
    }
}

/**
 * @see Flow.filterNotNull
 */
fun <T: Any> OngoingFlow<T?>.filterNotNull(): OngoingFlow<T>
{
    return unsafeTransform { filterNotNull() }
}

/**
 * @see Flow.distinctUntilChanged
 */
fun <T> OngoingFlow<T>.distinctUntilChanged(): OngoingFlow<T>
{
    return unsafeTransform { distinctUntilChanged() }
}

/**
 * Emits an item at the start of a flow.
 */
fun <T> OngoingFlow<T>.startWith(item: T): OngoingFlow<T>
{
    return unsafeTransform { onStart { emit(item) } }
}

/**
 * @see Flow.combinePair
 */
fun <T1, T2> OngoingFlow<T1>.combinePair(
    other: OngoingFlow<T2>,
): OngoingFlow<Pair<T1, T2>> {
    return unsafeTransform {
        this.combinePair(other.asFlow())
    }
}

/**
 * @see Flow.combineTriple
 */
fun <T1, T2, T3> OngoingFlow<Pair<T1, T2>>.combineTriple(
    other: OngoingFlow<T3>,
): OngoingFlow<Triple<T1, T2, T3>> {
    return unsafeTransform {
        this.combineTriple(other.asFlow())
    }
}

/**
 * @see Flow.combineFlatten
 */
fun <T> OngoingFlow<Iterable<T>>.combineFlatten(
    other: OngoingFlow<Iterable<T>>,
): OngoingFlow<List<T>> {
    return unsafeTransform {
        this.combineFlatten(other.asFlow())
    }
}

/**
 * @see Flow.safeCollect
 */
suspend fun <T> OngoingFlow<T>.safeCollect(
    observer: suspend (T) -> Unit,
) {
    asFlow().safeCollect(observer)
    throw UnexpectedEndOfFlow()
}

/**
 * @see Flow.collectOn
 */
fun <T> OngoingFlow<T>.collectOn(
    scope: CoroutineScope,
    observer: suspend (T) -> Unit,
): Job {
    return scope.launch {
        safeCollect(observer)
    }
}

/**
 * @see Flow.mapItems
 */
inline fun <T, R> OngoingFlow<Iterable<T>>.mapItems(
    crossinline mapping: suspend (T) -> R,
): OngoingFlow<List<R>> {
    return unsafeTransform { this.mapItems(mapping) }
}

/**
 * @see Flow.filterItems
 */
inline fun <T> OngoingFlow<Iterable<T>>.filterItems(
    crossinline predicate: suspend (T) -> Boolean,
): OngoingFlow<List<T>> {
    return unsafeTransform { this.filterItems(predicate) }
}

/**
 * @see Flow.filterItemIsInstance
 */
inline fun <reified R> OngoingFlow<Iterable<*>>.filterItemIsInstance(): OngoingFlow<List<R>>
{
    return unsafeTransform { this.filterItemIsInstance() }
}

/**
 * @see Flow.filterItemNotNull
 */
fun <T: Any> OngoingFlow<Iterable<T?>>.filterItemNotNull(): OngoingFlow<List<T>>
{
    return unsafeTransform { this.filterItemNotNull() }
}

/**
 * @see Flow.mapItemsCatching
 */
inline fun <T, R> OngoingFlow<Iterable<T>>.mapItemsCatching(
    crossinline mapping: (T) -> R,
): OngoingFlow<List<Result<R>>> {
    return unsafeTransform { this.mapItemsCatching(mapping) }
}

/**
 * @see Flow.onItemFailure
 */
inline fun <T> OngoingFlow<Iterable<Result<T>>>.onItemFailure(
    crossinline action: (Throwable) -> Unit,
): OngoingFlow<Iterable<Result<T>>> {
    return unsafeTransform { this.onItemFailure(action) }
}

/**
 * @see Flow.filterItemSuccess
 */
fun <T> OngoingFlow<Iterable<Result<T>>>.filterItemSuccess(): OngoingFlow<List<T>>
{
    return unsafeTransform { this.filterItemSuccess() }
}

/**
 * @see Flow.filterItemFailure
 */
fun <T> OngoingFlow<Iterable<Result<T>>>.filterItemFailure(): OngoingFlow<List<Throwable>>
{
    return unsafeTransform { this.filterItemFailure() }
}

/**
 * @see Flow.combineApply
 */
inline fun <STATE, ITEM> OngoingFlow<STATE>.combineApply(
    other: Flow<ITEM>,
    crossinline applicator: STATE.(ITEM) -> Unit,
): OngoingFlow<STATE> {
    return unsafeTransform { this.combineApply(other, applicator) }
}

/**
 * Analogue to [Flow.combine] for ongoing flows.
 */
fun <T1, T2, R> combine(
    flow1: OngoingFlow<T1>,
    flow2: OngoingFlow<T2>,
    transform: (a: T1, b: T2) -> R,
): OngoingFlow<R> {
    return combine(
        flow1.asFlow(),
        flow2.asFlow(),
        transform
    ).asOngoing()
}

/**
 * Analogue to [Flow.combine] for ongoing flows.
 */
fun <T1, T2, T3, R> combine(
    flow1: OngoingFlow<T1>,
    flow2: OngoingFlow<T2>,
    flow3: OngoingFlow<T3>,
    transform: (a: T1, b: T2, c: T3) -> R,
): OngoingFlow<R> {
    return combine(
        flow1.asFlow(),
        flow2.asFlow(),
        flow3.asFlow(),
        transform
    ).asOngoing()
}

/**
 * Analogue to [Flow.combine] for ongoing flows.
 */
fun <T1, T2, T3, T4, R> combine(
    flow1: OngoingFlow<T1>,
    flow2: OngoingFlow<T2>,
    flow3: OngoingFlow<T3>,
    flow4: OngoingFlow<T4>,
    transform: (a: T1, b: T2, c: T3, d: T4) -> R,
): OngoingFlow<R> {
    return combine(
        flow1.asFlow(),
        flow2.asFlow(),
        flow3.asFlow(),
        flow4.asFlow(),
        transform
    ).asOngoing()
}

/**
 * Analogue to [Flow.combine] for ongoing flows.
 */
fun <T1, T2, T3, T4, T5, R> combine(
    flow1: OngoingFlow<T1>,
    flow2: OngoingFlow<T2>,
    flow3: OngoingFlow<T3>,
    flow4: OngoingFlow<T4>,
    flow5: OngoingFlow<T5>,
    transform: (a: T1, b: T2, c: T3, d: T4, e: T5) -> R,
): OngoingFlow<R> {
    return combine(
        flow1.asFlow(),
        flow2.asFlow(),
        flow3.asFlow(),
        flow4.asFlow(),
        flow5.asFlow(),
        transform
    ).asOngoing()
}
