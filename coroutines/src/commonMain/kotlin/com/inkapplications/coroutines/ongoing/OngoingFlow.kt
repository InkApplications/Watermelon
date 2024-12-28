package com.inkapplications.coroutines.ongoing

import kotlinx.coroutines.flow.Flow

/**
 * An Ongoing flow is like a flow, but is not expected to end.
 *
 * This wraps a flow, discouraging accidental use of unsafe extensions
 * for this type of data stream.
 */
interface OngoingFlow<T>
{
    /**
     * Convert thee ongoing flow to a standard flow.
     */
    fun asFlow(): Flow<T>
}
