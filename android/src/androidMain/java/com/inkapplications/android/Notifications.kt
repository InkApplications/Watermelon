package com.inkapplications.android

import android.app.Notification
import android.content.Context
import android.os.Build

/**
 * Create a notification builder with a channel ID if the OS supports it.
 *
 * @param channelId The ID of the notification channel to use on Android O+.
 */
fun Context.createNotificationBuilder(channelId: String): Notification.Builder {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Notification.Builder(this, channelId)
    } else {
        Notification.Builder(this)
    }
}
