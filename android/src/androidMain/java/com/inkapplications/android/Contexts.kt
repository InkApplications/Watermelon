package com.inkapplications.android

import android.content.Context
import androidx.core.content.ContextCompat

/**
 * Check the self permission granted state of multiple permissions.
 *
 * @param permissions A collection of permissions to check the status of.
 * @return a map of [permissions] associated to their grant status.
 */
fun Context.checkSelfPermissions(permissions: Array<out String>): Map<String, Int> {
    return permissions.associateWith {
        ContextCompat.checkSelfPermission(this, it)
    }
}
