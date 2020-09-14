package com.inkapplications.android

/**
 * Consume the event, stopping further calls.
 * 
 * This is used to clean up and reduce errors when consuming events such
 * as click listeners in android.
 * This can clean up the look of a when statement and help reduce errors,
 * since these return values will be named more logically.
 * 
 * This function should be used to return from an event listener.
 * 
 * Example:
 * 
 *    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
 *        android.R.id.home -> stopPropagation { onBackPressed() }
 *        else -> continuePropagation()
 *    }
 * 
 * @param action An optional action to take before stopping the event.
 * @return Always returns true, to indicate that the event has been handled.
 * @see continuePropagation for the opposite.
 */
inline fun stopPropagation(action: () -> Unit = {}): Boolean {
    action()
    return true
}

/**
 * Continue the event calls upward.
 * 
 * This is used to clean up and reduce errors when consuming events such
 * as click listeners in android.
 * This can clean up the look of a when statement and help reduce errors,
 * since these return values will be named more logically.
 * 
 * This function should be used to return from an event listener.
 * 
 * Example:
 * 
 *    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
 *        android.R.id.home -> stopPropagation { onBackPressed() }
 *        else -> continuePropagation()
 *    }
 * 
 * @param action An optional action to take before proceeding with the event.
 * @return Always returns false, to indicate that the event should continue upwards.
 * @see stopPropagation for the opposite.
 */
inline fun continuePropagation(action: () -> Unit = {}): Boolean {
    action()
    return false
}
