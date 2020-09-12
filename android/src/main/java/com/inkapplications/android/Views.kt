/*
 * The following code is based on Jake Wharton's Butterknife library.
 * Sources received from: https://github.com/JakeWharton/butterknife
 * Copyright Jake Wharton and licensed under:
 * Apache License Version 2.0, January 2004
 * http://www.apache.org/licenses/
 * 
 * Modifications include:
 *  - Update code to run as an extension rather than a class.
 */
package com.inkapplications.android

import android.os.Handler
import android.os.Looper
import android.view.View

private val MAIN_HANDLER = Handler(Looper.getMainLooper())

/**
 * Add a click listener that debounces multiple clicks posted in the
 * same frame. A click on one button disables all buttons for that frame.
 */
fun View.setDebouncingOnClickListener(clickListner: (View?) -> Unit) {
    var enabled = true
    val enableAgain = Runnable { enabled = true }

    setOnClickListener(object : View.OnClickListener {
        override fun onClick(v: View?) {
            if (!enabled) return
            enabled = false

            // Post to the main looper directly rather than going through the view.
            // Ensure that ENABLE_AGAIN will be executed, avoid static field {@link #enabled}
            // staying in false state.
            MAIN_HANDLER.post(enableAgain)
            clickListner(v)
        }
    })
}

/**
 * Set the visibility of a view.
 *
 * This works like Android's `setVisibility` but takes a boolean instead
 * of a `View` integer constant.
 * Use this to replace unwieldy if statements when binding views.
 *
 * Example:
 *
 *     fun toggleViews(show: Boolean) {
 *         my_view.setVisibility(show)
 *         my_invisible_view.setVisibility(show, View.INVISIBLE)
 *     }
 *
 * @param visible Whether the view should be displayed or not.
 * @param hiddenState Optional parameter to control how the view is hidden when [visible] is false.
 */
fun View.setVisibility(visible: Boolean, hiddenState: Int = View.GONE) {
    visibility = if (visible) View.VISIBLE else hiddenState
}
