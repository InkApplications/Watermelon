package com.inkapplications.coroutines.ongoing

import kotlinx.coroutines.flow.Flow
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
