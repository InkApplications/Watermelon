package com.inkapplications.android

import android.app.Activity
import android.content.Intent
import kotlin.reflect.KClass

/**
 * Start an activity by class reference.
 *
 * Shortcute extension to start an activity by reference, with or without
 * modifying the intent bundle.
 *
 * Example:
 *
 *     startActivity(MyActivity::class)
 *     // or With Arguments:
 *     startActivity(MyActivity::class) {
 *         putExtra("extra_id", "my_extra_value")
 *     }
 *
 * @param target The Activity class to be started.
 * @param intentBuilder An optional block for modifying the intent and/or extras.
 */
inline fun Activity.startActivity(target: KClass<out Activity>, intentBuilder: Intent.() -> Unit = {}) {
    Intent(this, target.java)
        .apply { intentBuilder(this) }
        .run(this::startActivity)
}
