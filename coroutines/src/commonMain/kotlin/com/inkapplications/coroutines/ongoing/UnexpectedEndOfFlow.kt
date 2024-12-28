package com.inkapplications.coroutines.ongoing

/**
 * Exception thrown when an [OngoingFlow] ends unexpectedly.
 */
class UnexpectedEndOfFlow(
    message: String? = null,
): IllegalStateException(
    message ?: "Unexpected end of flow"
)
