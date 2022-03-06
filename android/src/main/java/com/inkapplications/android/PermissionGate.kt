package com.inkapplications.android

import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.trySendBlocking

/**
 * Simplifies permission requests in an activity
 *
 * @param activity The activity context to use when requesting permissions.
 */
class PermissionGate(
    private val activity: ComponentActivity,
) {
    /**
     * Channel used to listen to the results of a permission request.
     */
    private val results = Channel<Map<String, Boolean>>()

    /**
     * Generic Android binding for all permission requests. This pipes the request into the results channel.
     */
    private val listener = activity.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        results.trySendBlocking(it)
    }

    /**
     * Check whether permissions are granted, and request them if not.
     *
     * @param permissions A list of permissions to attempt to obtain
     * @return Whether all the provided [permissions] have been successfully granted to the application.
     */
    suspend fun requestPermissions(vararg permissions: String): Boolean {
        val required = activity.checkSelfPermissions(permissions)
            .filter { it.value != PackageManager.PERMISSION_GRANTED }
            .keys
        if (required.isEmpty()) return true
        listener.launch(permissions)

        return !results.receive().containsValue(false)
    }

    /**
     * Executes an action only if all permissions can be obtained.
     *
     * This will request permissions immediately if they are not currently
     * granted. So, this should only be used for actions that are already
     * justified or self evident in rationale.
     *
     * @param permissions Permissions required for the action
     * @param grantedAction An action to execute only if all [permissions] are obtained
     */
    suspend fun withPermissions(vararg permissions: String, grantedAction: () -> Unit) {
        if (requestPermissions(*permissions)) {
            grantedAction()
        }
    }

    /**
     * Unregisters the permissions activity result callback from the bound activity.
     */
    fun unregister() {
        listener.unregister()
    }
}
